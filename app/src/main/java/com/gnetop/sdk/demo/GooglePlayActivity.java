package com.gnetop.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.gnetop.ltgame.core.impl.OnRechargeStateListener;
import com.gnetop.ltgame.core.manager.login.fb.FacebookEventManager;
import com.gnetop.ltgame.core.model.RechargeResult;

import java.util.Map;
import java.util.WeakHashMap;


public class GooglePlayActivity extends AppCompatActivity {

    Button mBtnPay;
    TextView mTxtResult;
    private static final String TAG = GooglePlayActivity.class.getSimpleName();
    private String mGoodsID = "138";
    String mSKU = "com.gnetop.one";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play);
        initView();
    }

    private void initView() {
        //LoginEventManager.getInstance().gpInit(this, true, true, 0);
        final Map<String, Object> params = new WeakHashMap<>();
        params.put("key", "123");
        mTxtResult = findViewById(R.id.txt_result);
        mBtnPay = findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LoginEventManager.getInstance().gpRecharge(GooglePlayActivity.this, mSKU, mGoodsID, params, 0,
//                        mOnRechargeListener);
            }
        });
    }


    OnRechargeStateListener mOnRechargeListener = new OnRechargeStateListener() {
        @Override
        public void onState(Activity activity, RechargeResult result) {
            switch (result.state) {
                case RechargeResult.STATE_RECHARGE_SUCCESS:
                    //TODO:支付统计方法
//                    FacebookEventManager.getInstance().recharge(GooglePlayActivity.this,
//                            result.getResultModel().getData().getLt_price(),
//                            result.getResultModel().getData().getLt_currency(),
//                            result.getResultModel().getData().getLt_order_id());
                    mTxtResult.setText(result.getResultModel().toString());
                    break;
                case RechargeResult.STATE_RECHARGE_START:
                    Log.e(TAG, "开始支付");
                    break;
                case RechargeResult.STATE_RECHARGE_FAILED:
                    switch (result.getErrorMsg()) {
                        case "1": {//取消购买
                            Log.e(TAG, "取消购买");
                            break;
                        }
                        case "2": {//网络异常
                            Log.e(TAG, "网络异常");
                            break;
                        }
                        case "3": {//不支持购买
                            Log.e(TAG, "不支持购买");
                            break;
                        }
                        case "4": {//商品不可购买
                            Log.e(TAG, "商品不可购买");
                            break;
                        }
                        case "5": {//提供给 API 的无效参数
                            Log.e(TAG, "无效参数");
                            break;
                        }
                        case "6": {//错误
                            Log.e(TAG, "错误");
                            break;
                        }
                        case "7": {//未消耗掉
                            Log.e(TAG, "未消耗掉");
                            break;
                        }
                        case "8": {//不可购买
                            Log.e(TAG, "不可购买");
                            break;
                        }
                    }
                    break;
            }
        }
    };
}
