package com.gnetop.ltgame.core.model;


import java.io.Serializable;
import java.io.StringReader;

/**
 * 登录参数类
 */
public class LoginObject implements Serializable {

    private String appSecret; // 授权域
    private String scope = LTGameValues.WX_SCOPE; // 授权域
    private String nonceStr; // 随机字符串
    private String timestamp; // 时间戳
    private String signature; // 签名
    private String LTAppID;//乐推AppID
    private String mAdID;//唯一标识
    private String mGoogleClient;//GoogleClientID
    private int selfRequestCode;//请求码
    private String facebookAppID;//facebook AppID
    private boolean loginOut;//退出登录
    private String mPhone;//手机号
    private String mPassword;//密码
    private String mLoginCode;//登录状态码
    private String qqAppID;//qqAppID
    private String guestType;//游客登录类型
    private boolean mStats;//统计
    private String mType;//类型
    private String email;//邮箱
    private String authCode;//邮箱验证码
    private String loginType;//登录类型
    private String mAgreementUrl;//用户协议
    private String mPrivacyUrl;//隐私政策
    private boolean isServerTest;//是否是测试服
    private String mGPPublicKey;//google支付公钥
    private String mOneStorePublicKey;//onestore支付公钥
    private boolean mBind;//是否绑定过
    private String emailType;//邮箱类型：1登录2绑定
    private int role_number;//     角色编号（游戏服务器用户ID）
    private String role_name;//       角色名称
    private String role_sex;//      角色性别
    private String role_level;//      角色等级
    private long role_create_time;// 角色创建时间
    private int server_number;//   服务器编号


    public int getRole_number() {
        return role_number;
    }

    public void setRole_number(int role_number) {
        this.role_number = role_number;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getRole_sex() {
        return role_sex;
    }

    public void setRole_sex(String role_sex) {
        this.role_sex = role_sex;
    }

    public String getRole_level() {
        return role_level;
    }

    public void setRole_level(String role_level) {
        this.role_level = role_level;
    }

    public long getRole_create_time() {
        return role_create_time;
    }

    public void setRole_create_time(long role_create_time) {
        this.role_create_time = role_create_time;
    }

    public int getServer_number() {
        return server_number;
    }

    public void setServer_number(int server_number) {
        this.server_number = server_number;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public boolean ismBind() {
        return mBind;
    }

    public void setBind(boolean mBind) {
        this.mBind = mBind;
    }

    public String getGPPublicKey() {
        return mGPPublicKey;
    }

    public void setGPPublicKey(String mGPPublicKey) {
        this.mGPPublicKey = mGPPublicKey;
    }

    public String getOneStorePublicKey() {
        return mOneStorePublicKey;
    }

    public void setOneStorePublicKey(String mOneStorePublicKey) {
        this.mOneStorePublicKey = mOneStorePublicKey;
    }

    public boolean isServerTest() {
        return isServerTest;
    }

    public void setServerTest(boolean serverTest) {
        isServerTest = serverTest;
    }

    public String getAgreementUrl() {
        return mAgreementUrl;
    }

    public void setAgreementUrl(String mAgreementUrl) {
        this.mAgreementUrl = mAgreementUrl;
    }

    public String getPrivacyUrl() {
        return mPrivacyUrl;
    }

    public void setPrivacyUrl(String mPrivacyUrl) {
        this.mPrivacyUrl = mPrivacyUrl;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLTAppID() {
        return LTAppID;
    }

    public void setLTAppID(String LTAppID) {
        this.LTAppID = LTAppID;
    }


    public String getmAdID() {
        return mAdID;
    }

    public void setmAdID(String mAdID) {
        this.mAdID = mAdID;
    }

    public String getmGoogleClient() {
        return mGoogleClient;
    }

    public void setmGoogleClient(String mGoogleClient) {
        this.mGoogleClient = mGoogleClient;
    }

    public int getSelfRequestCode() {
        return selfRequestCode;
    }

    public void setSelfRequestCode(int selfRequestCode) {
        this.selfRequestCode = selfRequestCode;
    }


    public String getFacebookAppID() {
        return facebookAppID;
    }

    public void setFacebookAppID(String facebookAppID) {
        this.facebookAppID = facebookAppID;
    }

    public boolean isLoginOut() {
        return loginOut;
    }

    public void setLoginOut(boolean loginOut) {
        this.loginOut = loginOut;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getmLoginCode() {
        return mLoginCode;
    }

    public void setmLoginCode(String mLoginCode) {
        this.mLoginCode = mLoginCode;
    }

    public String getQqAppID() {
        return qqAppID;
    }

    public void setQqAppID(String qqAppID) {
        this.qqAppID = qqAppID;
    }

    public String getGuestType() {
        return guestType;
    }

    public void setGuestType(String guestType) {
        this.guestType = guestType;
    }

    public boolean getStats() {
        return mStats;
    }

    public void setStats(boolean mStats) {
        this.mStats = mStats;
    }
}
