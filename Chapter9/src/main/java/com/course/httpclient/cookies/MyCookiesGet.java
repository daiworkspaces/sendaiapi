package com.course.httpclient.cookies;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesGet {
    private String url ;
    private ResourceBundle bundle;
    //用来存储cookie信息
   private BasicCookieStore cookieStore;



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
    public void  testCookieStore() throws IOException {


        String url =this.url+ bundle.getString("getCookies.uri");
        HttpGet httpGet = new HttpGet(url);

         this.cookieStore = new BasicCookieStore();

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);


        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        CloseableHttpResponse response = client.execute(httpGet,context);

        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);

        //获取cookie信息
        for (Cookie c : cookieStore.getCookies()){
            System.out.println(c.getName());
            System.out.println(c.getValue());
        }


        //EntityUtils.consume(response.getEntity());
    }


    @Test
    public void testGetWihCookies() throws IOException {
        String url = bundle.getString("daiCookies.uri");
        String testUrl = this.url+url;

        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();

        HttpClientContext context = HttpClientContext.create();
        
        context.setCookieStore(cookieStore);
        System.out.println(cookieStore.toString());



        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(testUrl);
        httpGet.addHeader("Cookies","login=true");


//        HttpClientContext context = new HttpClientContext();
//        context.setCookieStore(cookieStore);

        CloseableHttpResponse response = httpClient.execute(httpGet,context);
        HttpEntity entity = response.getEntity();
        System.out.println(response.toString());
        String result = EntityUtils.toString(entity,"UTF-8");
        System.out.println("打印结果2"+result);







    }


    @Test
    public void testGetCookies() throws IOException {

        cookieStore = new BasicCookieStore();
        String tesUrl = this.url + bundle.getString("cookiesDemo.uri");
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpGet httpGet = new HttpGet(tesUrl);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        EntityUtils.consume(entity);
        System.out.println(result);

        System.out.println("cookie store: "+cookieStore.getCookies());

    }


    @Test(dependsOnMethods = {"testGetCookies"})
    public void testCookieDemo() throws IOException {
        String url = this.url +bundle.getString("huoQuCookies.uri");
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                .build();

        HttpGet  httpGet = new HttpGet(url);
        httpGet.addHeader("Cookie",cookieStore.toString());

        CloseableHttpResponse response = httpClient.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

    }

}
