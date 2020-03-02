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


public class PhoneActivity extends AppCompatActivity {


    Button mBtnLogin, mBtnRegister, mBtnChange;
    TextView mTxtResult;
    String TAG = "PhoneActivity";
    private OnLoginStateListener mOnLoginListener;
    String mPhone = "18302949079";
    String mPassword = "123456789";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
        initData();
    }

    private void initView() {
        //LoginEventManager.getInstance().phoneInit(this, true, true);

        mTxtResult = findViewById(R.id.txt_result);
        mBtnChange = findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginEventManager.getInstance().phoneLogin(PhoneActivity.this, mPhone, mPassword, "3", mOnLoginListener);

            }
        });
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginEventManager.getInstance().phoneLogin(PhoneActivity.this, mPhone, mPassword, "2", mOnLoginListener);
            }
        });
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginEventManager.getInstance().phoneLogin(PhoneActivity.this, mPhone, mPassword, "1", mOnLoginListener);
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

                    case LoginResult.STATE_FAIL:
                        if (result.getError() != null) {
                            switch (result.getError().getCode()) {
//                                case LTGameError.CODE_PARAM_ERROR:
//                                case LTGameError.CODE_REQUEST_ERROR:
//                                case LTGameError.CODE_NOT_SUPPORT: {
//                                    Log.e("RESULT123", result.getError().getMsg());
//                                    break;
//                                }
                            }
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
