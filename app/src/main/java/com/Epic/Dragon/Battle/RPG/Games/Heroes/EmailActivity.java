package com.Epic.Dragon.Battle.RPG.Games.Heroes;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LTGameSDK;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;


public class EmailActivity extends AppCompatActivity {

    //当前包名
    Button mBtnStart, mBtnLogin, mBtnBind;
    TextView mTxtResult;
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


        mTxtResult = findViewById(R.id.txt_result);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.EMAIL_LOGIN);
                mRequest.setType(Constants.EMAIL_GET_CODE);
                mRequest.setEmail(EMAIL);
                LTGameSDK.getDefaultInstance().login(EmailActivity.this, mRequest, mOnLoginListener);

            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.EMAIL_LOGIN);
                mRequest.setType(Constants.EMAIL_LOGIN);
                mRequest.setAuthCode(mEdtEmail.getText().toString().trim());
                Log.e("TAG", mEdtEmail.getText().toString());
                LTGameSDK.getDefaultInstance().login(EmailActivity.this, mRequest, mOnLoginListener);

            }
        });

        mBtnBind = findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.EMAIL_LOGIN);
                mRequest.setType(Constants.EMAIL_BIND);
                mRequest.setAuthCode(mEdtEmail.getText().toString().trim());
                LTGameSDK.getDefaultInstance().login(EmailActivity.this, mRequest, mOnLoginListener);

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
                        Log.e("TAG", "STATE_EMAIL_ALREADY_BIND==========" + result.getMsg());
                        break;
                    case LTResultCode.STATE_EMAIL_BIND_FAILED:
                        Log.e("TAG", "STATE_EMAIL_BIND_FAILED==========" + result.getMsg());
                        break;
                    case LTResultCode.STATE_EMAIL_LOGIN_FAILED:
                        Log.e("TAG", "STATE_EMAIL_LOGIN_FAILED==========" + result.getMsg());
                        break;
                    case LTResultCode.STATE_EMAIL_GET_CODE_FAILED:
                        Log.e("TAG", "STATE_EMAIL_GET_CODE_FAILED==========" + result.getMsg());
                        break;
                    case LTResultCode.STATE_EMAIL_GET_CODE_SUCCESS:
                        Log.e("TAG", "STATE_EMAIL_GET_CODE_SUCCESS==========" + result.getMsg());
                        break;
                    case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                        Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR==========" + result.getMsg());
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
