package com.gnetop.ltgame.core.manager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gnetop.ltgame.core.base.BaseEntry;
import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.common.LTGameCommon;
import com.gnetop.ltgame.core.common.LTGameOptions;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.LoginManager;
import com.gnetop.ltgame.core.manager.lt.LoginRealizeManager;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.model.ResultModel;
import com.gnetop.ltgame.core.net.Api;
import com.gnetop.ltgame.core.platform.Target;
import com.gnetop.ltgame.core.util.PreferencesUtils;
import com.gnetop.ltgame.core.widget.activity.LoginUIActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.lang.ref.WeakReference;


/**
 * 登录工具类
 */
public class LoginUIManager {


    private volatile static LoginUIManager sInstance;
    private OnLoginStateListener mListener;
    private WeakReference<Activity> mActivity;

    private LoginUIManager(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }


    public static LoginUIManager getInstance(Activity activity) {
        if (sInstance == null) {
            synchronized (LoginUIManager.class) {
                if (sInstance == null) {
                    sInstance = new LoginUIManager(activity);
                }
            }
        }
        return sInstance;
    }


    /**
     * 登录进入
     *
     * @param mListener 登录接口
     */
    public void loginIn(LoginObject object,
                        final OnLoginStateListener mListener) {
        if (PreferencesUtils.getInt(Constants.USER_LT_UID, 0) != 0 ||
                !TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(),
                        Constants.USER_LT_UID_KEY))) {
            if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(),
                    Constants.USER_GUEST_FLAG))) {
                if (PreferencesUtils.getString(mActivity.get(),
                        Constants.USER_GUEST_FLAG).equals("YES")) {//是否是游客
                    login(object, mListener);
                } else {
                    LoginRealizeManager.autoLogin(mActivity.get(), new OnLoginStateListener() {
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
                                    login(object, mListener);
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
            login(object, mListener);
        }
    }

    /**
     * 登出
     */
    public void loginOut(LoginObject result, OnLoginStateListener mListener) {
        PreferencesUtils.init(mActivity.get());
        if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(),
                Constants.USER_GUEST_FLAG))) {
            if (PreferencesUtils.getString(mActivity.get(),
                    Constants.USER_GUEST_FLAG).equals("NO")) {//是否是游客
                PreferencesUtils.remove(Constants.USER_LT_UID);
                PreferencesUtils.remove(Constants.USER_LT_UID_KEY);
            }
        }
        com.facebook.login.LoginManager.getInstance().logOut();
        String mAppID = "";
        if (!TextUtils.isEmpty(result.getmGoogleClient())) {
            mAppID = result.getmGoogleClient();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_GOOGLE_CLIENT_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_GOOGLE_CLIENT_ID);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mAppID)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mActivity.get(), gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(mActivity.get(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mListener.onLoginOut();
            }
        });
    }


    /**
     * 登录方法
     */
    private void login(LoginObject result, OnLoginStateListener listener) {
        this.mListener = listener;
        PreferencesUtils.init(mActivity.get());
        Intent intent = new Intent(mActivity.get(), LoginUIActivity.class);
        Bundle bundle = new Bundle();
        String mGoogleAppID = "";
        String mFBAppID = "";
        String mPrivacyUrl = "";
        String mAgreementUrl = "";
        String mServerTest = "";
        String LTAppID = "";
        String mQQAppID = "";
        String mWXAppID = "";
        String mWXSecret = "";
        String mCountryModel = "";
        boolean mIsLoginOut = false;
        //FB的AppID
        if (!TextUtils.isEmpty(result.getFBAppID())) {
            mFBAppID = result.getFBAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_FB_APP_ID))) {
            mFBAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_FB_APP_ID);
        }
        //隐私条款
        if (!TextUtils.isEmpty(result.getPrivacyUrl())) {
            mPrivacyUrl = result.getPrivacyUrl();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_PROVACY_URL))) {
            mPrivacyUrl = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_PROVACY_URL);
        }
        //用户协议
        if (!TextUtils.isEmpty(result.getAgreementUrl())) {
            mAgreementUrl = result.getAgreementUrl();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_AGREEMENT_URL))) {
            mAgreementUrl = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_AGREEMENT_URL);
        }
        //是否是测试服
        if (!TextUtils.isEmpty(result.isServerTest())) {
            mServerTest = result.isServerTest();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_SERVER_TEST_TAG))) {
            mServerTest = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_SERVER_TEST_TAG);
        }
        //Google客户端ID
        if (!TextUtils.isEmpty(result.getmGoogleClient())) {
            mGoogleAppID = result.getmGoogleClient();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_GOOGLE_CLIENT_ID))) {
            mGoogleAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_GOOGLE_CLIENT_ID);
        }
        //乐推AppID
        if (!TextUtils.isEmpty(result.getLTAppID())) {
            LTAppID = result.getLTAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID))) {
            LTAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID);
        }
        //QQ的AppID
        if (!TextUtils.isEmpty(result.getQqAppID())) {
            mQQAppID = result.getQqAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_QQ_APP_ID))) {
            mQQAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_QQ_APP_ID);
        }
        //微信AppID
        if (!TextUtils.isEmpty(result.getWxAppID())) {
            mWXAppID = result.getWxAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_APP_ID))) {
            mWXAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_APP_ID);
        }
        //微信AppSecret
        if (!TextUtils.isEmpty(result.getAppSecret())) {
            mWXSecret = result.getAppSecret();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_SECRET_KEY))) {
            mWXSecret = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_SECRET_KEY);
        }
        //国内还是国外
        if (!TextUtils.isEmpty(result.getCountryModel())) {
            mCountryModel = result.getCountryModel();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_COUNTRY_MODEL))) {
            mCountryModel = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_COUNTRY_MODEL);
        }
        //是否退出登录
        if (PreferencesUtils.getBoolean(mActivity.get(), Constants.LT_SDK_LOGIN_OUT_TAG, false)) {
            mIsLoginOut = PreferencesUtils.getBoolean(mActivity.get(), Constants.LT_SDK_LOGIN_OUT_TAG);
        }

        bundle.putString("mServerTest", mServerTest);
        bundle.putString("mFacebookID", mFBAppID);
        bundle.putString("mAgreementUrl", mAgreementUrl);
        bundle.putString("mPrivacyUrl", mPrivacyUrl);
        bundle.putString("googleClientID", mGoogleAppID);
        bundle.putString("LTAppID", LTAppID);
        bundle.putString("mQQAppID", mQQAppID);
        bundle.putString("mWXAppID", mWXAppID);
        bundle.putString("mWXSecret", mWXSecret);
        bundle.putString("mCountryModel", mCountryModel);
        bundle.putBoolean("mIsLoginOut", mIsLoginOut);
        intent.putExtra("bundleData", bundle);
        mActivity.get().startActivity(intent);
    }


    public void setResultSuccess(int code, BaseEntry<ResultModel> result) {
        if (mListener != null) {
            mListener.onState(mActivity.get(),
                    LoginResult.successOf(code, result));
        }
    }

    public void setResultFailed(int code, String msg) {
        if (mListener != null) {
            mListener.onState(mActivity.get(),
                    LoginResult.failOf(code, msg));
        }
    }


    /**
     * 从google获取信息
     */
    public void getGoogleInfo(
            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        PreferencesUtils.init(mActivity.get());
        String mAppID = "";
        if (!TextUtils.isEmpty(result.getmGoogleClient())) {
            mAppID = result.getmGoogleClient();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_GOOGLE_CLIENT_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_GOOGLE_CLIENT_ID);
        }
        object.setmGoogleClient(mAppID);
        object.setType(Constants.GOOGLE_UI_TOKEN);
        LoginManager.login(mActivity.get(), Target.LOGIN_GOOGLE,
                object, mOnLoginListener);
    }


    /**
     * 从Facebook获取信息
     */
    public void getFBInfo(
            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        PreferencesUtils.init(mActivity.get());
        String mAppID = "";
        if (!TextUtils.isEmpty(result.getFBAppID())) {
            mAppID = result.getFBAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_FB_APP_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_FB_APP_ID);
        }
        object.setFBAppID(mAppID);
        object.setType(Constants.FB_UI_TOKEN);
        LoginManager.login(mActivity.get(), Target.LOGIN_FACEBOOK,
                object, mOnLoginListener);

    }

    /**
     * 从QQ获取信息
     */
    public void getQQInfo(
            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        PreferencesUtils.init(mActivity.get());
        String mAppID = "";
        if (!TextUtils.isEmpty(result.getQqAppID())) {
            mAppID = result.getQqAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_QQ_APP_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_QQ_APP_ID);
        }
        object.setQqAppID(mAppID);
        object.setType(Constants.QQ_UI_TOKEN);
        LoginManager.login(mActivity.get(), Target.LOGIN_QQ,
                object, mOnLoginListener);
    }

    /**
     * 从微信获取信息
     */
    public void getWXInfo(
            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        PreferencesUtils.init(mActivity.get());
        String mAppID = "";
        String appSecret = "";
        if (!TextUtils.isEmpty(result.getWxAppID())) {
            mAppID = result.getQqAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_APP_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_APP_ID);
        }
        if (!TextUtils.isEmpty(result.getAppSecret())) {
            appSecret = result.getAppSecret();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_SECRET_KEY))) {
            appSecret = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_WX_SECRET_KEY);
        }
        object.setWxAppID(mAppID);
        object.setAppSecret(appSecret);
        object.setType(Constants.WX_UI_TOKEN);
        LoginManager.login(mActivity.get(), Target.LOGIN_WX,
                object, mOnLoginListener);
    }


    /**
     * 游客登录
     */
    public void guestLogin(
            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        PreferencesUtils.init(mActivity.get());
        String mAppID = "";
        if (!TextUtils.isEmpty(result.getLTAppID())) {
            mAppID = result.getLTAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID);
        }
        object.setLTAppID(mAppID);
        LoginManager.login(mActivity.get(), Target.LOGIN_GUEST,
                object, mOnLoginListener);
    }


    /**
     * 邮箱登录
     */
    public void emailLogin(
            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        PreferencesUtils.init(mActivity.get());
        String mAppID = "";
        if (!TextUtils.isEmpty(result.getLTAppID())) {
            mAppID = result.getLTAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID);
        }
        object.setLTAppID(mAppID);
        object.setType(Constants.EMAIL_LOGIN);
        object.setEmail(result.getEmail());
        object.setAuthCode(result.getAuthCode());
        LoginManager.login(mActivity.get(), Target.LOGIN_EMAIL,
                object, mOnLoginListener);
    }

    /**
     * 绑定邮箱
     */
    public void bindEmail(
            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        PreferencesUtils.init(mActivity.get());
        String mAppID = "";
        if (!TextUtils.isEmpty(result.getLTAppID())) {
            mAppID = result.getLTAppID();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID))) {
            mAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID);
        }
        object.setLTAppID(mAppID);
        object.setType(Constants.EMAIL_BIND);
        object.setEmail(result.getEmail());
        object.setAuthCode(result.getAuthCode());
        LoginManager.login(mActivity.get(), Target.LOGIN_EMAIL,
                object, mOnLoginListener);
    }


    /**
     * 获取验证码
     */
    public void getAuthCode(String mEmail, OnLoginStateListener mListener) {
        LoginRealizeManager.getEmailAuthCode(mActivity.get(), mEmail, mListener);
    }

    /**
     * Google登录
     */
    public void googleLogin(String bindID,
                            String account, String userName, String accessToken, OnLoginStateListener mListener) {
        LoginRealizeManager.googleLogin(mActivity.get(), bindID, account, userName, accessToken, mListener);
    }

    /**
     * facebook登录
     */
    public void fbLogin(String bindID,
                        String account, String userName, String accessToken, OnLoginStateListener mListener) {
        LoginRealizeManager.facebookLogin(mActivity.get(), bindID, account, userName, accessToken, mListener);
    }

    /**
     * QQ登录
     */
    public void qqLogin(String bindID,
                        String account, String userName, OnLoginStateListener mListener) {
        LoginRealizeManager.qqLogin(mActivity.get(), bindID, account, userName, mListener);
    }

    /**
     * 微信登录
     */
    public void wxLogin(String bindID,
                        String account, String userName, OnLoginStateListener mListener) {
        LTGameOptions options = LTGameCommon.getInstance().options();
        String mLtAppID = "";
        String baseUrl = "";
        if (!TextUtils.isEmpty(options.getLtAppId())) {
            mLtAppID = options.getLtAppId();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID))) {
            mLtAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID);
        }
        if (options.getISServerTest().equals(Constants.LT_SERVER_TEST)) {
            baseUrl = Api.TEST_SERVER_URL + mLtAppID + Api.TEST_SERVER_DOMAIN;
        } else if (options.getISServerTest().equals(Constants.LT_SERVER_OFFICIAL)) {
            baseUrl = Api.FORMAL_SERVER_URL + mLtAppID + Api.FORMAL_SERVER_DOMAIN;
        }
        LoginRealizeManager.weChatLogin(mActivity.get(), baseUrl, bindID, account, userName, mListener);
    }

    /**
     * 绑定Google
     */
    public void googleBind(String bindID,
                           String account, String userName, String accessToken, OnLoginStateListener mListener) {
        LoginRealizeManager.bindGoogle(mActivity.get(), bindID, account, userName, accessToken, mListener);
    }

    /**
     * 绑定facebook
     */
    public void fbBind(String bindID,
                       String account, String userName, String accessToken, OnLoginStateListener mListener) {
        LoginRealizeManager.bindFB(mActivity.get(), bindID, account, userName, accessToken, mListener);
    }

    /**
     * 绑定QQ
     */
    public void qqBind(String bindID,
                       String account, String userName, OnLoginStateListener mListener) {
        LoginRealizeManager.bindQQ(mActivity.get(), bindID, account, userName, mListener);
    }

    /**
     * 绑定微信
     */
    public void wxBind(String bindID,
                       String account, String userName, OnLoginStateListener mListener) {
        LTGameOptions options = LTGameCommon.getInstance().options();
        String mLtAppID = "";
        String baseUrl = "";
        if (!TextUtils.isEmpty(options.getLtAppId())) {
            mLtAppID = options.getLtAppId();
        } else if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID))) {
            mLtAppID = PreferencesUtils.getString(mActivity.get(), Constants.LT_SDK_APP_ID);
        }
        if (options.getISServerTest().equals(Constants.LT_SERVER_TEST)) {
            baseUrl = Api.TEST_SERVER_URL + mLtAppID + Api.TEST_SERVER_DOMAIN;
        } else if (options.getISServerTest().equals(Constants.LT_SERVER_OFFICIAL)) {
            baseUrl = Api.FORMAL_SERVER_URL + mLtAppID + Api.FORMAL_SERVER_DOMAIN;
        }
        LoginRealizeManager.bindWX(mActivity.get(), baseUrl, bindID, account, userName, mListener);
    }


}
