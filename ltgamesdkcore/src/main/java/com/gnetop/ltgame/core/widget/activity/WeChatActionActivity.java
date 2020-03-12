package com.gnetop.ltgame.core.widget.activity;

import android.util.Log;

import com.gnetop.ltgame.core.base.BaseActionActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WeChatActionActivity extends BaseActionActivity implements IWXAPIEventHandler {
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        handleResp(baseResp);
    }
}
