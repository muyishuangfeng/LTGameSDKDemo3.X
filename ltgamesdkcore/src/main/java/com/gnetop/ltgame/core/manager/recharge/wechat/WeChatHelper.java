package com.gnetop.ltgame.core.manager.recharge.wechat;

import android.app.Activity;

import com.gnetop.ltgame.core.model.RechargeObject;
import com.gnetop.ltgame.core.platform.Target;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.lang.ref.WeakReference;

public class WeChatHelper {

    private IWXAPI mIWXAPI;
    private String mAppId;
    private int mRechargeTarget;
    private WeakReference<Activity> mActivityRef;
    //商户号
    private String mPartnerid;
    //支付会话ID
    private String mPrepayid;
    //随机字符串
    private String mNoncestr;
    //时间戳
    private String mTimestamp;
    //签名
    private String mSign;

    public WeChatHelper(Activity act, IWXAPI iwxapi, String appId, String partnerid,
                        String prepayid,  String timestamp, int target) {
        mActivityRef = new WeakReference<>(act);
        mIWXAPI = iwxapi;
        mAppId = appId;
        mPartnerid = partnerid;
        mPrepayid = prepayid;
        mTimestamp = timestamp;
        mRechargeTarget = Target.RECHARGE_WX_PLAY;
    }

    /**
     * 微信支付
     */
    public void wxPay() {
        PayReq request = new PayReq();
        request.appId = mAppId;
        request.partnerId = mPartnerid;
        request.prepayId = mPrepayid;
        request.packageValue = "Sign=WXPay";
        request.nonceStr = mNoncestr;
        request.timeStamp = mTimestamp;
        request.sign = mSign;
        mIWXAPI.sendReq(request);
    }

}
