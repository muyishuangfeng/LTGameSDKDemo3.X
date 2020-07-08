package com.Epic.Dragon.Battle.RPG.Games.Heroes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LTGameSDK;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.util.PreferencesUtils;


public class MainActivity extends AppCompatActivity {

    Button mBtnGoogle, mBtnFB, mBtnGP, mBtnGuest, mBtnUI, mBtnEmail, mBtnPortUI, mBtnUploadRole,
            mBtnQQ, mBtnWX, mBtnAuto;
    TextView mTxtResult;

    private static final String mLtAppID = "4";
    private static final String mAuthID = "298347680331-nu3cd65dtgf26kf9nvv59qebe39gvo5v.apps.googleusercontent.com";
    private static final String mFacebookId = "251194162797290";
    private static final String mAgreementUrl = "http://www.baidu.com";
    private static final String mProvacyUrl = "http://www.baidu.com";
    private static final String QQ_APP_ID = "1108097616";
    private static final String wxAppID = "wx12163d2efc218137";
    private static final String wxSecret = "55210ccc632b6273f7c9e995346dc211";
    private static final String mCountryModel = Constants.LT_SDK_COUNTRY_ABROAD;
    private int mPayTest = 1;
    static LoginObject mRequest;
    private boolean debug = true;
    private String isServerTest = Constants.LT_SERVER_TEST;
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
        //TODO:设置是否是沙盒
        mRequest.setPayTest(mPayTest);
        mRequest.setServerTest(isServerTest);
        mRequest.setDebug(debug);
        mRequest.setmGoogleClient(mAuthID);
        mRequest.setLTAppID(mLtAppID);
        mRequest.setPrivacyUrl(mProvacyUrl);
        mRequest.setAgreementUrl(mAgreementUrl);
        mRequest.setQqAppID(QQ_APP_ID);
        mRequest.setWxAppID(wxAppID);
        mRequest.setAppSecret(wxSecret);
        mRequest.setCountryModel(mCountryModel);
        mRequest.setLoginOut(false);
        LTGameSDK.getDefaultInstance().init(this, mRequest);

        Log.e("TSA", PreferencesUtils.getString(this, Constants.LT_SDK_APP_ID) + "==" +
                PreferencesUtils.getString(this, Constants.LT_SDK_DEVICE_ADID) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_SERVER_TEST_TAG) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_FB_APP_ID) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_PROVACY_URL) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_AGREEMENT_URL) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_QQ_APP_ID) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_WX_SECRET_KEY) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_WX_APP_ID) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_COUNTRY_MODEL) + "===" +
                PreferencesUtils.getString(this, Constants.LT_SDK_GOOGLE_CLIENT_ID));
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
        mBtnQQ = findViewById(R.id.btn_qq);
        mBtnQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QQActivity.class));
            }
        });
        mBtnWX = findViewById(R.id.btn_wx);
        mBtnWX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WeChatActivity.class));
            }
        });
        mBtnUploadRole = findViewById(R.id.btn_upload);
        mBtnUploadRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = new LoginObject();
                //角色创建时间，时间戳 精确到秒
                mRequest.setRole_create_time(1594063352);
                //角色编号（游戏服务器用户ID）
                mRequest.setRole_number(2000062);
                //角色名称
                mRequest.setRole_name("WrightDaisy");
                //角色性别 0女 1男 2 未知
                mRequest.setRole_sex("1");
                //服务器编号
                mRequest.setServer_number(2);
                //角色等级
                mRequest.setRole_level("25");
//                //角色创建时间，时间戳 精确到秒
//                mRequest.setRole_create_time(1593811595);
//                //角色编号（游戏服务器用户ID）
//                mRequest.setRole_number(1000005);
//                //角色名称
//                mRequest.setRole_name("MorganLucy");
//                //角色性别 0女 1男 2 未知
//                mRequest.setRole_sex("1");
//                //服务器编号
//                mRequest.setServer_number(1);
//                //角色等级
//                mRequest.setRole_level("32");
                LTGameSDK.getDefaultInstance().uploadRole(MainActivity.this, mRequest,
                        mOnStateListener);
            }
        });
        mBtnAuto = findViewById(R.id.btn_auto);
        mBtnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LTGameSDK.getDefaultInstance().autoLogin(MainActivity.this, mOnStateListener);
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
                        Log.e("TAG", "STATE_ROLE_UPLOAD_FAILED"+result.getMsg());
                        break;

                    case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                        Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR=========="+result.getMsg());
                        break;
                    case LTResultCode.STATE_AUTO_LOGIN_SUCCESS:
                        mTxtResult.setText(result.getResultModel().getData().toString());
                        break;
                    case LTResultCode.STATE_AUTO_LOGIN_FAILED:
                        Log.e("TAG", "STATE_AUTO_LOGIN_FAILED=========="+result.getMsg());
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
