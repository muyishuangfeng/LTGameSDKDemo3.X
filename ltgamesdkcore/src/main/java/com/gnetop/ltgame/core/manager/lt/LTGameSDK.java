package com.gnetop.ltgame.core.manager.lt;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.common.LTGameCommon;
import com.gnetop.ltgame.core.common.LTGameOptions;
import com.gnetop.ltgame.core.crashhandler.CrashHandler;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.impl.OnRechargeStateListener;
import com.gnetop.ltgame.core.manager.LoginManager;
import com.gnetop.ltgame.core.manager.RechargeManager;
import com.gnetop.ltgame.core.manager.login.fb.FacebookEventManager;
import com.gnetop.ltgame.core.manager.ui.LoginUIManager;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.model.RechargeObject;
import com.gnetop.ltgame.core.model.RechargeResult;
import com.gnetop.ltgame.core.platform.Target;
import com.gnetop.ltgame.core.util.DeviceUtils;
import com.gnetop.ltgame.core.util.LTGameUtil;
import com.gnetop.ltgame.core.util.PreferencesUtils;

import java.util.concurrent.Executors;

/**
 * Describe:
 * Author: Heaven
 * CtrateTime: 2020-02-13 17:00
 */
public class LTGameSDK {

    private static LTGameSDK sInstance;
    private String mAdID;

    private LTGameSDK() {
    }

    /**
     * 单例
     */
    public static LTGameSDK getDefaultInstance() {
        if (sInstance == null) {
            synchronized (LTGameSDK.class) {
                if (sInstance == null) {
                    sInstance = new LTGameSDK();
                }
            }
        }
        return sInstance;
    }

    /**
     * 登录
     */
    public void login(Activity context,
                      LoginObject result, OnLoginStateListener mOnLoginListener) {
        if (result != null) {
            switch (result.getLoginType()) {
                case Constants.GOOGLE_LOGIN: {//google登录和绑定
                    googleLogin(context, result, mOnLoginListener);
                    break;
                }
                case Constants.FB_LOGIN: {//Facebook登录和绑定
                    fbLogin(context, result, mOnLoginListener);
                    break;
                }
                case Constants.GUEST_LOGIN: {//游客登录和绑定
                    guestLogin(context, result, mOnLoginListener);
                    break;
                }
                case Constants.EMAIL_LOGIN: {//邮箱登录和绑定
                    emailLogin(context, result, mOnLoginListener);
                    break;
                }
                case Constants.QQ_LOGIN: {//QQ登录和绑定
                    qqLogin(context, result, mOnLoginListener);
                    break;
                }
                case Constants.UI_LOGIN: {//UI库登录
                    uiLogin(context, result, mOnLoginListener);
                    break;
                }
                case Constants.UI_LOGIN_OUT: {//UI库退出登录
                    uiLoginOut(context, result, mOnLoginListener);
                    break;
                }
            }
        }
    }

    /**
     * 支付
     */
    public void recharge(Activity context, boolean debug, boolean isServerTest,
                         RechargeObject result, OnRechargeStateListener mOnLoginListener) {
        if (result != null) {
            initRecharge(context, debug, isServerTest, result);
            switch (result.getRechargeType()) {
                case Constants.GP_RECHARGE: {//google支付
                    gpRecharge(context, result, mOnLoginListener);
                    break;
                }
                case Constants.ONE_STORE_RECHARGE: {//oneStore支付
                    oneStoreRecharge(context, result, mOnLoginListener);
                    break;
                }
            }
        }
    }

