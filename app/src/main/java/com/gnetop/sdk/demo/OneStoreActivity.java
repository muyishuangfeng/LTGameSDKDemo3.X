package com.gnetop.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.gnetop.ltgame.core.impl.OnRechargeStateListener;
import com.gnetop.ltgame.core.model.RechargeResult;

import java.util.Map;
import java.util.WeakHashMap;


public class OneStoreActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    Map<String, Object> params = new WeakHashMap<>();
    String mSKU = "com.ltgamesyyjw.lslb1";
    private static final String TAG = OneStoreActivity.class.getSimpleName();
    String mGoodsID = "11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_store);
        initView();
        init();
    }


    private void initView() {
        //LoginEventManager.getInstance().oneStoreInit(this, true, true);

        params.put("key", "123");
        mTxtResult = findViewById(R.id.txt_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LoginEventManager.getInstance().oneStoreRecharge(OneStoreActivity.this, mSKU, mGoodsID, params,
//                        0, mOnRechargeListener);
            }
        });
    }

    private void init() {

    }

    OnRechargeStateListener mOnRechargeListener = new OnRechargeStateListener() {
        @Override
        public void onState(Activity activity, RechargeResult result) {
            switch (result.state) {
                case RechargeResult.STATE_RECHARGE_SUCCESS:
                    mTxtResult.setText(result.getResultModel().getCode() + "======");
                    break;
                case RechargeResult.STATE_RECHARGE_START:
                    Log.e(TAG, "开始支付");
                    break;
                case RechargeResult.STATE_RECHARGE_RESULT:
                    switch (result.getResult()) {
                        case RESULT_BILLING_NEED_UPDATE: {
                            Log.e(TAG, "RESULT_BILLING_NEED_UPDATE");
                            break;
                        }
                        case RESULT_CLIENT_UN_CONNECTED: {
                            Log.e(TAG, "RESULT_CLIENT_UN_CONNECTED");
                            break;
                        }
                        case RESULT_CLIENT_CONNECTED: {
                            Log.e(TAG, "RESULT_CLIENT_CONNECTED");
                            break;
                        }
                        case RESULT_PURCHASES_REMOTE_ERROR: {
                            Log.e(TAG, "RESULT_PURCHASES_REMOTE_ERROR");
                            break;
                        }
                        case RESULT_PURCHASES_SECURITY_ERROR: {
                            Log.e(TAG, "RESULT_PURCHASES_SECURITY_ERROR");
                            break;
                        }
                        case RESULT_CLIENT_NOT_INIT: {
                            Log.e(TAG, "RESULT_CLIENT_NOT_INIT");
                            break;
                        }
                        case RESULT_BILLING_OK: {
                            Log.e(TAG, "RESULT_BILLING_OK");
                            break;
                        }
                        case RESULT_CONNECTED_NEED_UPDATE: {
                            Log.e(TAG, "RESULT_BILLING_OK");
                            break;
                        }
                        case RESULT_BILLING_REMOTE_ERROR: {
                            Log.e(TAG, "RESULT_BILLING_REMOTE_ERROR");
                            break;
                        }
                        case RESULT_BILLING_SECURITY_ERROR: {
                            Log.e(TAG, "RESULT_BILLING_SECURITY_ERROR");
                            break;
                        }
                        case IAP_ERROR_UNDEFINED_CODE: {
                            Log.e(TAG, "IAP_ERROR_UNDEFINED_CODE");
                            break;
                        }
                    }
                    break;
                case RechargeResult.STATE_RECHARGE_FAILED:
                    Log.e(TAG, "支付错误" + result.getErrorMsg());
                    break;

            }
        }

    };
}
