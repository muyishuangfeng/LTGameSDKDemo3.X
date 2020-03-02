package com.gnetop.ltgame.core.manager.login.google.model;

import com.gnetop.ltgame.core.model.AccessToken;
import com.gnetop.ltgame.core.platform.Target;

public class GoogleAccessToken extends AccessToken {

    private String access_token;

    @Override
    public String getAccess_token() {
        return access_token;
    }

    @Override
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public int getLoginTarget() {
        return Target.LOGIN_GOOGLE;
    }
}
