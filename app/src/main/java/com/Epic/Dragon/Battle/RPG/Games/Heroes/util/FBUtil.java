//package com.Epic.Dragon.Battle.RPG.Games.Heroes.util;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.FacebookSdkNotInitializedException;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginBehavior;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.google.gson.Gson;
//
//import org.json.JSONObject;
//
//import java.util.Arrays;
//
///**
// * Created by Administrator on 2020\2\12
// */
//
//public class FBUtil {
//
//    private static CallbackManager mFaceBookCallBack;
//
//
//    /**
//     * 初始化
//     */
//    public static void initFaceBook(final Context context, String mFacebookID,  boolean isLoginOut) {
//        FacebookSdk.setApplicationId(mFacebookID);
//        FacebookSdk.sdkInitialize(context);
//        if (isLoginOut) {
//            LoginManager.getInstance().logOut();
//        }
//        try {
//            mFaceBookCallBack = CallbackManager.Factory.create();
//            LoginManager.getInstance()
//                    .setLoginBehavior(LoginBehavior.WEB_ONLY)
//                    .logInWithReadPermissions((Activity) context,
//                            Arrays.asList("public_profile"));
//            LoginManager.getInstance().registerCallback(mFaceBookCallBack,
//                    new FacebookCallback<LoginResult>() {
//                        @Override
//                        public void onSuccess(LoginResult loginResult) {
//                            if (loginResult != null) {
//                                getUserInfo(loginResult.getAccessToken());
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancel() {
//
//                        }
//
//                        @Override
//                        public void onError(FacebookException error) {
//
//                        }
//                    });
//
//        } catch (FacebookSdkNotInitializedException ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//
//    /**
//     * 设置登录结果回调
//     *
//     * @param requestCode 请求码
//     * @param resultCode  结果码
//     * @param data        数据
//     */
//    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (mFaceBookCallBack != null) {
//            mFaceBookCallBack.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    /**
//     * 退出登录
//     */
//    public static void loginOut() {
//        LoginManager.getInstance().logOut();
//    }
//
//
//
//    private static  void getUserInfo(AccessToken accessToken){
//        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                if (object != null) {
////                    String id = object.optString( "id" ) ;   //比如:1565455221565
////                    String name = object.optString( "name" ) ;  //比如：Zhang San
////                    String gender = object.optString("gender") ;  //性别：比如 male （男）  female （女）
////                    String emali = object.optString("email") ;  //邮箱：比如：56236545@qq.com
////
////                    //获取用户头像
////                    JSONObject object_pic = object.optJSONObject( "picture" ) ;
////                    JSONObject object_data = object_pic.optJSONObject( "data" ) ;
////                    String photo = object_data.optString( "url" )  ;
////
////                    //获取地域信息
////                    String locale = object.optString( "locale" ) ;   //zh_CN 代表中文简体
//                    Log.e("fb",object.toString());
//
//                }
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link,gender,birthday,email,picture,locale,updated_time,timezone,age_range,first_name,last_name");
//        request.setParameters(parameters);
//        request.executeAsync() ;
//    }
//
//}
