package com.gnetop.ltgame.core.widget.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gnetop.ltgame.core.R;
import com.gnetop.ltgame.core.base.BaseFragment;
import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.ui.LoginUIManager;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.ui.dialog.GeneralCenterDialog;
import com.gnetop.ltgame.core.util.PreferencesUtils;


public class LoginUIFragment extends BaseFragment implements View.OnClickListener {

    LinearLayout mLytGoogle, mLytFaceBook;
    TextView mTxtGuest;
    String mAgreementUrl;
    String mPrivacyUrl;
    String googleClientID;
    String LTAppID;
    String mAdID;
    String mFacebookID;
    boolean mIsLoginOut;
    boolean mServerTest;
    LoginObject mData;
    private OnLoginStateListener mListener;
    GeneralCenterDialog mDialog;
    TextView mTxtEmail;


    public static LoginUIFragment newInstance(LoginObject data) {
        Bundle args = new Bundle();
        LoginUIFragment fragment = new LoginUIFragment();
        args.putSerializable(ARG_NUMBER, data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getFragmentId() {
        return R.layout.fragment_loign;
    }

    @Override
    protected void initView(View view) {
        mDialog = new GeneralCenterDialog(mActivity);

        mLytGoogle = view.findViewById(R.id.lyt_login_google);
        mLytGoogle.setOnClickListener(this);

        mTxtGuest = view.findViewById(R.id.txt_visitor);
        mTxtGuest.setOnClickListener(this);

        mTxtEmail = view.findViewById(R.id.txt_email);
        mTxtEmail.setOnClickListener(this);

        mLytFaceBook = view.findViewById(R.id.lyt_login_facebook);
        mLytFaceBook.setOnClickListener(this);
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
            }
        }
        initData();

    }

    @Override
    public void onClick(View view) {
        int resID = view.getId();
        if (resID == R.id.lyt_login_facebook) {//facebook
            LoginUIManager.getInstance().getFBInfo(mActivity, mData, mListener);
        } else if (resID == R.id.lyt_login_google) {//google
            LoginUIManager.getInstance().getGoogleInfo(mActivity, mData, mListener);
        } else if (resID == R.id.txt_visitor) {//游客登录
            if (!TextUtils.isEmpty(PreferencesUtils.getString(mActivity, Constants.USER_BIND_FLAG)) &&
                    TextUtils.equals(PreferencesUtils.getString(mActivity, Constants.USER_BIND_FLAG), "YES")) {//游客登录过
                guestTurn();
            } else {
                guestLogin();
            }
        } else if (resID == R.id.txt_email) {//邮箱登录
            emailLogin();
        }
    }


    /**
     * 初始化数据
     */
    private void initData() {
        mListener = new OnLoginStateListener() {
            @Override
            public void onState(Activity activity, LoginResult result) {
                switch (result.state) {
                    case LTResultCode.STATE_GOOGLE_UI_TOKEN: //google获取信息
                        if (result.getResultModel() != null) {
                            showDialog(getResources().getString(R.string.text_loading));
                            LoginUIManager.getInstance().googleLogin(
                                    mActivity,
                                    result.getResultModel().getData().getId(),
                                    result.getResultModel().getData().getEmali(),
                                    result.getResultModel().getData().getNickName(),
                                    result.getResultModel().getData().getAccessToken(),
                                    mListener);
                        }

                        break;
                    case LTResultCode.STATE_FB_UI_TOKEN: //Facebook获取信息
                        if (result.getResultModel() != null) {
                            showDialog(getResources().getString(R.string.text_loading));
                            LoginUIManager.getInstance().fbLogin(mActivity,
                                    result.getResultModel().getData().getId(),
                                    result.getResultModel().getData().getEmali(),
                                    result.getResultModel().getData().getNickName(),
                                    result.getResultModel().getData().getAccessToken(),
                                    mListener);
                        }

                        break;
                    case LTResultCode.STATE_GOOGLE_LOGIN_FAILED: //google登录失败
                        LoginUIManager.getInstance().setResultFailed(activity,
                                LTResultCode.STATE_GOOGLE_LOGIN_FAILED,
                                result.getResultModel().getMsg());
                        dismissDialog();
                        break;
                    case LTResultCode.STATE_FB_LOGIN_FAILED: //Facebook登录失败
                        LoginUIManager.getInstance().setResultFailed(activity,
                                LTResultCode.STATE_FB_LOGIN_FAILED,
                                result.getResultModel().getMsg());
                        dismissDialog();
                        break;
                    case LTResultCode.STATE_GOOGLE_LOGIN_SUCCESS: //google登录成功
                        if (result.getResultModel() != null) {
                            LoginUIManager.getInstance().setResultSuccess(activity,
                                    LTResultCode.STATE_GOOGLE_LOGIN_SUCCESS,
                                    result.getResultModel());
                            dismissDialog();
                            getProxyActivity().finish();

                        }
                        break;
                    case LTResultCode.STATE_FB_LOGIN_SUCCESS: //facebook登录成功
                        if (result.getResultModel() != null) {
                            LoginUIManager.getInstance().setResultSuccess(activity,
                                    LTResultCode.STATE_FB_LOGIN_SUCCESS,
                                    result.getResultModel());
                            dismissDialog();
                            getProxyActivity().finish();

                        }
                        break;
                }
            }

            @Override
            public void onLoginOut() {

            }
        };
    }

