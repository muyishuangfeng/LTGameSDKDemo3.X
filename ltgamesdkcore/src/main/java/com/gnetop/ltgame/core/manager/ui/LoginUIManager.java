package com.gnetop.ltgame.core.manager.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;


import com.gnetop.ltgame.core.base.BaseEntry;
import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.LoginManager;
import com.gnetop.ltgame.core.manager.lt.LoginRealizeManager;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.model.ResultModel;
import com.gnetop.ltgame.core.platform.Target;
import com.gnetop.ltgame.core.util.PreferencesUtils;
import com.gnetop.ltgame.core.widget.activity.LoginUIActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * 登录工具类
 */
public class LoginUIManager {


    private volatile static LoginUIManager sInstance;
    private OnLoginStateListener mListener;

    private LoginUIManager() {
    }


    public static LoginUIManager getInstance() {
        if (sInstance == null) {
            synchronized (LoginUIManager.class) {
                if (sInstance == null) {
                    sInstance = new LoginUIManager();
                }
            }
        }
        return sInstance;
    }


    /**
     * 登录进入
     *
     * @param activity  上下文
     * @param mListener 登录接口
     */
    public void loginIn(final Activity activity, LoginObject object,
                        final OnLoginStateListener mListener) {
        if (PreferencesUtils.getInt(Constants.USER_LT_UID, 0) != 0 ||
                !TextUtils.isEmpty(PreferencesUtils.getString(activity,
                        Constants.USER_LT_UID_KEY))) {
            if (!TextUtils.isEmpty(PreferencesUtils.getString(activity,
                    Constants.USER_GUEST_FLAG))) {
                if (PreferencesUtils.getString(activity,
                        Constants.USER_GUEST_FLAG).equals("YES")) {//是否是游客
                    login(activity, object, mListener);
                } else {
                    LoginRealizeManager.autoLogin(activity, new OnLoginStateListener() {
                        @Override
                        public void onState(Activity activity, LoginResult result) {
                            switch (result.state) {
                                case LTResultCode.STATE_AUTO_LOGIN_SUCCESS: {//成功
                                    if (result.getResultModel() != null) {
                                        mListener.onState(activity, LoginResult.successOf(
                                                LTResultCode.STATE_AUTO_LOGIN_SUCCESS,
                                                result.getResultModel()));
                                    }

                                    break;
                                }
                                case LTResultCode.STATE_AUTO_LOGIN_FAILED: {//失败
                                    loginOut(activity, object, mListener);
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onLoginOut() {

                        }
                    });

                }
            }
        } else {
            login(activity, object, mListener);
        }
    }

    /**
     * 登出
     *
     * @param activity 上下文
     */
    public void loginOut(Activity activity,
                         LoginObject result, OnLoginStateListener mListener) {
        PreferencesUtils.remove(Constants.USER_LT_UID);
        PreferencesUtils.remove(Constants.USER_LT_UID_KEY);
        com.facebook.login.LoginManager.getInstance().logOut();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(result.getmGoogleClient())
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mListener.onLoginOut();
            }
        });
    }

    /**
     * 是否登录成功
     */
    private boolean isLoginStatus(Activity activity) {
        return TextUtils.isEmpty(PreferencesUtils.getString(activity,
                Constants.USER_LT_UID)) &&
                TextUtils.isEmpty(PreferencesUtils.getString(activity,
                        Constants.USER_LT_UID_KEY));
    }

