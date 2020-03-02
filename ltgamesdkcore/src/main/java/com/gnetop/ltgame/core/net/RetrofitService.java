package com.gnetop.ltgame.core.net;


import com.gnetop.ltgame.core.base.BaseEntry;
import com.gnetop.ltgame.core.model.AuthWXModel;
import com.gnetop.ltgame.core.model.ResultModel;
import com.gnetop.ltgame.core.model.WeChatAccessToken;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {


    /**
     * 微信登录
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/wx/login")
    Observable<BaseEntry<ResultModel>> weChatLogin(@Header("language") String LTAppID,
                                                   @Header("token") String LTToken,
                                                   @Header("tsp") int LTTime,
                                                   @Header("app_id") String Long,
                                                   @Body Map<String, Object> map);

    /**
     * QQ登录
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/qq/login")
    Observable<BaseEntry<ResultModel>> qqLogin(@Header("language") String LTAppID,
                                               @Header("token") String LTToken,
                                               @Header("tsp") int LTTime,
                                               @Header("app_id") String Long,
                                               @Body Map<String, Object> map);

    /**
     * 获取验证码
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/email/getcode")
    Observable<BaseEntry> getAuthCode(@Header("language") String LTAppID,
                                      @Header("token") String LTToken,
                                      @Header("tsp") int LTTime,
                                      @Header("app_id") String Long,
                                      @Body Map<String, Object> map);


    /**
     * 注册
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/api/auth/register-phone")
    Observable<BaseEntry<ResultModel>> register(@Header("language") String LTAppID,
                                                @Header("token") String LTToken,
                                                @Header("tsp") int LTTime,
                                                @Header("app_id") String Long,
                                                @Body Map<String, Object> map);

    /**
     * 获取设备信息
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/api/other/join")
    Observable<BaseEntry<ResultModel>> getDeviceInfo(@Header("language") String LTAppID,
                                                     @Header("token") String LTToken,
                                                     @Header("tsp") int LTTime,
                                                     @Header("app_id") String Long,
                                                     @Body Map<String, Object> map);

    /**
     * 手机登录
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/api/auth/login-phone")
    Observable<BaseEntry<ResultModel>> login(@Header("language") String LTAppID,
                                             @Header("token") String LTToken,
                                             @Header("tsp") int LTTime,
                                             @Header("app_id") String Long,
                                             @Body Map<String, Object> map);

    /**
     * 更改密码
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/api/auth/reset-password")
    Observable<BaseEntry<ResultModel>> updatePassword(@Header("language") String LTAppID,
                                                      @Header("token") String LTToken,
                                                      @Header("tsp") int LTTime,
                                                      @Header("app_id") String Long,
                                                      @Body Map<String, Object> map);

    /**
     * google登录
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/google/login")
    Observable<BaseEntry<ResultModel>> googleLogin(@Header("language") String LTAppID,
                                                   @Header("token") String LTToken,
                                                   @Header("tsp") int LTTime,
                                                   @Header("app_id") String Long,
                                                   @Body Map<String, Object> map);

    /**
     * facebook登录
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/facebook/login")
    Observable<BaseEntry<ResultModel>> faceBookLogin(@Header("language") String LTAppID,
                                                     @Header("token") String LTToken,
                                                     @Header("tsp") int LTTime,
                                                     @Header("app_id") String Long,
                                                     @Body Map<String, Object> map);

    /**
     * 游客登录验证
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/visitor/login")
    Observable<BaseEntry<ResultModel>> guestLogin(@Header("language") String LTAppID,
                                                  @Header("token") String LTToken,
                                                  @Header("tsp") int LTTime,
                                                  @Header("app_id") String Long,
                                                  @Body Map<String, Object> map);


    /**
     * 邮箱登录
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/email/login")
    Observable<BaseEntry<ResultModel>> emailLogin(@Header("language") String LTAppID,
                                                  @Header("token") String LTToken,
                                                  @Header("tsp") int LTTime,
                                                  @Header("app_id") String Long,
                                                  @Body Map<String, Object> map);

    /**
     * 发送异常信息
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/exception/store")
    Observable<BaseEntry<ResultModel>> sendException(@Header("language") String LTAppID,
                                                     @Header("token") String LTToken,
                                                     @Header("tsp") int LTTime,
                                                     @Header("app_id") String Long,
                                                     @Body Map<String, Object> map);

    /**
     * 邮箱绑定
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/email/bind")
    Observable<BaseEntry<ResultModel>> bindEmail(@Header("language") String LTAppID,
                                                 @Header("token") String LTToken,
                                                 @Header("tsp") int LTTime,
                                                 @Header("app_id") String Long,
                                                 @Body Map<String, Object> map);

    /**
     * 游客绑定
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/visitor/bind")
    Observable<BaseEntry<ResultModel>> bindGuest(@Header("language") String LTAppID,
                                                 @Header("token") String LTToken,
                                                 @Header("tsp") int LTTime,
                                                 @Header("app_id") String Long,
                                                 @Body Map<String, Object> map);

    /**
     * Google绑定
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/google/bind")
    Observable<BaseEntry<ResultModel>> bindGoogle(@Header("language") String LTAppID,
                                                  @Header("token") String LTToken,
                                                  @Header("tsp") int LTTime,
                                                  @Header("app_id") String Long,
                                                  @Body Map<String, Object> map);

    /**
     * Facebook绑定
     */
    @Headers({"Content-Type:application/json",
            "Accept:application/json"})
    @POST("/v1/user/facebook/bind")
    Observable<BaseEntry<ResultModel>> bindFB(@Header("language") String LTAppID,
                                              @Header("token") String LTToken,
                                              @Header("tsp") int LTTime,
                                              @Header("app_id") String Long,
                                              @Body Map<String, Object> map);

    /**
     * 微信绑定
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/wx/bind")
    Observable<BaseEntry<ResultModel>> bindWX(@Header("language") String LTAppID,
                                              @Header("token") String LTToken,
                                              @Header("tsp") int LTTime,
                                              @Header("app_id") String Long,
                                              @Body Map<String, Object> map);

    /**
     * QQ绑定
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/qq/bind")
    Observable<BaseEntry<ResultModel>> bindQQ(@Header("language") String LTAppID,
                                              @Header("token") String LTToken,
                                              @Header("tsp") int LTTime,
                                              @Header("app_id") String Long,
                                              @Body Map<String, Object> map);


    /**
     * 创建订单
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/email/bind")
    Observable<BaseEntry<ResultModel>> createOrder(@Header("language") String LTAppID,
                                                   @Header("token") String LTToken,
                                                   @Header("tsp") int LTTime,
                                                   @Header("app_id") String Long,
                                                   @Body Map<String, Object> map);

    /**
     * google支付
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/order/google_pay")
    Observable<BaseEntry<ResultModel>> googlePlay(@Header("language") String LTAppID,
                                                  @Header("token") String LTToken,
                                                  @Header("tsp") int LTTime,
                                                  @Header("app_id") String Long,
                                                  @Body Map<String, Object> map);

    /**
     * oneStore支付
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/api/p/one-store")
    Observable<BaseEntry<ResultModel>> oneStorePlay(@Header("language") String LTAppID,
                                                    @Header("token") String LTToken,
                                                    @Header("tsp") int LTTime,
                                                    @Header("app_id") String Long,
                                                    @Body RequestBody map);

    /**
     * 自动登录验证
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user/info")
    Observable<BaseEntry<ResultModel>> autoLogin(@Header("language") String LTAppID,
                                                 @Header("token") String LTToken,
                                                 @Header("tsp") int LTTime,
                                                 @Header("app_id") String Long,
                                                 @Body Map<String, Object> map);

    /**
     * 获取微信AccessToken
     */
    @POST("/oauth2/access_token")
    Observable<WeChatAccessToken> getWXAccessToken(@Query("appid") String appid,
                                                   @Query("secret") String secret,
                                                   @Query("code") String code,
                                                   @Query("grant_type") String grant_type);


    /**
     * 刷新微信AccessToken
     */
    @POST("/oauth2/refresh_token")
    Observable<WeChatAccessToken> refreshWXAccessToken(@Query("appid") String appid,
                                                       @Query("grant_type") String grant_type,
                                                       @Query("refresh_token") String refresh_token);

    /**
     * 验证微信AccessToken
     */
    @POST
    Observable<AuthWXModel> authToken(@Query("access_token") String access_token);


    /**
     * 绑定游戏角色
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/v1/user_role/store")
    Observable<BaseEntry> bindRole(@Header("language") String LTAppID,
                                   @Header("token") String LTToken,
                                   @Header("tsp") int LTTime,
                                   @Header("app_id") String Long,
                                   @Body Map<String, Object> map);

    /**
     * 提交信息
     */
    @Headers({"Content-Type:application/json",
            "Accept-Charset:utf-8"})
    @POST("/api/other/join")
    Observable<BaseEntry> uploadDevice(@Header("AppID") String LTAppID,
                                       @Header("Token") String LTToken,
                                       @Header("Tsp") int LTTime,
                                       @Header("Long") String Long,
                                       @Body Map<String, Object> map);

}
