package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DataBaseUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        //发送请求获取结果
        JsonArray resultJson = getJsonResult(getUserInfoCase);


        //验证结果
        //先取数据库信息
        User user = sqlSession.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List userList = new ArrayList();
        userList.add(user);

        JsonArray jsonArray = new Gson().toJsonTree(
                userList).getAsJsonArray();

        Assert.assertEquals(jsonArray,resultJson);

    }

    private JsonArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JsonObject param = new JsonObject();
        param.addProperty("id",getUserInfoCase.getUserId());

        post.setHeader("Content-type","application/json");
        post.addHeader("cookie",TestConfig.cookieStore.toString());
        StringEntity entity = new StringEntity(param.toString(),"UTF-8");
        post.setEntity(entity);

        String result;
        CloseableHttpResponse response = TestConfig.httpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"UTF-8");


        List resultList = Arrays.asList(result);

        JsonArray array = new Gson().toJsonTree(resultList).getAsJsonArray();
        return array;
    }
}
