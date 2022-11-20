package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.utils.DataBaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {

    @Test(dependsOnGroups="loginTrue",description = "更改用户信息")
    public void updateUserInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
    public void deleteUser() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

    }
}
