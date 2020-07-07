package com.Epic.Dragon.Battle.RPG.Games.Heroes;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


public class UIActivity extends AppCompatActivity {

    Button mBtnLogin, mBtnLoginOut;
    TextView mTxtResult;
    String mLtToken;
    int mLtId;
    LoginObject mRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        initView();
    }


    protected void initView() {

        mTxtResult = findViewById(R.id.txt_result);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest=MainActivity.mRequest;
                mRequest.setLoginType(Constants.UI_LOGIN);
                LTGameSDK.getDefaultInstance().login(UIActivity.this,  mRequest, mOnLoginListener);
            }
        });
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest=MainActivity.mRequest;
                mRequest.setLoginType(Constants.UI_LOGIN_OUT);
                LTGameSDK.getDefaultInstance().login(UIActivity.this,  mRequest, mOnLoginListener);

            }
        });
    }

    OnLoginStateListener mOnLoginListener = new OnLoginStateListener() {
        @Override
        public void onState(Activity activity, LoginResult result) {
            switch (result.state) {
                case LTResultCode.STATE_GOOGLE_LOGIN_SUCCESS:
                    Log.e("TAGUI", "googleLogin");
                    if (result.getResultModel() != null) {
                        mLtToken = result.getResultModel().getData().getUkey();
                        mLtId = result.getResultModel().getData().getUser_id();
                        mTxtResult.setText(mLtId + "====" + mLtToken);
                    }
                    break;
                case LTResultCode.STATE_GOOGLE_BIND_SUCCESS:
                    Log.e("TAGUI", "STATE_GOOGLE_BIND_SUCCESS");
                    if (result.getResultModel() != null) {
                        mLtToken = result.getResultModel().getData().getUkey();
                        mLtId = result.getResultModel().getData().getUser_id();
                        mTxtResult.setText(mLtId + "====" + mLtToken);
                    }
                    break;
                case LTResultCode.STATE_UI_LOGIN_SUCCESS:
                    Log.e("TAGUI", "STATE_UI_LOGIN_SUCCESS");
                    if (result.getResultModel() != null) {
                        mLtToken = result.getResultModel().getData().getUkey();
                        mLtId = result.getResultModel().getData().getUser_id();
                        mTxtResult.setText(mLtId + "====" + mLtToken);
                    }
                    break;
                case LTResultCode.STATE_GOOGLE_BIND_FAILED:
                    Log.e("TAGUI", "STATE_GOOGLE_BIND_FAILED=========="+result.getMsg());
                    break;
                case LTResultCode.STATE_GOOGLE_LOGIN_FAILED:
                    Log.e("TAGUI", "STATE_GOOGLE_LOGIN_FAILED=========="+result.getMsg());
                    break;
                case LTResultCode.STATE_AUTO_LOGIN_SUCCESS:
                    mTxtResult.setText(result.getResultModel().toString());
                    Log.e("TAGUI", "STATE_AUTO_LOGIN_SUCCESS==========");
                    break;
                case LTResultCode.STATE_FB_LOGIN_SUCCESS:
                    if (result.getResultModel() != null) {
                        Log.e("TAGUI", "STATE_FB_LOGIN_SUCCESS" + result.getResultModel().toString());
                        mTxtResult.setText(result.getResultModel().toString());
                    }
                    break;
                case LTResultCode.STATE_FB_CANCEL_CODE:
                    Log.e("TAGUI", "STATE_FB_CANCEL_CODE==========");
                    if (result.getError().getMsg() != null) {
                        Toast.makeText(UIActivity.this, result.getError().getMsg(), Toast.LENGTH_SHORT).show();
                        mTxtResult.setText(result.getError().getMsg());
                    }
                    break;
                case LTResultCode.STATE_FB_LOGIN_FAILED:
                    Log.e("TAGUI", "STATE_FB_LOGIN_FAILED=========="+result.getMsg());

                    break;
                case LTResultCode.STATE_FB_BIND_FAILED:
                    Log.e("TAGUI", "STATE_FB_BIND_FAILED=========="+result.getMsg());

                    break;
                case LTResultCode.STATE_FB_BIND_SUCCESS:
                    Log.e("TAGUI", "STATE_FB_BIND_SUCCESS==========");

                    break;
                case LTResultCode.STATE_GUEST_LOGIN_SUCCESS:
                    Log.e("TAGUI", "STATE_GUEST_LOGIN_SUCCESS==========");
                    if (result.getResultModel() != null) {
                        String mLtToken = result.getResultModel().getData().getUkey();
                        int mLtId = result.getResultModel().getData().getUser_id();
                        mTxtResult.setText(mLtId + "====" + mLtToken);

                    }
                    break;
                case LTResultCode.STATE_GUEST_LOGIN_FAILED:
                    Log.e("TAGUI", "STATE_GUEST_LOGIN_FAILED=========="+result.getMsg());
                    break;
                case LTResultCode.STATE_EMAIL_LOGIN_SUCCESS:
                    Log.e("TAGUI", "STATE_EMAIL_LOGIN_SUCCESS");
                    if (result.getResultModel() != null) {
                        mLtToken = result.getResultModel().getData().getUkey();
                        mLtId = result.getResultModel().getData().getUser_id();
                        mTxtResult.setText(mLtId + "====" + mLtToken);
                    }
                    break;
                case LTResultCode.STATE_EMAIL_BIND_SUCCESS:
                    Log.e("TAGUI", "STATE_EMAIL_BIND_SUCCESS");
                    if (result.getResultModel() != null) {
                        mLtToken = result.getResultModel().getData().getUkey();
                        mLtId = result.getResultModel().getData().getUser_id();
                        mTxtResult.setText(mLtId + "====" + mLtToken);
                    }
                    break;
                case LTResultCode.STATE_EMAIL_ALREADY_BIND:
                    Log.e("TAGUI", "STATE_EMAIL_ALREADY_BIND=========="+result.getMsg());
                    break;
                case LTResultCode.STATE_EMAIL_BIND_FAILED:
                    Log.e("TAGUI", "STATE_EMAIL_BIND_FAILED=========="+result.getMsg());
                    break;
                case LTResultCode.STATE_EMAIL_LOGIN_FAILED:
                    Log.e("TAGUI", "STATE_EMAIL_LOGIN_FAILED=========="+result.getMsg());
                    break;
                case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                    Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR=========="+result.getMsg());
                    break;
            }
        }

        @Override
        public void onLoginOut() {
            Log.e("TAGUI", "onLoginOut==========");

        }
    };


}
