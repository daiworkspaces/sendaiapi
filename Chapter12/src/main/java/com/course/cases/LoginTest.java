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
        TestConfig.cookieStore = new BasicCookieStore();

    }




    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase",1);

        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUri);

        Boolean result = getResult(loginCase);
        Assert.assertTrue(result);
        //Assert.assertFalse(result);




    }

    private Boolean getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUri);

        JsonObject param = new JsonObject();
        param.addProperty("password",loginCase.getPassword());
        param.addProperty("userName",loginCase.getUserName());

        post.setHeader("Content-type","application/json");
        post.addHeader("cookie",TestConfig.cookieStore.toString());

        StringEntity entity = new StringEntity(param.toString(),"UTF-8");
        post.setEntity(entity);
        CloseableHttpResponse response = TestConfig.httpClient.execute(post);

        Boolean result;
        result = Boolean.valueOf(EntityUtils.toString(response.getEntity(),"utf-8"));

        System.out.println(result);

        return result;

    }


}
