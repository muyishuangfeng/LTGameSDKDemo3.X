package com.gnetop.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gnetop.ltgame.core.exception.LTGameError;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.model.LoginResult;


public class QQActivity extends AppCompatActivity {

    Button mBtnLogin, mBtnLoginOut;
    TextView mTxtResult;
    String TAG = "QQActivity";
    private OnLoginStateListener mOnLoginListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        initView();
        initData();
    }

    private void initView() {
        //LoginEventManager.getInstance().qqInit(this, true, true);

        mTxtResult = findViewById(R.id.txt_result);
        mBtnLoginOut = findViewById(R.id.btn_loginOut);
        mBtnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // LoginEventManager.getInstance().qqLogin(QQActivity.this, true, mOnLoginListener);
            }
        });
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // LoginEventManager.getInstance().qqLogin(QQActivity.this, false, mOnLoginListener);
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
                    case LoginResult.STATE_SUCCESS:
                        Log.e(TAG, result.getResultModel().toString());
                        mTxtResult.setText(result.getResultModel().toString());
                        break;
                    case LoginResult.STATE_CANCEL:
                        Log.e(TAG, "STATE_FAIL");
                        break;
                    case LoginResult.STATE_FAIL:
                        if (result.getError() != null) {
//                            switch (result.getError().getCode()) {
//                                case LTGameError.CODE_PARAM_ERROR: {
//                                    Log.e("RESULT123", result.getError().getMsg());
//                                    break;
//                                }
//                                case LTGameError.CODE_REQUEST_ERROR: {
//                                    Log.e("RESULT123", "CODE_REQUEST_ERROR" + result.getError().getMsg());
//                                    break;
//                                }
//                                case LTGameError.CODE_NOT_SUPPORT: {
//                                    Log.e("RESULT123", "CODE_NOT_SUPPORT" + result.getError().getMsg());
//                                    break;
//                                }
//                            }
                        }
                        break;
                }
            }

            @Override
            public void onLoginOut() {

            }
        };
    }


}
