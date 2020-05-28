package com.gnetop.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnRechargeStateListener;
import com.gnetop.ltgame.core.manager.lt.LTGameSDK;
import com.gnetop.ltgame.core.model.RechargeObject;
import com.gnetop.ltgame.core.model.RechargeResult;


public class GooglePlayActivity extends AppCompatActivity {

    Button mBtnPay,mBtnOrder;
    TextView mTxtResult;
    private static final String TAG = GooglePlayActivity.class.getSimpleName();
    private String mGoodsID = "com.gnetop.one";
    String mSKU = "com.gnetop.one";
    RechargeObject mRequest;
    private int mPayTest = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play);
        initView();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = new RechargeObject();
                mRequest.setRechargeType(Constants.GP_RECHARGE);
                mRequest.setGoods_number(mGoodsID);
                mRequest.setPayTest(mPayTest);
                mRequest.setSku(mSKU);
                mRequest.setRole_number(12);
                mRequest.setServer_number(1);
                LTGameSDK.getDefaultInstance().recharge(GooglePlayActivity.this,
                        mRequest, mOnRechargeListener);
            }
        });

    }


    OnRechargeStateListener mOnRechargeListener = new OnRechargeStateListener() {

        @Override
        public void onState(Activity activity, RechargeResult result) {
            switch (result.state) {
                case LTResultCode.STATE_GP_CREATE_ORDER_SUCCESS://创建订单成功
                    Log.e(TAG, "STATE_GP_CREATE_ORDER_SUCCESS");
                    break;
                case LTResultCode.STATE_GP_CREATE_ORDER_FAILED://创建订单失败
                    Log.e(TAG, "STATE_GP_CREATE_ORDER_FAILED");
                    break;
                case LTResultCode.STATE_RECHARGE_SUCCESS_CODE://支付成功
                    mTxtResult.setText(result.getResultModel().getData().toString());
                    Log.e(TAG, "STATE_RECHARGE_SUCCESS_CODE");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_FAILED://支付失败
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_FAILED");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_SERVICE_UNAVAILABLE://网络异常或者Google服务异常
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_SERVICE_UNAVAILABLE");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_BILLING_UNAVAILABLE://不支持Google支付
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_BILLING_UNAVAILABLE");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_ITEM_UNAVAILABLE://商品不可购买

                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_ITEM_UNAVAILABLE");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_DEVELOPER_ERROR://提供给 API 的无效参数
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_DEVELOPER_ERROR");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_ERROR://错误
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_ERROR");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_ITEM_ALREADY_OWNED://已经拥有该商品（未消耗掉)
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_ITEM_ALREADY_OWNED");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_ITEM_NOT_OWNED://没有当前商品（不可购买）
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_ITEM_NOT_OWNED");
                    break;
                case LTResultCode.STATE_GP_RESPONSE_RESULT_USER_CANCELED://用户取消支付
                    Log.e(TAG, "STATE_GP_RESPONSE_RESULT_USER_CANCELED");
                    break;
                case LTResultCode.STATE_CODE_PARAMETERS_ERROR://参数错误
                    Log.e(TAG, "STATE_CODE_PARAMETERS_ERROR");
                    break;
            }
        }
    };
}
