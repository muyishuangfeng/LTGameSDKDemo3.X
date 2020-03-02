package com.gnetop.ltgame.core.manager.login.google;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.gnetop.ltgame.core.base.BaseEntry;
import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTGameError;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LoginRealizeManager;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.model.ResultModel;
import com.gnetop.ltgame.core.platform.Target;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.lang.ref.WeakReference;


public class GoogleLoginHelper {
    private int mLoginTarget;
    private WeakReference<Activity> mActivityRef;
    private OnLoginStateListener mListener;
    private String clientID;
    int selfRequestCode;
    private String type;

    GoogleLoginHelper(Activity activity, String clientID, String type,
                      int selfRequestCode, OnLoginStateListener listener) {
        this.mActivityRef = new WeakReference<>(activity);
        this.clientID = clientID;
        this.selfRequestCode = selfRequestCode;
        this.mListener = listener;
        this.type = type;
        this.mLoginTarget = Target.LOGIN_GOOGLE;
    }


    void loginAction(String clientID) {
        if (!TextUtils.isEmpty(type)) {
            if (type.equals(Constants.GOOGLE_LOGIN_OUT)) {
                loginOut(mActivityRef.get(), clientID);
            } else {
                login();
            }
        }
    }


    /**
     * 登录
     */
    private void login() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mActivityRef.get(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mActivityRef.get().startActivityForResult(signInIntent, selfRequestCode);
    }

    /**
     * 登录回调
     */
    void onActivityResult(int requestCode, Intent data, int selfRequestCode) {
        if (requestCode == selfRequestCode) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                if (!TextUtils.isEmpty(type)) {
                    switch (type) {
                        case Constants.GOOGLE_LOGIN: //登录
                            LoginRealizeManager.googleLogin(mActivityRef.get(), account.getId(),
                                    account.getEmail(), account.getDisplayName(), mListener);
                            break;
                        case Constants.GOOGLE_BIND: //绑定
                            LoginRealizeManager.bindGoogle(mActivityRef.get(), account.getId(),
                                    account.getEmail(), account.getDisplayName(), mListener);
                            break;
                        case Constants.GOOGLE_UI_TOKEN: //获取token
                            BaseEntry<ResultModel> resultModelBaseEntry = new BaseEntry<>();
                            ResultModel model = new ResultModel();
                            model.setEmali(account.getEmail());
                            model.setId(account.getId());
                            model.setNickName(account.getDisplayName());
                            resultModelBaseEntry.setData(model);
                            mListener.onState(mActivityRef.get(), LoginResult.successOf(
                                    LTResultCode.STATE_GOOGLE_UI_TOKEN, resultModelBaseEntry));
                            break;
                    }
                }
            } else {
                mListener.onState(mActivityRef.get(),
                        LoginResult.failOf(LTGameError.make(LTResultCode.STATE_GOOGLE_COMMON_FAILED)));
            }

        } catch (ApiException e) {
            e.printStackTrace();
            mListener.onState(mActivityRef.get(),
                    LoginResult.failOf(LTGameError.make(LTResultCode.STATE_GOOGLE_COMMON_FAILED, e.getMessage())));
        }
    }

    /**
     * 退出登录
     */
    private void loginOut(Context context, String clientID) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientID)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mListener.onLoginOut();
                mActivityRef.get().finish();
            }
        });
    }


}