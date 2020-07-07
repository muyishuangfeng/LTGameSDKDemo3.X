package com.Epic.Dragon.Battle.RPG.Games.Heroes;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by Administrator on 2020\2\11
 */

public class MyApp extends Application {

//    private static final String mLtAppID = "1";
//    private static final String mAuthID = "443503959733-nlr4ofibakk0j2dqkkomdqu3uta50pbe.apps.googleusercontent.com";
//    private static final String mFacebookId = "2717734461592670";
//    private static final String mAgreementUrl = "http://www.baidu.com";
//    private static final String mProvacyUrl = "http://www.baidu.com";
//    private static final String mGPPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAleVlYQtKhvo+lb83j73kXGH8xAMhHcaAZoS22Bo3Jdujix9Ou5DjtUW3i6MIFqWEbnb9da50iH5IrxkkdJCcqzeYDdLk2Y3Gc+kyaw5ch4I//hjC2hh8nHgo8eWfrxSFce/DpNBeS1j4mWcjWZhYJtxheEUk8iTyXIVWHC8dCyifibs7z8wCXMhy3Q66Zym5GarAYjpuQsXTxHuOYUXakLWCwIXG8d8ihoRxweI7PtLpVyNU5FKgse42uouMRz6TgVotgu+NdamNyTH/CutQMPGeNXUj6FpHUDEWQhsRp27k0KsA8YWJDJBj4R9bJ5GDqD8XJo2y5V7/vy1OH4afkQIDAQAB";
//    private static final String QQ_APP_ID = "1108097616";

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
//        LoginObject mRequest = new LoginObject();
//        mRequest.setFBAppID(mFacebookId);
//        mRequest.setmGoogleClient(mAuthID);
//        mRequest.setLTAppID(mLtAppID);
//        mRequest.setPrivacyUrl(mProvacyUrl);
//        mRequest.setAgreementUrl(mAgreementUrl);
//        mRequest.setGPPublicKey(mGPPublicKey);
//        mRequest.setQqAppID(QQ_APP_ID);
//        mRequest.setLoginOut(false);
//        LTGameSDK.getDefaultInstance().init(this, mRequest);
    }
}
