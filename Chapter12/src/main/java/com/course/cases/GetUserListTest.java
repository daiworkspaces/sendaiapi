package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.GetUserListCase;
import com.course.utils.DataBaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetUserListTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserListCase getUserLIstCase = sqlSession.selectOne("getUserLIstCase",1);
        System.out.println(getUserLIstCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

    }
}
