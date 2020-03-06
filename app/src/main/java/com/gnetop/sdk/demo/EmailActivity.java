package com.gnetop.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LTGameSDK;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.util.PreferencesUtils;


public class EmailActivity extends AppCompatActivity {

    //当前包名
    Button mBtnStart, mBtnLogin, mBtnBind;
    TextView mTxtResult;
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
    EditText mEdtEmail;


    private OnLoginStateListener mOnLoginListener;
    String mLtToken;
    int mLtId;
    LoginObject mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        initView();
        initData();
    }

    private void initView() {
        mEdtEmail = findViewById(R.id.edt_email);

        PreferencesUtils.init(this);
        Log.e("TAG", "ukey===" + PreferencesUtils.getString(this, Constants.USER_LT_UID_KEY));
        mRequest = new LoginObject();
        mRequest.setFacebookAppID(mFacebookId);
        mRequest.setmGoogleClient(mAuthID);
        mRequest.setLTAppID(mLtAppID);
        mRequest.setLoginType(Constants.EMAIL_LOGIN);
        mRequest.setType(Constants.EMAIL_LOGIN);
        LTGameSDK.getDefaultInstance().init(this, true, true, mRequest);


        mTxtResult = findViewById(R.id.txt_result);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest.setLoginType(Constants.EMAIL_LOGIN);
                mRequest.setType(Constants.EMAIL_GET_CODE);
                mRequest.setEmail(EMAIL);
                LTGameSDK.getDefaultInstance().login(EmailActivity.this,  mRequest, mOnLoginListener);

            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest.setLoginType(Constants.EMAIL_LOGIN);
                mRequest.setType(Constants.EMAIL_LOGIN);
                mRequest.setAuthCode(mEdtEmail.getText().toString().trim());
                Log.e("TAG",mEdtEmail.getText().toString());
                LTGameSDK.getDefaultInstance().login(EmailActivity.this, mRequest, mOnLoginListener);

            }
        });

        mBtnBind = findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest.setLoginType(Constants.EMAIL_LOGIN);
                mRequest.setType(Constants.EMAIL_BIND);
                mRequest.setAuthCode(mEdtEmail.getText().toString().trim());
                LTGameSDK.getDefaultInstance().login(EmailActivity.this,  mRequest, mOnLoginListener);

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
                    case LTResultCode.STATE_EMAIL_LOGIN_SUCCESS:
                        Log.e("TAG", "STATE_EMAIL_LOGIN_SUCCESS");
                        if (result.getResultModel() != null) {
                            mLtToken = result.getResultModel().getData().getUkey();
                            mLtId = result.getResultModel().getData().getUser_id();
                            mTxtResult.setText(mLtId + "====" + mLtToken);
                        }
                        break;
                    case LTResultCode.STATE_EMAIL_BIND_SUCCESS:
                        Log.e("TAG", "STATE_EMAIL_BIND_SUCCESS");
                        if (result.getResultModel() != null) {
                            mLtToken = result.getResultModel().getData().getUkey();
                            mLtId = result.getResultModel().getData().getUser_id();
                            mTxtResult.setText(mLtId + "====" + mLtToken);
                        }
                        break;
                    case LTResultCode.STATE_EMAIL_ALREADY_BIND:
                        Log.e("TAG", "STATE_EMAIL_ALREADY_BIND==========");
                        break;
                    case LTResultCode.STATE_EMAIL_BIND_FAILED:
                        Log.e("TAG", "STATE_EMAIL_BIND_FAILED==========");
                        break;
                    case LTResultCode.STATE_EMAIL_LOGIN_FAILED:
                        Log.e("TAG", "STATE_EMAIL_LOGIN_FAILED==========");
                        break;
                    case LTResultCode.STATE_EMAIL_GET_CODE_FAILED:
                        Log.e("TAG", "STATE_EMAIL_GET_CODE_FAILED==========");
                        break;
                    case LTResultCode.STATE_EMAIL_GET_CODE_SUCCESS:
                        Log.e("TAG", "STATE_EMAIL_GET_CODE_SUCCESS==========");
                        break;
                    case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                        Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR==========");
                        break;

                }


            }

            @Override
            public void onLoginOut() {
                Log.e("TAG", "onLoginOut");
            }
        };
    }


}
