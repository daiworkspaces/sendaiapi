package com.course.controller;


import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@RestController
@Api(value = "/")
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;




    @ApiOperation(value = "登录接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public Boolean login(HttpServletResponse response ,@RequestBody Map<String,String> params)
    {
        System.out.println("查看传递进来的参数params"+params.toString());
//
//        User user = new User();
//        params = new HashMap<>();
//        params.put("userName", user.getUserName() );
//        params.put("password", user.getUserName());


        int i = template.selectOne("loginCase",params);
        System.out.println(template.toString());

        System.out.println("================="+i);

        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        if (i==1){

            return  true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户接口",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public boolean addUser(HttpServletRequest request ,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int result = 0;
        if (x!= null){
            result = template.insert("addUser",user);
        }
        if (result > 0){
            log.info("添加用户的数量是："+ result);
            return true;
        }
        return false;

    }

    @ApiOperation(value = "获取用户(列表)信息接口",httpMethod = "POST")
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        if (x.equals(true)) {
            List<User> users = template.selectList("getUserInfo", user);
            log.info("getUserInfo获取到的用户数量是"+users.size());
            return users;
        }else {
            return  null;
        }
        }

    @ApiOperation(value = "更新/删除用户接口",httpMethod = "POST")
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if (x.equals(true)){
            i = template.update("updateUserInfo",user);
        }
        log.info("更新数据的条目数为："+i);
        return i;

        }



    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            log.info("cookies为空");
            return false;
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals("login") &&
                    cookie.getValue().equals("true")) {
                log.info("cookies验证通过");
                return true;
            }
            }
        return false;
        }

}
