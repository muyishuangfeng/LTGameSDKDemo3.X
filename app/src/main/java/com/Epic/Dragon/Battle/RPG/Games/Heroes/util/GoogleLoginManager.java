//package com.Epic.Dragon.Battle.RPG.Games.Heroes.util;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.util.Log;
//
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.Task;
//
///**
// * Created by Administrator on 2020\2\12
// */
//
//public class GoogleLoginManager {
//
//    public static void initGoogle(Activity context, String clientID, int selfRequestCode) {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(clientID)
//                .requestEmail()
//                .build();
//        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        context.startActivityForResult(signInIntent, selfRequestCode);
//    }
//
//
//    public static void onActivityResult(int requestCode, Intent data, int selfRequestCode) {
//        if (requestCode == selfRequestCode) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            handleSignInResult(task);
//        }
//    }
//
//
//    private static void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            Log.e("TAGs",account.toJson());
//
//            Log.e("TAG", account.getDisplayName() + "===" + account.getEmail() + "==" + account.getFamilyName() + "==" +
//                    account.getGivenName() + "==" + account.getId() + "===" + account.getIdToken() + "==" +
//                    account.getAccount() + "===" + account.getPhotoUrl() + "==" + account.getObfuscatedIdentifier() + "==" + account.getObfuscatedIdentifier() + "==" +
//                    account.getExpirationTimeSecs());
//            String idToken = account.getIdToken();
//
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
