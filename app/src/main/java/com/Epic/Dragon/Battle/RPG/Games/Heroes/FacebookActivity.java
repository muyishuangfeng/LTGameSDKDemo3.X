package com.Epic.Dragon.Battle.RPG.Games.Heroes;

import android.app.Activity;
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


public class FacebookActivity extends AppCompatActivity {

    Button mBtnStart, mBtnLoginOut, mBtnBind;
    TextView mTxtResult;
    String TAG = "FacebookActivity";
    private OnLoginStateListener mOnLoginListener;


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


        mTxtResult = findViewById(R.id.txt_result);
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.FB_LOGIN);
                mRequest.setType(Constants.FB_LOGIN);
                LTGameSDK.getDefaultInstance().login(
                        FacebookActivity.this, mRequest, mOnLoginListener);

            }
        });
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.FB_LOGIN);
                mRequest.setType(Constants.FB_LOGIN_OUT);
                LTGameSDK.getDefaultInstance().login(
                        FacebookActivity.this, mRequest, mOnLoginListener);

            }
        });
        mBtnBind = findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.FB_LOGIN);
                mRequest.setType(Constants.FB_BIND);
                LTGameSDK.getDefaultInstance().login(
                        FacebookActivity.this, mRequest, mOnLoginListener);

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
                        Log.e("TAG", "STATE_FB_LOGIN_FAILED==========" + result.getMsg());

                        break;
                    case LTResultCode.STATE_FB_BIND_FAILED:
                        Log.e("TAG", "STATE_FB_BIND_FAILED==========" + result.getMsg());

                        break;
                    case LTResultCode.STATE_FB_BIND_SUCCESS:
                        Log.e("TAG", "STATE_FB_BIND_SUCCESS==========" + result.getMsg());
                        break;
                    case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                        Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR==========" + result.getMsg());
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
