package com.highplace.service.oauth.domain;

/*
access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
expires_in	access_token接口调用凭证超时时间，单位（秒）
refresh_token	用户刷新access_token
openid	用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
scope	用户授权的作用域，使用逗号（,）分隔
示例:
{"access_token":"qDfMCYe1FpI7Ow6JJoFYszb9KY2VPl6OcQa8dYfojhxMpMW0vN-iAOUKVWHzgy-DVly9XZkKqmZ4FyuX5glj2UdNZuW-oA1PbqXnQSEwyJ0","expires_in":7200,"refresh_token":"T3IpdGUmrUOKOSTUL7tEB84Dluxyvb49kJKEZ9hSng-XtG9shw0gfp7TThyEqASneo4ccCZowuh3mf-Q_ZsKrHtGNFIV6EOTGcHOp3KhbxM","openid":"ox7u_tyIWFXwmOC7xYYCrem4ynOI","scope":"snsapi_base"}
*/
public class WechatAccessToken extends WechatError {

    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String openid;
    private String scope;

    @Override
    public String toString() {
        return "WechatAccessToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
