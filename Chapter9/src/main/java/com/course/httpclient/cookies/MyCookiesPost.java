package com.course.httpclient.cookies;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class MyCookiesPost {
    private String url ;
    private ResourceBundle bundle;
    private BasicCookieStore cookieStore;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CANADA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {

        cookieStore = new BasicCookieStore();
        String tesUrl = this.url + bundle.getString("getCookies.uri");
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
    public void testPostCookiesDemo() throws IOException {
        String url = this.url + bundle.getString("postCookies.uri");

        //声明一个client对象，用来进行方法的执行
        CloseableHttpClient httpClient =HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        //声明一个方法，这个方法就是post方法
        HttpPost httpPost = new HttpPost(url);

        //添加参数
        JsonObject param = new JsonObject();
        param.addProperty("name","huHanSan");
        param.addProperty("age","18");

        //设置请求头信息,设置header
        httpPost.setHeader("Content-type","application/json");
        httpPost.addHeader("Cookie",cookieStore.toString());


        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"UTF-8");
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(result);

        //将返回的响应结果字符串转化
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(result,JsonObject.class);
        System.out.println(obj.get("huHanSan"));
        System.out.println(obj.get("status"));

        //获取到结果值

        String success =  obj.get("huHanSan").getAsString();
        String status = obj.get("status").getAsString();

        //判断结果
        Assert.assertEquals("success",success);
        Assert.assertEquals("1",status);



        // 遍历JsonObject 获取key value
        for (Map.Entry<String, JsonElement> set :obj.entrySet()){
            System.out.println(set.getKey()+"_"+set.getValue());
        }











    }




}
