package com.gnetop.ltgame.core.widget.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gnetop.ltgame.core.R;
import com.gnetop.ltgame.core.base.BaseFragment;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.ui.ProgressView;


public class LoginFailedFragment extends BaseFragment {

    LinearLayout mLytFailed;
    String mAgreementUrl;
    String mPrivacyUrl;
    String googleClientID;
    String LTAppID;
    String mAdID;
    String mFacebookID;
    boolean mServerTest;
    boolean mIsLoginOut;
    boolean mIsBind;
    ProgressView mPgbLoading;
    TextView mTxtTips, mTxtContent;
    LoginObject mData;

    public static LoginFailedFragment newInstance(LoginObject data) {
        Bundle args = new Bundle();
        LoginFailedFragment fragment = new LoginFailedFragment();
        args.putSerializable(ARG_NUMBER, data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentId() {
        return R.layout.fragment_login_failed;
    }

    @Override
    protected void initView(View view) {
        mLytFailed = view.findViewById(R.id.lyt_login_failed);
        mPgbLoading = view.findViewById(R.id.pgb_loading);
        mTxtTips = view.findViewById(R.id.txt_login_failed_tips);
        mTxtContent = view.findViewById(R.id.txt_login_failed_content);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        Bundle args = getArguments();
        if (args != null) {
             mData = (LoginObject) args.getSerializable(ARG_NUMBER);
            if (mData != null) {
                mAgreementUrl = mData.getAgreementUrl();
                mPrivacyUrl = mData.getPrivacyUrl();
                googleClientID = mData.getmGoogleClient();
                LTAppID = mData.getLTAppID();
                mAdID = mData.getmAdID();
                mFacebookID = mData.getFacebookAppID();
                mServerTest = mData.isServerTest();
                mIsLoginOut = mData.isLoginOut();
                mIsBind = mData.ismBind();
                initData(mPrivacyUrl, mAgreementUrl);
            }
        }
        if (mIsBind) {
            mTxtTips.setText(getResources().getString(R.string.text_bind_failed));
            mTxtContent.setText(getResources().getString(R.string.text_bind_content));
        } else {
            mTxtTips.setText(getResources().getString(R.string.text_login_way));
            mTxtContent.setText(getResources().getString(R.string.text_login_failed));
        }
    }

    /**
     * 跳转
     */
    private void initData(final String mPrivacyUrl, final String mAgreementUrl) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginObject data = new LoginObject();
                data.setPrivacyUrl(mPrivacyUrl);
                data.setAgreementUrl(mAgreementUrl);
                data.setLTAppID(LTAppID);
                data.setmGoogleClient(googleClientID);
                data.setmAdID(mAdID);
                data.setFacebookAppID(mFacebookID);
                data.setServerTest(mServerTest);
                data.setLoginOut(mIsLoginOut);
                getProxyActivity().addFragment(LoginUIFragment.newInstance(data),
                        false,
                        true);
            }
        }, 2000);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPgbLoading.stopAnimation();
    }
}