package com.gnetop.ltgame.core.manager.recharge.ali;

import android.app.Activity;
import android.content.Context;

import com.gnetop.ltgame.core.common.LTGameCommon;
import com.gnetop.ltgame.core.common.LTGameOptions;
import com.gnetop.ltgame.core.impl.OnRechargeStateListener;
import com.gnetop.ltgame.core.manager.recharge.gp.GooglePlayPlatform;
import com.gnetop.ltgame.core.model.RechargeObject;
import com.gnetop.ltgame.core.platform.AbsPlatform;
import com.gnetop.ltgame.core.platform.IPlatform;
import com.gnetop.ltgame.core.platform.PlatformFactory;
import com.gnetop.ltgame.core.platform.Target;
import com.gnetop.ltgame.core.util.LTGameUtil;

public class AliPlayPlatform extends AbsPlatform {


    public AliPlayPlatform(Context context, String appId, int target) {
        super(context, appId, target);
    }

    @Override
    public void recharge(Activity activity, int target, RechargeObject object,
                         OnRechargeStateListener listener) {
        AliPlayHelper mHelper = new AliPlayHelper(activity, object.getRole_number(),
                object.getServer_number(), listener);
        mHelper.getOrderInfo();
    }


    /**
     * 工厂
     */
    public static class Factory implements PlatformFactory {

        @Override
        public IPlatform create(Context context, int target) {
            IPlatform platform = null;
            LTGameOptions options = LTGameCommon.getInstance().options();
            if (!LTGameUtil.isAnyEmpty(options.getLtAppId())) {
                platform = new AliPlayPlatform(context, options.getLtAppId(), target);
            }
            return platform;
        }

        @Override
        public int getPlatformTarget() {
            return Target.PLATFORM_ALI_PLAY;
        }

        @Override
        public boolean checkLoginPlatformTarget(int target) {
            return false;
        }

        @Override
        public boolean checkRechargePlatformTarget(int target) {
            return target == Target.RECHARGE_ALI_PLAY;
        }
    }
}
