package com.gnetop.sdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LTGameSDK;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.util.PreferencesUtils;
import com.gnetop.sdk.demo.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    Button mBtnGoogle, mBtnFB, mBtnGP, mBtnGuest, mBtnUI, mBtnEmail, mBtnPortUI,mBtnUploadRole;
    TextView mTxtResult;

    private static final String mLtAppID = "1";
    private static final String mAuthID = "443503959733-nlr4ofibakk0j2dqkkomdqu3uta50pbe.apps.googleusercontent.com";
    private static final String mFacebookId = "2717734461592670";
    private static final String mAgreementUrl = "http://www.baidu.com";
    private static final String mProvacyUrl = "http://www.baidu.com";
    private static final String mGPPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAleVlYQtKhvo+lb83j73kXGH8xAMhHcaAZoS22Bo3Jdujix9Ou5DjtUW3i6MIFqWEbnb9da50iH5IrxkkdJCcqzeYDdLk2Y3Gc+kyaw5ch4I//hjC2hh8nHgo8eWfrxSFce/DpNBeS1j4mWcjWZhYJtxheEUk8iTyXIVWHC8dCyifibs7z8wCXMhy3Q66Zym5GarAYjpuQsXTxHuOYUXakLWCwIXG8d8ihoRxweI7PtLpVyNU5FKgse42uouMRz6TgVotgu+NdamNyTH/CutQMPGeNXUj6FpHUDEWQhsRp27k0KsA8YWJDJBj4R9bJ5GDqD8XJo2y5V7/vy1OH4afkQIDAQAB";
    private static final String QQ_APP_ID = "1108097616";
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static LoginObject mRequest;
    private boolean debug=true;
    private String isServerTest=Constants.LT_SERVER_TEST;
    private OnLoginStateListener mOnStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        mRequest = new LoginObject();
        mRequest.setFBAppID(mFacebookId);
        mRequest.setServerTest(isServerTest);
        mRequest.setDebug(debug);
        mRequest.setmGoogleClient(mAuthID);
        mRequest.setLTAppID(mLtAppID);
        mRequest.setPrivacyUrl(mProvacyUrl);
        mRequest.setAgreementUrl(mAgreementUrl);
        mRequest.setGPPublicKey(mGPPublicKey);
        mRequest.setQqAppID(QQ_APP_ID);
        mRequest.setLoginOut(false);
        LTGameSDK.getDefaultInstance().init(this, mRequest);

        Log.e("TSA", PreferencesUtils.getString(this,Constants.LT_SDK_APP_ID)+"=="+
                PreferencesUtils.getString(this,Constants.LT_SDK_DEVICE_ADID)+"==="+
                PreferencesUtils.getString(this,Constants.LT_SDK_SERVER_TEST_TAG)+"==="+
                PreferencesUtils.getString(this,Constants.LT_SDK_FB_APP_ID)+"==="+
                PreferencesUtils.getString(this,Constants.LT_SDK_GP_PUBLIC_KEY)+"==="+
                PreferencesUtils.getString(this,Constants.LT_SDK_PROVACY_URL)+"==="+
                PreferencesUtils.getString(this,Constants.LT_SDK_AGREEMENT_URL)+"==="+
                PreferencesUtils.getString(this,Constants.LT_SDK_GOOGLE_CLIENT_ID));
        mTxtResult = findViewById(R.id.txt_result);
        mBtnGuest = findViewById(R.id.btn_guest);
        mBtnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GuestActivity.class));
            }
        });

        mBtnGoogle = findViewById(R.id.btn_google);
        mBtnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GoogleActivity.class));
            }
        });
        mBtnFB = findViewById(R.id.btn_fb);
        mBtnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FacebookActivity.class));
            }
        });
        mBtnGP = findViewById(R.id.btn_gp);
        mBtnGP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GooglePlayActivity.class));
            }
        });
        mBtnEmail = findViewById(R.id.btn_email);
        mBtnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EmailActivity.class));
            }
        });

        mBtnUI = findViewById(R.id.btn_ui);
        mBtnUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UIActivity.class));
            }
        });
        mBtnPortUI = findViewById(R.id.btn_ui_port);
        mBtnPortUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PortUIActivity.class));
            }
        });
        mBtnUploadRole = findViewById(R.id.btn_upload);
        mBtnUploadRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest=new LoginObject();
                mRequest.setRole_create_time(DateUtil.stringToLong(System.currentTimeMillis()+""));
                mRequest.setRole_number(1);
                mRequest.setRole_name("Silence");
                mRequest.setRole_sex("0");
                mRequest.setServer_number(1);
                mRequest.setRole_level("1");
                LTGameSDK.getDefaultInstance().uploadRole(MainActivity.this,mRequest,
                        mOnStateListener);
            }
        });


    }
    /**
     * 初始化数据
     */
    private void initData() {
        mOnStateListener = new OnLoginStateListener() {
            @Override
            public void onState(Activity activity, LoginResult result) {
                switch (result.state) {
                    case LTResultCode.STATE_ROLE_UPLOAD_SUCCESS:
                        Log.e("TAG", "STATE_ROLE_UPLOAD_SUCCESS");
                        break;
                    case LTResultCode.STATE_ROLE_UPLOAD_FAILED:
                        Log.e("TAG", "STATE_ROLE_UPLOAD_FAILED");
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
