package com.gnetop.ltgame.core.manager.login.qq;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;


import com.gnetop.ltgame.core.exception.LTGameError;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LoginRealizeManager;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.platform.Target;
import com.gnetop.ltgame.core.util.PreferencesUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

class QQHelper {

    private static final String TAG = QQHelper.class.getSimpleName();
    private String mTencentID;
    private String type;
    private boolean mIsLoginOut;
    private int mLoginTarget;
    private WeakReference<Activity> mActivityRef;
    private OnLoginStateListener mListener;
    private Tencent mTencent;
    private LoginUIListener mLoginListener;


    QQHelper(Activity activity, String mTencentID, String type, boolean mIsLoginOut, int mLoginTarget,
             OnLoginStateListener mListener) {
        this.mActivityRef = new WeakReference<>(activity);
        this.mTencentID = mTencentID;
        this.mIsLoginOut = mIsLoginOut;
        this.mLoginTarget = Target.LOGIN_QQ;
        this.mListener = mListener;
        this.type = type;
        mTencent = Tencent.createInstance(mTencentID, activity);
    }


    /**
     * 登录
     */
    void loginAction() {
        if (mIsLoginOut) {
            if (mTencent != null) {
                mTencent.logout(mActivityRef.get());
                login();
                if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivityRef.get(),
                        com.gnetop.ltgame.core.common.Constants.QQ_ACCESS_TOKEN)) &&
                        !TextUtils.isEmpty(PreferencesUtils.getString(mActivityRef.get(),
                                com.gnetop.ltgame.core.common.Constants.QQ_OPEN_ID)) &&
                        PreferencesUtils.getLong(mActivityRef.get(),
                                com.gnetop.ltgame.core.common.Constants.QQ_TOKEN_TIMEOUT,
                                0) != 0) {
                    PreferencesUtils.remove(
                            com.gnetop.ltgame.core.common.Constants.QQ_ACCESS_TOKEN);
                    PreferencesUtils.remove(
                            com.gnetop.ltgame.core.common.Constants.QQ_OPEN_ID);
                    PreferencesUtils.remove(
                            com.gnetop.ltgame.core.common.Constants.QQ_TOKEN_TIMEOUT);
                }
            }
        } else {
            login();
        }
    }

    /**
     * QQ登录回调
     */
    private class LoginUIListener implements IUiListener {

        @Override
        public void onComplete(Object object) {
            if (null == object) {
                LTGameError error = LTGameError.make(LTResultCode.STATE_CODE_PARSE_ERROR, TAG + "#LoginUiListener#qq token is null"
                );
                mListener.onState(null, LoginResult.failOf(error));
                return;
            }
            JSONObject jsonResponse = (JSONObject) object;
            if (jsonResponse.length() == 0) {
                LTGameError error = LTGameError.make(LTResultCode.STATE_CODE_PARSE_ERROR,
                        TAG + "#LoginUiListener#qq token is null ");
                mListener.onState(null, LoginResult.failOf(error));
            } else {
                try {
                    String token = jsonResponse.getString(Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonResponse.getString(Constants.PARAM_EXPIRES_IN);
                    String openId = jsonResponse.getString(Constants.PARAM_OPEN_ID);
                    if (!TextUtils.isEmpty(token) &&
                            !TextUtils.isEmpty(expires)
                            && !TextUtils.isEmpty(openId)) {
                        long time = System.currentTimeMillis() + Long.parseLong(expires) * 1000;
                        saveData(token, openId, time);
                        mTencent.setAccessToken(token, expires);
                        mTencent.setOpenId(openId);
                        getUserInfo();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(UiError uiError) {
            LTGameError error = LTGameError.make(LTResultCode.STATE_CODE_PARSE_ERROR,
                    TAG + uiError.errorDetail + uiError.errorMessage + uiError.errorCode);
            mListener.onState(null, LoginResult.failOf(error));
        }

        @Override
        public void onCancel() {
            mListener.onState(null, LoginResult.cancelOf());
        }
    }


    /**
     * 登录
     */
    private void login() {
        if (mTencent != null) {
            long time = (PreferencesUtils.getLong(mActivityRef.get(),
                    com.gnetop.ltgame.core.common.Constants.QQ_TOKEN_TIMEOUT)
                    - System.currentTimeMillis()) / 1000;
            if (time > 0) {
                mTencent.setOpenId(PreferencesUtils.getString(mActivityRef.get(),
                        com.gnetop.ltgame.core.common.Constants.QQ_OPEN_ID));
                mTencent.setAccessToken(PreferencesUtils.getString(mActivityRef.get(),
                        com.gnetop.ltgame.core.common.Constants.QQ_ACCESS_TOKEN),
                        String.valueOf(time));
                getUserInfo();
            } else {
                mLoginListener = new LoginUIListener();
                mTencent.login(mActivityRef.get(), "all", mLoginListener, true);
            }

        }

    }

    /**
     * 保存数据
     */
    private void saveData(String accessToken, String openID, long time) {
        PreferencesUtils.putString(mActivityRef.get(),
                com.gnetop.ltgame.core.common.Constants.QQ_ACCESS_TOKEN, accessToken);
        PreferencesUtils.putString(mActivityRef.get(),
                com.gnetop.ltgame.core.common.Constants.QQ_OPEN_ID, openID);
        PreferencesUtils.putLong(
                com.gnetop.ltgame.core.common.Constants.QQ_TOKEN_TIMEOUT, time);
    }

    /**
     * 回调
     */
    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            mLoginListener = new LoginUIListener();
            Tencent.onActivityResultData(requestCode, resultCode, data, mLoginListener);
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        QQToken token = mTencent.getQQToken();
        UserInfo mInfo = new UserInfo(mActivityRef.get(), token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jb = (JSONObject) object;
                try {
                    String name = jb.getString("nickname");
                    if (!TextUtils.isEmpty(type)) {
                        if (type.equals(com.gnetop.ltgame.core.common.Constants.QQ_LOGIN)) {//登录
                            LoginRealizeManager.qqLogin(mActivityRef.get(),
                                    PreferencesUtils.getString(mActivityRef.get(),
                                            com.gnetop.ltgame.core.common.Constants.QQ_OPEN_ID),
                                    PreferencesUtils.getString(mActivityRef.get(),
                                            com.gnetop.ltgame.core.common.Constants.QQ_USER_NAME),
                                    PreferencesUtils.getString(mActivityRef.get(),
                                            com.gnetop.ltgame.core.common.Constants.QQ_USER_NAME),
                                    mListener);
                        } else if (type.equals(com.gnetop.ltgame.core.common.Constants.QQ_BIND)) {//绑定
                            LoginRealizeManager.bindQQ(mActivityRef.get(),
                                    PreferencesUtils.getString(mActivityRef.get(),
                                            com.gnetop.ltgame.core.common.Constants.QQ_OPEN_ID),
                                    PreferencesUtils.getString(mActivityRef.get(),
                                            com.gnetop.ltgame.core.common.Constants.QQ_USER_NAME),
                                    PreferencesUtils.getString(mActivityRef.get(),
                                            com.gnetop.ltgame.core.common.Constants.QQ_USER_NAME), mListener);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });

    }
}
