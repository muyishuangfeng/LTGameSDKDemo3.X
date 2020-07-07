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


public class QQActivity extends AppCompatActivity {

    Button mBtnLogin, mBtnBind, mBtnFB;
    TextView mTxtResult;
    private OnLoginStateListener mOnLoginListener;
    LoginObject mRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        initView();
        initData();
    }

    private void initView() {
        mTxtResult = findViewById(R.id.txt_result);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.QQ_LOGIN);
                mRequest.setType(Constants.QQ_LOGIN);
                mRequest.setLoginOut(false);
                LTGameSDK.getDefaultInstance().login(QQActivity.this, mRequest,
                        mOnLoginListener);


            }
        });
        mBtnFB = findViewById(R.id.btn_bind_fb);
        mBtnFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRequest = MainActivity.mRequest;
                mRequest.setLoginType(Constants.QQ_LOGIN);
                mRequest.setType(Constants.QQ_BIND);
                mRequest.setLoginOut(false);
                LTGameSDK.getDefaultInstance().login(QQActivity.this, mRequest,
                        mOnLoginListener);
            }
        });
        mBtnBind = findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    case LTResultCode.STATE_QQ_LOGIN_SUCCESS:
                        Log.e("TAG", "STATE_QQ_LOGIN_SUCCESS==========");
                        if (result.getResultModel() != null) {
                            String mLtToken = result.getResultModel().getData().getUkey();
                            int mLtId = result.getResultModel().getData().getUser_id();
                            mTxtResult.setText(mLtId + "====" + mLtToken);

                        }
                        break;
                    case LTResultCode.STATE_QQ_LOGIN_FAILED:
                        Log.e("TAG", "STATE_QQ_LOGIN_FAILED=========="+result.getMsg());
                        break;
                    case LTResultCode.STATE_QQ_BIND_FAILED:
                        Log.e("TAG", "STATE_QQ_BIND_FAILED=========="+result.getMsg());
                        break;
                    case LTResultCode.STATE_QQ_BIND_SUCCESS:
                        Log.e("TAG", "STATE_QQ_BIND_SUCCESS==========");
                        break;
                    case LTResultCode.STATE_CODE_PARAMETERS_ERROR:
                        Log.e("TAG", "STATE_CODE_PARAMETERS_ERROR=========="+result.getMsg());
                        break;
                }
            }

            @Override
            public void onLoginOut() {

            }
        };
    }


}
