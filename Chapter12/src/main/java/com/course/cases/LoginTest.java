package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DataBaseUtil;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

public class LoginTest {

    @BeforeTest(groups = "loginTrue",description = "测试前的准备工作，获取httpClient对象")
    public void beforeTest(){
        /**
         * 加载接口地址
         * 通过工具类 utils 中的configFIle来实现
         * 需要借助与model中的枚举值来实现。
         * 通过TestConfig 将application中定义的接口与此
         * 接口对应的url进行匹配
         *
         * */
        TestConfig.loginUri = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.httpClient = HttpClients.createDefault();

    }

    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUri);


        //第一步发送请求 loginCase是通过接口获取到的数据，用此数据完成
        //数据的http请求然后获取result在与expected数据对比进行校验
        String result = getResult(loginCase);

        //第二步验证结果
        Assert.assertEquals(loginCase.getExpected(),result);


    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost();
        JsonObject param = new JsonObject();
        param.addProperty("userName","");
        param.addProperty("password","");

        post.setHeader("Content-type","application/json");
        post.addHeader("Cookie",TestConfig.cookieStore.toString());

        StringEntity entity = new StringEntity(param.toString(),"UTF-8");
        post.setEntity(entity);

        String result;
        CloseableHttpResponse response = TestConfig.httpClient.execute(post);

        result = EntityUtils.toString(response.getEntity(),"UTF-8");

        //登录成功返回cookiestore；
        TestConfig.cookieStore = (BasicCookieStore) TestConfig.cookieStore.getCookies();

        return  result;



    }


    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUri);

    }

}
