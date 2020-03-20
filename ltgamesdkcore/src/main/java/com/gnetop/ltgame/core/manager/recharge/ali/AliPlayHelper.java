package com.gnetop.ltgame.core.manager.recharge.ali;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.gnetop.ltgame.core.base.BaseEntry;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnRechargeStateListener;
import com.gnetop.ltgame.core.manager.lt.LoginRealizeManager;
import com.gnetop.ltgame.core.model.RechargeResult;
import com.gnetop.ltgame.core.model.ResultModel;
import com.gnetop.ltgame.core.platform.Target;
import com.gnetop.ltgame.core.util.ali.AuthResult;
import com.gnetop.ltgame.core.util.ali.PayResult;

import java.lang.ref.WeakReference;
import java.util.Map;

public class AliPlayHelper {

    private static String mOrderInfo;
    private static WeakReference<Activity> mActivityRef;
    private int mRechargeTarget;
    private static OnRechargeStateListener mListener;
    private int role_number;//  角色编号（游戏服务器用户ID）
    private int server_number;// 服务器编号（游戏提供）
    private MyHandler mHandler;
    //支付回调
    private static final int SDK_PAY_FLAG = 1;
    //授权回调
    private static final int SDK_AUTH_FLAG = 2;

    AliPlayHelper(Activity activity, int role_number,
                  int server_number, OnRechargeStateListener listener) {
        mActivityRef = new WeakReference<>(activity);
        this.role_number = role_number;
        this.server_number = server_number;
        this.mRechargeTarget = Target.RECHARGE_ALI_PLAY;
        mListener = listener;
        mHandler = new MyHandler(activity);
    }

    /**
     * Handler
     */
    private static class MyHandler extends Handler {

        private final WeakReference<Activity> mActivity;

        MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG: {//支付标记
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000")) {//支付成功
                        uploadToServer(payResult.getResult());
                    } else if (TextUtils.equals(resultStatus, "4000")) {//支付失败
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_FAILED, resultInfo));
                    } else if (TextUtils.equals(resultStatus, "8000")) {//支付宝支付结果未知（有可能已经支付成功）
                        // 请查询商户订单列表中订单的支付状态
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_HANDLE, resultInfo));
                    } else if (TextUtils.equals(resultStatus, "6001")) {//用户取消
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_CANCEL, resultInfo));
                    } else if (TextUtils.equals(resultStatus, "6002")) {//网络错误
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_NTE_WORK, resultInfo));
                    } else if (TextUtils.equals(resultStatus, "6004")) {//支付宝支付结果未知（有可能已经支付成功）
                        // 请查询商户订单列表中订单的支付状态
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_RESULT, resultInfo));
                    } else if (TextUtils.equals(resultStatus, "5000")) {//重复请求
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_REPEAT, resultInfo));
                    } else {//支付错误
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_ERROR, resultInfo));
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {//授权标记
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000") &&
                            TextUtils.equals(authResult.getResultCode(), "200")) {//授权成功
                        BaseEntry<ResultModel> baseEntry = new BaseEntry<>();
                        ResultModel resultModel = new ResultModel();
                        resultModel.setAliResult(authResult.getResult());
                        baseEntry.setData(resultModel);
                        mListener.onState(mActivity.get(), RechargeResult.successOf(
                                LTResultCode.STATE_ALI_PLAY_AUTH_SUCCESS, baseEntry));
                    } else {// 授权失败
                        mListener.onState(mActivity.get(), RechargeResult.failOf(
                                LTResultCode.STATE_ALI_PLAY_FAILED, authResult.getResult()));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }


    /**
     * 获取订单信息
     */
    void getOrderInfo() {
        pay(mOrderInfo);
        auth(mOrderInfo);
    }


    /**
     * 支付宝支付业务示例
     */
    private void pay(String mOrderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask aliPay = new PayTask(mActivityRef.get());
                Map<String, String> result = aliPay.payV2(mOrderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 支付宝账户授权业务示例
     */
    private void auth(String mOrderInfo) {
        //authInfo 的获取必须来自服务端
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(mActivityRef.get());
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(mOrderInfo, true);
                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }


    /**
     * 上传到服务器验证
     */
    private static void uploadToServer(String purchaseToken) {
        LoginRealizeManager.aliPlay(mActivityRef.get(),
                purchaseToken, mOrderInfo, new OnRechargeStateListener() {

                    @Override
                    public void onState(Activity activity, RechargeResult result) {
                        if (result != null) {
                            if (result.getResultModel() != null) {
                                if (result.getResultModel().getCode() == 0) {
                                    BaseEntry<ResultModel> entry = new BaseEntry<>();
                                    ResultModel resultModel = new ResultModel();
                                    resultModel.setOrder_number(result.getResultModel().getData().getOrder_number());
                                    resultModel.setGoods_price_type(result.getResultModel().getData().getGoods_price_type());
                                    resultModel.setGoods_price(result.getResultModel().getData().getGoods_price());
                                    entry.setData(resultModel);
                                    mListener.onState(mActivityRef.get(),
                                            RechargeResult.successOf(
                                                    LTResultCode.STATE_ALI_PLAY_SUCCESS, entry));
                                }
                            }

                        }

                    }

                });
    }

    void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

}
