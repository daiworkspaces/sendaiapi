package com.course.cases;


import com.course.config.TestConfig;
import com.course.model.AddUserCase;
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

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException {
        SqlSession sqlSession = DataBaseUtil.getSqlSession();
        AddUserCase addUserCase = sqlSession.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发请求，获取结果。
        String result = getResult(addUserCase);

        //验证返回结果
        User user = sqlSession.selectOne("addUser",addUserCase);
        Assert.assertEquals(addUserCase.getExpected(),result);

    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JsonObject param = new JsonObject();
        param.addProperty("userName",addUserCase.getUserName());
        param.addProperty("password",addUserCase.getPassword());
        param.addProperty("sex",addUserCase.getSex());
        param.addProperty("age",addUserCase.getAge());
        param.addProperty("permission",addUserCase.getPermission());
        param.addProperty("isDelete",addUserCase.getIsDelete());

        //设置头信息 //设置cookies
        post.setHeader("Content-type","application/json");
        post.addHeader("Cookie", TestConfig.cookieStore.toString());


        StringEntity entity = new StringEntity(param.toString(),"UTF-8");
        post.setEntity(entity);


        String result;//存放返回结果
        CloseableHttpResponse response = TestConfig.httpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"UTF-8");

        return result;
    }
}
