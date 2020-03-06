package com.gnetop.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTGameError;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LTGameSDK;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;


public class FacebookActivity extends AppCompatActivity {

    Button mBtnStart, mBtnLoginOut,mBtnBind;
    TextView mTxtResult;
    String TAG = "FacebookActivity";
    private OnLoginStateListener mOnLoginListener;
    private String mLtAppID = "1";
    private String mAuthID = "443503959733-nlr4ofibakk0j2dqkkomdqu3uta50pbe.apps.googleusercontent.com";
    private String mFacebookId = "2717734461592670";
    private static final String mAgreementUrl = "http://www.baidu.com";
    private static final String mProvacyUrl = "http://www.baidu.com";
    private static final int REQUEST_CODE = 0X01;
    private static final String mGPPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAleVlYQtKhvo+lb83j73kXGH8xAMhHcaAZoS22Bo3Jdujix9Ou5DjtUW3i6MIFqWEbnb9da50iH5IrxkkdJCcqzeYDdLk2Y3Gc+kyaw5ch4I//hjC2hh8nHgo8eWfrxSFce/DpNBeS1j4mWcjWZhYJtxheEUk8iTyXIVWHC8dCyifibs7z8wCXMhy3Q66Zym5GarAYjpuQsXTxHuOYUXakLWCwIXG8d8ihoRxweI7PtLpVyNU5FKgse42uouMRz6TgVotgu+NdamNyTH/CutQMPGeNXUj6FpHUDEWQhsRp27k0KsA8YWJDJBj4R9bJ5GDqD8XJo2y5V7/vy1OH4afkQIDAQAB";
    private static final String mONEPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCu9RPDbvVqM8XWqVc75JXccIXN1VS8XViRZzATUq62kkFIXCeo52LKzBCh3iWFQIvX3jqDhim4ESqHMezEx8CxaTq8NpNoQXutBNmOEl+/7HTUsZxI93wgn9+7pFMyoFlasqmVjCcM7zbbAx5G0bySsm98TFxTu16OGmO01JGonQIDAQAB";
    private static final String QQ_APP_ID = "1108097616";
    private static final String EMAIL = "yangkemuyi@sina.com";


    String mLtToken;
    int mLtId;
    LoginObject mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_ui);
        initView();
        initData();
    }

    private void initView() {
        mRequest = new LoginObject();
        mRequest.setFacebookAppID(mFacebookId);
        mRequest.setmGoogleClient(mAuthID);
        mRequest.setLTAppID(mLtAppID);
        mRequest.setLoginType(Constants.FB_LOGIN);
        mRequest.setType(Constants.FB_LOGIN);
        LTGameSDK.getDefaultInstance().init(this, true, true, mRequest);


        mTxtResult = findViewById(R.id.txt_result);
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest.setType(Constants.FB_LOGIN);
                LTGameSDK.getDefaultInstance().login(
                        FacebookActivity.this,  mRequest, mOnLoginListener);

            }
        });
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest.setType(Constants.FB_LOGIN_OUT);
                LTGameSDK.getDefaultInstance().login(
                        FacebookActivity.this,  mRequest, mOnLoginListener);

            }
        });
        mBtnBind=findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest.setType(Constants.FB_BIND);
                LTGameSDK.getDefaultInstance().login(
                        FacebookActivity.this,  mRequest, mOnLoginListener);

            }
        });
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mOnLoginListener = new OnLoginStateListener() {
            @Override
            public void onState(Activity activity, LoginResult result) {
                switch (result.state) {
                    case LTResultCode.STATE_FB_LOGIN_SUCCESS:
                        if (result.getResultModel() != null) {
                            Log.e(TAG, result.getResultModel().toString());
                            mTxtResult.setText(result.getResultModel().toString());
                        }
                        break;
                    case LTResultCode.STATE_FB_CANCEL_CODE:
                        if (result.getError().getMsg() != null) {
                            Toast.makeText(FacebookActivity.this, result.getError().getMsg(), Toast.LENGTH_SHORT).show();
                            mTxtResult.setText(result.getError().getMsg());
                        }
                        break;
                    case LTResultCode.STATE_FB_LOGIN_FAILED:
                        Log.e("TAG", "STATE_FB_LOGIN_FAILED==========");

                        break;
                    case LTResultCode.STATE_FB_BIND_FAILED:
                        Log.e("TAG", "STATE_FB_BIND_FAILED==========");

                        break;
                    case LTResultCode.STATE_FB_BIND_SUCCESS:
                        Log.e("TAG", "STATE_FB_BIND_SUCCESS==========");
                        break;
                    case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                        Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR==========");
                        break;
                }
            }

            @Override
            public void onLoginOut() {
                Log.e("TAG", "onLoginOut==========fb");

            }
        };
    }


}
