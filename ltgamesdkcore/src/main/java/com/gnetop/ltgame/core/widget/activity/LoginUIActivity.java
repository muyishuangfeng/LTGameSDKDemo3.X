package com.gnetop.ltgame.core.widget.activity;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;

import com.gnetop.ltgame.core.R;
import com.gnetop.ltgame.core.base.BaseAppActivity;
import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.impl.OnResultClickListener;
import com.gnetop.ltgame.core.manager.ui.LoginUIManager;
import com.gnetop.ltgame.core.model.BundleData;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.model.ResultModel;
import com.gnetop.ltgame.core.util.PreferencesUtils;
import com.gnetop.ltgame.core.widget.fragment.AgreementFragment;
import com.gnetop.ltgame.core.widget.fragment.LoginUIFragment;

public class LoginUIActivity extends BaseAppActivity {


    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }


    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra("bundleData");
        String mAgreementUrl = bundle.getString("mAgreementUrl");
        String mPrivacyUrl = bundle.getString("mPrivacyUrl");
        String googleClientID = bundle.getString("googleClientID");
        String LTAppID = bundle.getString("LTAppID");
        boolean mServerTest = bundle.getBoolean("mServerTest");
        String mFacebookID = bundle.getString("mFacebookID");
        final boolean mIsLoginOut = bundle.getBoolean("mIsLoginOut");

        LoginObject data = new LoginObject();
        data.setAgreementUrl(mAgreementUrl);
        data.setPrivacyUrl(mPrivacyUrl);
        data.setmGoogleClient(googleClientID);
        data.setLTAppID(LTAppID);
        data.setServerTest(mServerTest);
        data.setFacebookAppID(mFacebookID);
        data.setLoginOut(mIsLoginOut);

        if (!TextUtils.isEmpty(mAgreementUrl) &&
                !TextUtils.isEmpty(mPrivacyUrl)) {
            if (TextUtils.isEmpty(PreferencesUtils.getString(this,
                    Constants.USER_AGREEMENT_FLAT))) {
                if (findFragment(AgreementFragment.class) == null) {
                    addFragment(AgreementFragment.newInstance(data),
                            false,
                            true);
                }
            } else {
                if (findFragment(LoginUIFragment.class) == null) {
                    LoginUIFragment fragment = LoginUIFragment.newInstance(data);
                    addFragment(fragment,
                            false,
                            true);
                }
            }

        }

    }

    @Override
    protected void initData() {
    }
}
