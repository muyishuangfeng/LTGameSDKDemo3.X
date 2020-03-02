package com.gnetop.ltgame.core.manager.login.qq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.gnetop.ltgame.core.base.BaseActionActivity;
import com.gnetop.ltgame.core.common.LTGameCommon;
import com.gnetop.ltgame.core.common.LTGameOptions;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.impl.OnRechargeStateListener;
import com.gnetop.ltgame.core.widget.activity.QQActionActivity;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.RechargeObject;
import com.gnetop.ltgame.core.platform.AbsPlatform;
import com.gnetop.ltgame.core.platform.IPlatform;
import com.gnetop.ltgame.core.platform.PlatformFactory;
import com.gnetop.ltgame.core.platform.Target;
import com.gnetop.ltgame.core.util.LTGameUtil;


public class QQPlatform extends AbsPlatform {

    private QQHelper mHelper;

    private QQPlatform(Context context, String appId,  int target) {
        super(context, appId,  target);
    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object, OnRechargeStateListener listener) {

    }

    @Override
    public void login(Activity activity, int target, LoginObject object, OnLoginStateListener listener) {
        mHelper = new QQHelper(activity, object.getQqAppID(), object.getmAdID(), object.isLoginOut(),
                target, listener);
        mHelper.loginAction();
    }

    @Override
    public Class getUIKitClazz() {
        return QQActionActivity.class;
    }

    @Override
    public void onActivityResult(BaseActionActivity activity, int requestCode, int resultCode, Intent data) {
        mHelper.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 工厂类
     */
    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            LTGameOptions options = LTGameCommon.options();
            if (!LTGameUtil.isAnyEmpty(options.getLtAppId())) {
                platform = new QQPlatform(context, options.getLtAppId(),
                        target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_QQ;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return target == Target.LOGIN_QQ;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return false;
        }
    }
}
