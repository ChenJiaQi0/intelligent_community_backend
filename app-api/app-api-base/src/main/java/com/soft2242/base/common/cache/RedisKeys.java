package com.soft2242.base.common.cache;

/**
 * Redis Key管理
 *
 * @author
 */
public class RedisKeys {

    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return "code:" + key;
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return "token:" + accessToken;
    }

}
