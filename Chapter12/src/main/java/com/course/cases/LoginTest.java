package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.model.User;
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

    }

//    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
//    public void loginTrue() throws IOException {
//        SqlSession sqlSession = DataBaseUtil.getSqlSession();
//        User user = sqlSession.selectOne("Login",1);
//        System.out.println(user.toString());
//        System.out.println(TestConfig.loginUri);
//
//
//        //第一步发送请求 loginCase是通过接口获取到的数据，用此数据完成
//        //数据的http请求然后获取result在与expected数据对比进行校验
//        String result = getResult(user);
//
//        //第二步验证结果
//        Assert.assertEquals(user,result);
//
//
//    }

//    private String getResult(User user) throws IOException {
//        HttpPost post = new HttpPost();
//        JsonObject param = new JsonObject();
//        param.addProperty("userName",user.getUserName());
//        param.addProperty("password",user.getPassword());
//        param.addProperty("age",user.getAge());
//        param.addProperty("id",user.getId());
//        param.addProperty("isDelete",user.getIsDelete());
//        param.addProperty("sex",user.getSex());
//
//        post.setHeader("Content-type","application/json");
//        post.addHeader("Cookie",TestConfig.cookieStore.toString());
//
//        StringEntity entity = new StringEntity(param.toString(),"UTF-8");
//        post.setEntity(entity);
//
//        String result;
//        CloseableHttpResponse response = TestConfig.httpClient.execute(post);
//
//        result = EntityUtils.toString(response.getEntity(),"UTF-8");
//
//        //登录成功返回cookiestore；
//        TestConfig.cookieStore = (BasicCookieStore) TestConfig.cookieStore.getCookies();
//
//        System.out.println("结果返回查看result"+result);
//        return  result;
//
//
//
//    }


    @Test(groups = "loginTrue",description = "用户登录失败接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUri);

        String result = getResult(loginCase);

    }

    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUri);
        JsonObject param = new JsonObject();
        param.addProperty("userName",loginCase.getUserName());
        param.addProperty("password",loginCase.getPassword());
        param.addProperty("id",loginCase.getId());
        param.addProperty("expected",loginCase.getExpected());
        //param.addProperty("id",loginCase.getId());

        post.setHeader("Content-type","application/json");
        post.addHeader("Cookie",TestConfig.cookieStore.toString());

       StringEntity entity = new StringEntity(param.toString(),"UTF-8");
        post.setEntity(entity);

       String result;
        CloseableHttpResponse response = TestConfig.httpClient.execute(post);

       result = EntityUtils.toString(response.getEntity(),"UTF-8");
        //登录成功返回cookiestore；
        TestConfig.cookieStore = (BasicCookieStore) TestConfig.cookieStore.getCookies();

        System.out.println("结果返回查看result"+result);
       return  result;




    }

}