    /**
     * 显示对话框
     */
    private void showDialog(String content) {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.setContent(content);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
        }
    }

    /**
     * 隐藏对话框
     */
    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismissDialog(mActivity);
        }

    }


    /**
     * 登录失败
     */
    private void loginFailed() {
        LoginObject data = new LoginObject();
        data.setAgreementUrl(mAgreementUrl);
        data.setPrivacyUrl(mPrivacyUrl);
        data.setLTAppID(LTAppID);
        data.setmGoogleClient(googleClientID);
        data.setmAdID(mAdID);
        data.setServerTest(mServerTest);
        data.setFacebookAppID(mFacebookID);
        data.setLoginOut(mIsLoginOut);
        data.setBind(false);
        getProxyActivity().addFragment(LoginFailedFragment.newInstance(data),
                false,
                true);
    }

    /**
     * 邮箱登录
     */
    private void emailLogin() {
        LoginObject data = new LoginObject();
        data.setAgreementUrl(mAgreementUrl);
        data.setPrivacyUrl(mPrivacyUrl);
        data.setLTAppID(LTAppID);
        data.setmGoogleClient(googleClientID);
        data.setmAdID(mAdID);
        data.setServerTest(mServerTest);
        data.setFacebookAppID(mFacebookID);
        data.setLoginOut(mIsLoginOut);
        data.setEmailType(Constants.EMAIL_LOGIN_JUMP);
        EmailLoginFragment fragment = EmailLoginFragment.newInstance(data);
        getProxyActivity().addFragment(fragment,
                false,
                true);

    }

    /**
     * 游客登录
     */
    private void guestLogin() {
        LoginObject data = new LoginObject();
        data.setAgreementUrl(mAgreementUrl);
        data.setPrivacyUrl(mPrivacyUrl);
        data.setLTAppID(LTAppID);
        data.setmGoogleClient(googleClientID);
        data.setmAdID(mAdID);
        data.setServerTest(mServerTest);
        data.setFacebookAppID(mFacebookID);
        data.setLoginOut(mIsLoginOut);
        GuestFragment fragment = GuestFragment.newInstance(data);
        getProxyActivity().addFragment(fragment,
                false,
                true);
    }

    /**
     * 游客转正
     */
    private void guestTurn() {
        LoginObject data = new LoginObject();
        data.setAgreementUrl(mAgreementUrl);
        data.setPrivacyUrl(mPrivacyUrl);
        data.setLTAppID(LTAppID);
        data.setmGoogleClient(googleClientID);
        data.setmAdID(mAdID);
        data.setServerTest(mServerTest);
        data.setFacebookAppID(mFacebookID);
        data.setLoginOut(mIsLoginOut);
        GuestTurnFragment fragment = GuestTurnFragment.newInstance(data);
        getProxyActivity().addFragment(fragment,
                false,
                true);
    }


}
