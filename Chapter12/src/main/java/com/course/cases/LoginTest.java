package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DataBaseUtil;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
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

    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUri);
    }

    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        LoginCase loginCase = sqlSession.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUri);

    }
}
