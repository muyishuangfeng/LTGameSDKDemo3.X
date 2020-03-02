package com.gnetop.sdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;



public class WebActivity extends AppCompatActivity {

    Button mBtnStart;
    private String mAuthID = "443503959733-0vhjo7df08ahd9i7d5lj9mdtt7bahsbq.apps.googleusercontent.com";
    private static final int REQUEST_CODE = 0X01;
    private String mFacebookId = "2717734461592670";
    private static final String QQ_APP_ID = "1108097616";
    //QQUtil mQQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView() {
        //mQQ = new QQUtil(WebActivity.this, QQ_APP_ID);
        //mQQ.login(WebActivity.this);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GoogleLoginManager.initGoogle(WebActivity.this,mAuthID,REQUEST_CODE);
                //FBUtil.initFaceBook(WebActivity.this,mFacebookId,false);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // mQQ.onActivityResult(requestCode, resultCode, data);
        //GoogleLoginManager.onActivityResult(requestCode, data, REQUEST_CODE);
        //FBUtil.onActivityResult(requestCode,resultCode,data);
    }
}