    /**
     * 初始化登录
     */
    public void init(Context context, boolean debug, boolean isServerTest,
                     LoginObject result) {
        FacebookEventManager.getInstance().start(context, result.getFacebookAppID());
        PreferencesUtils.init(context);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mAdID = DeviceUtils.getGoogleAdId(context.getApplicationContext());
                    if (!TextUtils.isEmpty(mAdID)) {
                        LTGameOptions options = new LTGameOptions.Builder(context)
                                .debug(debug)
                                .isServerTest(isServerTest)
                                .setFBiD(result.getFacebookAppID())
                                .setGoogle(result.getmGoogleClient())
                                //.setGP(result.getGPPublicKey())
                                .setGuest()
                                //.setOneStore(result.getOneStorePublicKey())
                                .appID(result.getLTAppID())
                                .emailEnable()
                                .setAdID(mAdID)
                                .build();
                        LTGameCommon.init(options);
//                        CrashHandler.getInstance().init(context, LTGameCommon.options().getCacheDir() +
//                                "/Crash/log/");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 初始化支付
     */
    private void initRecharge(Context context, boolean debug, boolean isServerTest,
                              RechargeObject result) {
        FacebookEventManager.getInstance().start(context, result.getFbAppID());
        CrashHandler.getInstance().init(context, LTGameCommon.options().getCacheDir() +
                "/Crash/log/");
        PreferencesUtils.init(context);
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mAdID = DeviceUtils.getGoogleAdId(context.getApplicationContext());
                    if (!TextUtils.isEmpty(mAdID)) {
                        LTGameOptions options = new LTGameOptions.Builder(context)
                                .debug(debug)
                                .isServerTest(isServerTest)
                                .setGP(result.getGPPublicKey())
                                .setGuest()
                                .setOneStore(result.getOnePublicKey())
                                .appID(result.getLTAppID())
                                .setAdID(mAdID)
                                .build();
                        LTGameCommon.init(options);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Google登录
     */
    private void googleLogin(Activity context,
                             LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setmGoogleClient(result.getmGoogleClient());
        object.setmAdID(mAdID);
        object.setType(result.getType());
        LoginManager.login(context, Target.LOGIN_GOOGLE,
                object, mOnLoginListener);
    }


    /**
     * Facebook登录
     */
    private void fbLogin(Activity context,
                         LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setFacebookAppID(result.getFacebookAppID());
        object.setmAdID(mAdID);
        object.setType(result.getType());
        LoginManager.login(context, Target.LOGIN_FACEBOOK,
                object, mOnLoginListener);

    }


    /**
     * 游客登录
     */
    private void guestLogin(Activity context,
                            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setmAdID(mAdID);
        object.setType(result.getType());
        LoginManager.login(context, Target.LOGIN_GUEST,
                object, mOnLoginListener);
    }


    /**
     * 邮箱登录
     */
    private void emailLogin(Activity context,
                            LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setmAdID(mAdID);
        object.setType(result.getType());
        object.setEmail(result.getEmail());
        if (!TextUtils.isEmpty(result.getAuthCode())) {
            object.setAuthCode(result.getAuthCode());
        } else {
            object.setAuthCode("");
        }
        LoginManager.login(context, Target.LOGIN_EMAIL,
                object, mOnLoginListener);
    }


    /**
     * QQ登录
     */
    private void qqLogin(Activity context,
                         LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginObject object = new LoginObject();
        object.setLTAppID(result.getLTAppID());
        object.setmAdID(mAdID);
        object.setType(result.getType());
        object.setEmail(result.getQqAppID());
        LoginManager.login(context, Target.LOGIN_QQ,
                object, mOnLoginListener);
    }


    /**
     * UI登录
     */
    private void uiLogin(Activity activity, LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginUIManager.getInstance().loginIn(activity, result, mOnLoginListener);
    }

    /**
     * UI退出登录
     */
    private void uiLoginOut(Activity activity, LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginUIManager.getInstance().loginOut(activity, result, mOnLoginListener);
    }

    /**
     * Google支付
     */
    private void gpRecharge(Activity activity, RechargeObject object, OnRechargeStateListener mListener) {
        RechargeObject result = new RechargeObject();
        result.setGoods_number(object.getGoods_number());
        result.setLTAppID(object.getLTAppID());
        result.setPayTest(object.getPayTest());
        result.setSku(object.getSku());
        result.setGPPublicKey(object.getGPPublicKey());
        result.setRole_number(object.getRole_number());
        result.setServer_number(object.getServer_number());
        RechargeManager.recharge(activity, Target.RECHARGE_GOOGLE, object, mListener);
    }

    /**
     * OneStore支付
     */
    private void oneStoreRecharge(Activity activity, RechargeObject object, OnRechargeStateListener mListener) {
        RechargeObject result = new RechargeObject();
        result.setGoods_number(object.getGoods_number());
        result.setLTAppID(object.getLTAppID());
        result.setPayTest(object.getPayTest());
        result.setSku(object.getSku());
        result.setGPPublicKey(object.getOnePublicKey());
        result.setRole_number(object.getRole_number());
        result.setServer_number(object.getServer_number());
        RechargeManager.recharge(activity, Target.RECHARGE_ONE_STORE, object, mListener);
    }

    /**
     * 上传服务器角色
     */
    public void uploadRole(Activity context,
                           LoginObject result, OnLoginStateListener mOnLoginListener) {
        LoginRealizeManager.bindRole(context, result.getRole_number(), result.getRole_name(),
                result.getRole_sex(), result.getRole_level(), result.getRole_create_time(),
                result.getServer_number(), mOnLoginListener);
    }


}
