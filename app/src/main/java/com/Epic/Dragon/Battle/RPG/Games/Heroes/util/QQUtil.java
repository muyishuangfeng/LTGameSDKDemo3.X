//package com.Epic.Dragon.Battle.RPG.Games.Heroes.util;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.gentop.ltsdk.common.util.PreferencesUtils;
//import com.tencent.connect.UserInfo;
//import com.tencent.connect.auth.QQToken;
//import com.tencent.connect.common.Constants;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
//
//import org.json.JSONObject;
//
//
//public class QQUtil {
//
//    LoginUIListener mLoginListener;
//    Tencent mTencent;
//    Activity activity;
//
//    public QQUtil(Activity activity,String mTencentID){
//        this.activity=activity;
//        mTencent = Tencent.createInstance(mTencentID, activity);
//    }
//
//
//    /**
//     * 登录
//     */
// public   void login(Activity context) {
//        mLoginListener = new LoginUIListener();
//        mTencent.login(context, "all", mLoginListener, true);
//    }
//
//    /**
//     * QQ登录回调
//     */
//    private class LoginUIListener implements IUiListener {
//
//        @Override
//        public void onComplete(Object object) {
//            if (null == object) {
//                return;
//            }
//            JSONObject jsonResponse = (JSONObject) object;
//            if (jsonResponse.length() != 0) {
//                try {
//                    String token = jsonResponse.getString(Constants.PARAM_ACCESS_TOKEN);
//                    String expires = jsonResponse.getString(Constants.PARAM_EXPIRES_IN);
//                    String openId = jsonResponse.getString(Constants.PARAM_OPEN_ID);
//                    if (!TextUtils.isEmpty(token) &&
//                            !TextUtils.isEmpty(expires)
//                            && !TextUtils.isEmpty(openId)) {
//                        long time = System.currentTimeMillis() + Long.parseLong(expires) * 1000;
//                       // saveData(token, openId, time);
//                        mTencent.setAccessToken(token, expires);
//                        mTencent.setOpenId(openId);
//                        Log.e("TAGSSS",jsonResponse.toString());
//                        getUserInfo(activity);
//
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        public void onError(UiError uiError) {
//        }
//
//        @Override
//        public void onCancel() {
//        }
//    }
//
//
//
//
//
//    /**
//     * 回调
//     */
//   public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == Constants.REQUEST_LOGIN ||
//                requestCode == Constants.REQUEST_APPBAR) {
//            mLoginListener = new LoginUIListener();
//            Tencent.onActivityResultData(requestCode, resultCode, data, mLoginListener);
//        }
//    }
//
//    /**
//     * 获取用户信息
//     */
//    private void getUserInfo(Activity activity) {
//        QQToken token = mTencent.getQQToken();
//        UserInfo mInfo = new UserInfo(activity, token);
//        mInfo.getUserInfo(new IUiListener() {
//            @Override
//            public void onComplete(Object object) {
//                JSONObject jb = (JSONObject) object;
//                try {
//                    String name = jb.getString("nickname");
//                    Log.e("TAGSSS",((JSONObject) object).toString()+"========="+
//                            name+"\n");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(UiError uiError) {
//            }
//
//            @Override
//            public void onCancel() {
//            }
//        });
//
//    }
//}
//
