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
import com.gnetop.ltgame.core.manager.login.fb.FacebookEventManager;
import com.gnetop.ltgame.core.manager.lt.LTGameSDK;
import com.gnetop.ltgame.core.model.RechargeObject;
import com.gnetop.ltgame.core.model.RechargeResult;

import java.util.Map;
import java.util.WeakHashMap;


public class GooglePlayActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    private static final String TAG = GooglePlayActivity.class.getSimpleName();
    private String mGoodsID = "1";
    String mSKU = "com.gnetop.one";
    private static final String mGPPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAleVlYQtKhvo+lb83j73kXGH8xAMhHcaAZoS22Bo3Jdujix9Ou5DjtUW3i6MIFqWEbnb9da50iH5IrxkkdJCcqzeYDdLk2Y3Gc+kyaw5ch4I//hjC2hh8nHgo8eWfrxSFce/DpNBeS1j4mWcjWZhYJtxheEUk8iTyXIVWHC8dCyifibs7z8wCXMhy3Q66Zym5GarAYjpuQsXTxHuOYUXakLWCwIXG8d8ihoRxweI7PtLpVyNU5FKgse42uouMRz6TgVotgu+NdamNyTH/CutQMPGeNXUj6FpHUDEWQhsRp27k0KsA8YWJDJBj4R9bJ5GDqD8XJo2y5V7/vy1OH4afkQIDAQAB";
    RechargeObject mRequest;


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
                mRequest.setPayTest(1);
                mRequest.setSku(mSKU);
                mRequest.setGPPublicKey(mGPPublicKey);
                mRequest.setRole_number(1);
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
                case LTResultCode.STATE_GP_CREATE_ORDER_SUCCESS:
                    Log.e(TAG, "STATE_GP_CREATE_ORDER_SUCCESS");
                    break;
                case LTResultCode.STATE_GP_CREATE_ORDER_FAILED:
                    Log.e(TAG, "STATE_GP_CREATE_ORDER_FAILED");
                    break;
            }
        }
    };
}
