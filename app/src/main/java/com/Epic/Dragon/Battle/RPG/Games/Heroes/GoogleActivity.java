package com.Epic.Dragon.Battle.RPG.Games.Heroes;

import android.app.Activity;
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


public class GoogleActivity extends AppCompatActivity {

    //当前包名
    Button mBtnStart, mBtnLoginOut, mBtnAuto, mBtnBind;
    TextView mTxtResult;
    private OnLoginStateListener mOnLoginListener;
    String mLtToken;
    int mLtId;
    LoginObject mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnAuto = findViewById(R.id.btn_auto);
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest=MainActivity.mRequest;
                mRequest.setLoginType(Constants.GOOGLE_LOGIN);
                mRequest.setType(Constants.GOOGLE_LOGIN);
                LTGameSDK.getDefaultInstance().login(GoogleActivity.this,
                         mRequest, mOnLoginListener);

            }
        });
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest=MainActivity.mRequest;
                mRequest.setLoginType(Constants.GOOGLE_LOGIN);
                mRequest.setType(Constants.GOOGLE_LOGIN_OUT);
                LTGameSDK.getDefaultInstance().login(GoogleActivity.this,
                         mRequest, mOnLoginListener);

            }
        });
        mBtnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LTGameSDK.getDefaultInstance().autoLogin(GoogleActivity.this,mOnLoginListener);
            }
        });

        mBtnBind = findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest=MainActivity.mRequest;
                mRequest.setLoginType(Constants.GOOGLE_LOGIN);
                mRequest.setType(Constants.GOOGLE_BIND);
                LTGameSDK.getDefaultInstance().login(GoogleActivity.this, mRequest, mOnLoginListener);

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
                    case LTResultCode.STATE_GOOGLE_LOGIN_SUCCESS:
                        Log.e("TAG", "googleLogin");
                        if (result.getResultModel() != null) {
                            mLtToken = result.getResultModel().getData().getUkey();
                            mLtId = result.getResultModel().getData().getUser_id();
                            mTxtResult.setText(mLtId + "====" + mLtToken);
                        }
                        break;
                    case LTResultCode.STATE_GOOGLE_BIND_SUCCESS:
                        Log.e("TAG", "STATE_GOOGLE_BIND_SUCCESS");
                        if (result.getResultModel() != null) {
                            mLtToken = result.getResultModel().getData().getUkey();
                            mLtId = result.getResultModel().getData().getUser_id();
                            mTxtResult.setText(mLtId + "====" + mLtToken);
                        }
                        break;
                    case LTResultCode.STATE_GOOGLE_ALREADY_BIND:
                        Log.e("TAG", "STATE_GOOGLE_ALREADY_BIND=========="+result.getMsg());
                        break;
                    case LTResultCode.STATE_GOOGLE_BIND_FAILED:
                        Log.e("TAG", "STATE_GOOGLE_BIND_FAILED=========="+result.getMsg());
                        break;
                    case LTResultCode.STATE_GOOGLE_LOGIN_FAILED:
                        Log.e("TAG", "STATE_GOOGLE_LOGIN_FAILED=========="+result.getMsg());
                        break;
                    case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                        Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR=========="+result.getMsg());
                        break;
                    case LTResultCode.STATE_AUTO_LOGIN_SUCCESS:
                        Log.e("TAG", "STATE_AUTO_LOGIN_SUCCESS=========="+result.getMsg());
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
