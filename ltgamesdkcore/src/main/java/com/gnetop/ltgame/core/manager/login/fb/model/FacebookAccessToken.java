package com.gnetop.ltgame.core.manager.login.fb.model;


import com.gnetop.ltgame.core.model.AccessToken;
import com.gnetop.ltgame.core.platform.Target;

public class FacebookAccessToken extends AccessToken {


    private String access_token;

    @Override
    public int getLoginTarget() {
        return Target.LOGIN_FACEBOOK;
    }

    @Override
    public String getAccess_token() {
        return access_token;
    }

    @Override
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
