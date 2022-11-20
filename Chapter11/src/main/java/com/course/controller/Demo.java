package com.course.controller;

import com.course.model.User111;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@Api(value = "/")
@RequestMapping("v1")
public class Demo {

    //首先获取一个执行sql语句的对象
    //启动即加载 autowired
    @Autowired
    private SqlSessionTemplate template;


    @RequestMapping(value = "/getUSerCount",method = RequestMethod.GET,produces = {"text/plain;charset=UTF-8"})
    @ApiOperation(value = "获取到用户总数",httpMethod = "GET")
    public int getUSerCount(){
        return template.selectOne("getUserCount");

    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "添加用户",httpMethod = "POST")
    public int addUser(@RequestBody User111 user111){
        return  template.insert("addUser", user111);

    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "更新用户信息",httpMethod = "POST")
    public int updateUser(@RequestBody User111 user111){
        return template.update("updateUser", user111);
    }

    //produces 的写法是为了解决删除问题返回500 但是数据库的数据已经被删除的问题
    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET,consumes = MediaType.ALL_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "删除用户信息",httpMethod = "GET")
    public int delUser(@RequestParam int id){
           return template.delete("deleteUser",id);
    }



}
