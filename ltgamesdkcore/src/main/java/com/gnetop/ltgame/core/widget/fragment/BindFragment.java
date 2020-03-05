package com.gnetop.ltgame.core.widget.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.gnetop.ltgame.core.R;
import com.gnetop.ltgame.core.base.BaseFragment;
import com.gnetop.ltgame.core.common.Constants;
import com.gnetop.ltgame.core.exception.LTResultCode;
import com.gnetop.ltgame.core.impl.OnLoginStateListener;
import com.gnetop.ltgame.core.manager.lt.LoginRealizeManager;
import com.gnetop.ltgame.core.manager.ui.LoginUIManager;
import com.gnetop.ltgame.core.model.LoginObject;
import com.gnetop.ltgame.core.model.LoginResult;
import com.gnetop.ltgame.core.ui.dialog.GeneralCenterDialog;
import com.gnetop.ltgame.core.util.PreferencesUtils;


public class BindFragment extends BaseFragment implements View.OnClickListener {

    LinearLayout mLytGoogle, mLytFaceBook, mLytEmail;
    String mAgreementUrl;
    String mPrivacyUrl;
    String googleClientID;
    String LTAppID;
    String mAdID;
    boolean mServerTest;
    String mFacebookID;
    boolean mIsLoginOut;
    LoginObject mData;
    private OnLoginStateListener mListener;
    GeneralCenterDialog mDialog;


    public static BindFragment newInstance(LoginObject data) {
        Bundle args = new Bundle();
        BindFragment fragment = new BindFragment();
        args.putSerializable(ARG_NUMBER, data);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getFragmentId() {
        return R.layout.fragment_bind;
    }

    @Override
    protected void initView(View view) {
        PreferencesUtils.init(mActivity);
        mLytGoogle = view.findViewById(R.id.lyt_bind_google);
        mLytGoogle.setOnClickListener(this);

        mLytFaceBook = view.findViewById(R.id.lyt_bind_facebook);
        mLytFaceBook.setOnClickListener(this);

        mLytEmail = view.findViewById(R.id.lyt_bind_email);
        mLytEmail.setOnClickListener(this);

        initData();

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

    }

    @Override
    public void onClick(View view) {
        int resID = view.getId();
        if (resID == R.id.lyt_bind_facebook) {//facebook
            LoginUIManager.getInstance().getFBInfo(mActivity, mData, mListener);
        } else if (resID == R.id.lyt_bind_google) {//google
            LoginUIManager.getInstance().getGoogleInfo(mActivity, mData, mListener);
        } else if (resID == R.id.lyt_bind_email) {//邮箱
            bindEmail();
        }
    }


    /**
     * 绑定失败
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
     * 绑定邮箱
     */
    private void bindEmail() {
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
        data.setEmailType(Constants.EMAIL_BIND_JUMP);
        getProxyActivity().addFragment(EmailLoginFragment.newInstance(data),
                false,
                true);

    }

    /**
     * 已经绑定
     */
    private void loginAlreadyBind() {
        LoginObject data = new LoginObject();
        data.setAgreementUrl(mAgreementUrl);
        data.setPrivacyUrl(mPrivacyUrl);
        data.setLTAppID(LTAppID);
        data.setmGoogleClient(googleClientID);
        data.setmAdID(mAdID);
        data.setServerTest(mServerTest);
        data.setFacebookAppID(mFacebookID);
        data.setLoginOut(mIsLoginOut);
        data.setBind(true);
        getProxyActivity().addFragment(LoginFailedFragment.newInstance(data),
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
        GuestTurnFragment fragment = GuestTurnFragment.newInstance(data);
        getProxyActivity().addFragment(fragment,
                false,
                true);
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
                        Log.e("TAG", "STATE_GOOGLE_UI_TOKEN=====" +
                                result.getResultModel().getData().getId() + "==" +
                                result.getResultModel().getData().getEmali() + "==" +
                                result.getResultModel().getData().getNickName() + "==");
                        if (result.getResultModel() != null) {
                            showDialog(getResources().getString(R.string.text_loading));
                            LoginUIManager.getInstance().googleBind(mActivity,
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
                            LoginUIManager.getInstance().fbBind(mActivity,
                                    result.getResultModel().getData().getId(),
                                    result.getResultModel().getData().getEmali(),
                                    result.getResultModel().getData().getNickName(),
                                    result.getResultModel().getData().getAccessToken(),
                                    mListener);
                        }

                        break;
                    case LTResultCode.STATE_GOOGLE_BIND_FAILED: //google绑定失败
                        LoginUIManager.getInstance().setResultFailed(activity,
                                LTResultCode.STATE_GOOGLE_BIND_FAILED, "Google Bind Failed");
                        PreferencesUtils.putString(mActivity, Constants.USER_GUEST_FLAG, "YES");
                        dismissDialog();

                        break;
                    case LTResultCode.STATE_FB_BIND_FAILED: //Facebook绑定失败
                        LoginUIManager.getInstance().setResultFailed(activity,
                                LTResultCode.STATE_FB_BIND_FAILED,
                                result.getResultModel().getMsg());
                        PreferencesUtils.putString(mActivity, Constants.USER_GUEST_FLAG, "YES");
                        dismissDialog();
                        break;
                    case LTResultCode.STATE_GOOGLE_BIND_SUCCESS: //google绑定成功
                        if (result.getResultModel() != null) {
                            LoginUIManager.getInstance().setResultSuccess(activity,
                                    LTResultCode.STATE_GOOGLE_BIND_SUCCESS,
                                    result.getResultModel());
                            PreferencesUtils.putString(mActivity, Constants.USER_GUEST_FLAG, "NO");
                            dismissDialog();
                            getProxyActivity().finish();

                        }
                        break;
                    case LTResultCode.STATE_FB_ALREADY_BIND: //已经绑定了facebook
                        LoginUIManager.getInstance().setResultFailed(activity,
                                LTResultCode.STATE_FB_ALREADY_BIND,
                                result.getResultModel().getMsg());
                        PreferencesUtils.putString(mActivity, Constants.USER_GUEST_FLAG, "YES");
                        dismissDialog();

                        break;
                    case LTResultCode.STATE_GOOGLE_ALREADY_BIND: //已经绑定了Google
                        LoginUIManager.getInstance().setResultFailed(activity,
                                LTResultCode.STATE_GOOGLE_ALREADY_BIND,
                                result.getResultModel().getMsg());

                        PreferencesUtils.putString(mActivity, Constants.USER_GUEST_FLAG, "YES");
                        dismissDialog();

                        break;
                    case LTResultCode.STATE_FB_BIND_SUCCESS: //Facebook绑定成功
                        if (result.getResultModel() != null) {
                            LoginUIManager.getInstance().setResultSuccess(activity,
                                    LTResultCode.STATE_FB_BIND_SUCCESS,
                                    result.getResultModel());
                            PreferencesUtils.putString(mActivity, Constants.USER_GUEST_FLAG, "NO");
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


}
