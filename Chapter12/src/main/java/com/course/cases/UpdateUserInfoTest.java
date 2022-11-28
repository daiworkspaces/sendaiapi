package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {

    @Test(dependsOnGroups="loginTrue",description = "更改用户信息")
    public void updateUserInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        int result = getResult(updateUserInfoCase);

        User user = sqlSession.selectOne(updateUserInfoCase.getExpected());
        Assert.assertNotNull(user);
        Assert.assertNotNull(result);
    }

    private int getResult(UpdateUserInfoCase updateUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.updateUserInfoUrl);
        JsonObject param = new JsonObject();
        param.addProperty("id",updateUserInfoCase.getId());
        param.addProperty("userName",updateUserInfoCase.getUserName());
        param.addProperty("sex",updateUserInfoCase.getSex());
        param.addProperty("age",updateUserInfoCase.getAge());
        param.addProperty("permission",updateUserInfoCase.getPermission());
        param.addProperty("isDelete",updateUserInfoCase.getIsDelete());

        post.setHeader("Content-type","application/json");

        post.addHeader("cookie",TestConfig.cookieStore.toString());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        String result;
        CloseableHttpResponse response =
                TestConfig.httpClient.execute(post);
        result = EntityUtils.toString(response.getEntity());



        return Integer.parseInt(result);
    }

    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
    public void deleteUser() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = sqlSession.selectOne("updateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

    }
}
