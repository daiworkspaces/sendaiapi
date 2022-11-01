package com.course.httpclient.cookies;

import com.sun.org.apache.xml.internal.security.keys.storage.implementations.SingleCertificateResolver;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesGet {
    private String url ;
    private ResourceBundle bundle;
    //用来存储cookie信息
    private CookieStore cookieStore;



    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CANADA);
        url = bundle.getString("test.url");
    }

    @Test()
    public void test(){
        System.out.println(url);
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url +uri;
        HttpGet get = new HttpGet(testUrl);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(get);

        HttpEntity entityUtils = response.getEntity();
        result = EntityUtils.toString(entityUtils,"UTF-8");
//        result = EntityUtils.toString(response.getEntity(),"UTF-8");
        System.out.println(result);



    }

    @Test
    public void  testCookieStore() throws IOException {
        cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = null;
        httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        String url =this.url+ bundle.getString("getCookies.uri");
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);

        //获取cookie信息
        List<Cookie> cookies = cookieStore.getCookies();
//        for (int i =0; i<cookies.size();i++){
//            System.out.println(cookies.get(i).getName());
//            System.out.println(cookies.get(i).getValue());
//        }

        for (Cookie cookie :cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println(name);
            System.out.println(value);
        }


        EntityUtils.consume(response.getEntity());
    }


    @Test(dependsOnMethods = {"testCookieStore"})
    public void testDaiCookie() throws IOException {
        CloseableHttpClient httpClient = null;
         url = this.url + bundle.getString("daiCookies.uri");
        System.out.println(url);

        String cookieName=null;
        String cookieValue =null;
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie :cookies){
            cookieName = cookie.getName();
            cookieValue = cookie.getValue();
            System.out.println(cookieName);
            System.out.println(cookieValue);
        }

        BasicClientCookie cookie = new BasicClientCookie(cookieName,cookieValue);
        cookie.setDomain(cookie.getDomain());
        cookie.setPath(cookie.getPath());
        cookie.setAttribute(ClientCookie.PATH_ATTR,cookie.getPath());
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR,cookie.getDomain());
        cookieStore.addCookie(cookie);

        httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);


        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec("singleCookiesSpec")
                .build();

        context.setCookieSpecRegistry(context.getCookieSpecRegistry());







        HttpGet httpGet = new HttpGet(url);


        System.out.println(cookieStore.toString());

        //设置cookies信息



        CloseableHttpResponse response = httpClient.execute(httpGet,context);
        HttpEntity entity = response.getEntity();
        System.out.println(response.toString());
        String result = EntityUtils.toString(entity,"UTF-8");
        System.out.println(result);
        System.out.println("------------------");

    }






}
