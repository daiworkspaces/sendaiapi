package com.course.config;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

public class TestConfig {
    public static String loginUri;
    public static String updateUserInfoUrl;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;

    public static CloseableHttpClient httpClient;
    public static BasicCookieStore cookieStore;

}