    /**
     * 登录方法
     *
     * @param activity 上下文
     */
    private void login(Activity activity, LoginObject result, OnLoginStateListener listener) {
        this.mListener = listener;
        Intent intent = new Intent(activity, LoginUIActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("mServerTest", result.isServerTest());
        bundle.putString("mFacebookID", result.getFacebookAppID());
        bundle.putString("mAgreementUrl", result.getAgreementUrl());
        bundle.putString("mPrivacyUrl", result.getPrivacyUrl());
        bundle.putString("googleClientID", result.getmGoogleClient());
        bundle.putString("LTAppID", result.getQqAppID());
        bundle.putBoolean("mIsLoginOut", result.isLoginOut());
        intent.putExtra("bundleData", bundle);
        activity.startActivity(intent);
    }


    public void setResultSuccess(Activity activity, int code, BaseEntry<ResultModel> result) {
        if (mListener != null) {
            mListener.onState(activity,
                    LoginResult.successOf(code, result));
        }
    }

    public void setResultFailed(Activity activity, int code, String msg) {
        if (mListener != null) {
            mListener.onState(activity,
                    LoginResult.failOf(code, msg));
        }
    }


    /**
     * 从google获取信息
     */
    public void getGoogleInfo(Activity context,
                              LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setmGoogleClient(result.getmGoogleClient());
        object.setSelfRequestCode(result.getSelfRequestCode());
        object.setType(Constants.GOOGLE_UI_TOKEN);
        LoginManager.login(context, Target.LOGIN_GOOGLE,
                object, mOnLoginListener);
    }


    /**
     * 从Facebook获取信息
     */
    public void getFBInfo(Activity context,
                          LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setFacebookAppID(result.getFacebookAppID());
        object.setSelfRequestCode(result.getSelfRequestCode());
        object.setType(Constants.FB_UI_TOKEN);
        LoginManager.login(context, Target.LOGIN_FACEBOOK,
                object, mOnLoginListener);

    }


    /**
     * 游客登录
     */
    public void guestLogin(Activity context,
                           LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        LoginManager.login(context, Target.LOGIN_GUEST,
                object, mOnLoginListener);
    }


    /**
     * 邮箱登录
     */
    public void emailLogin(Activity context,
                           LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setType(Constants.EMAIL_LOGIN);
        object.setEmail(result.getEmail());
        object.setAuthCode(result.getAuthCode());
        LoginManager.login(context, Target.LOGIN_EMAIL,
                object, mOnLoginListener);
    }

    /**
     * 绑定邮箱
     */
    public void bindEmail(Activity context,
                          LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setType(Constants.EMAIL_BIND);
        object.setEmail(result.getEmail());
        object.setAuthCode(result.getAuthCode());
        LoginManager.login(context, Target.LOGIN_EMAIL,
                object, mOnLoginListener);
    }

    /**
     * QQ登录
     */
    public void qqLogin(Activity context,
                        LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setType(Constants.QQ_LOGIN);
        object.setQqAppID(result.getQqAppID());
        LoginManager.login(context, Target.LOGIN_QQ,
                object, mOnLoginListener);
    }

    /**
     * 绑定QQ
     */
    public void bindQQ(Activity context,
                       LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setType(Constants.QQ_BIND);
        object.setQqAppID(result.getQqAppID());
        LoginManager.login(context, Target.LOGIN_QQ,
                object, mOnLoginListener);
    }

    /**
     * 获取验证码
     */
    public void getAuthCode(Activity activity, String mEmail, OnLoginStateListener mListener) {
        LoginRealizeManager.getEmailAuthCode(activity, mEmail, mListener);
    }

    /**
     * Google登录
     */
    public void googleLogin(Activity activity, String bindID,
                            String account, String userName, OnLoginStateListener mListener) {
        LoginRealizeManager.googleLogin(activity, bindID, account, userName, mListener);
    }

    /**
     * facebook登录
     */
    public void fbLogin(Activity activity, String bindID,
                        String account, String userName, OnLoginStateListener mListener) {
        LoginRealizeManager.facebookLogin(activity, bindID, account, userName, mListener);
    }

    /**
     * 绑定Google
     */
    public void googleBind(Activity activity, String bindID,
                           String account, String userName, OnLoginStateListener mListener) {
        LoginRealizeManager.bindGoogle(activity, bindID, account, userName, mListener);
    }

    /**
     * 绑定facebook
     */
    public void fbBind(Activity activity, String bindID,
                       String account, String userName, OnLoginStateListener mListener) {
        LoginRealizeManager.bindFB(activity, bindID, account, userName, mListener);
    }


}
