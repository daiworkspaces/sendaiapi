package com.course.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Api(value = "/")
@RequestMapping("v1")
public class Demo {

    //首先获取一个执行sql语句的对象
    //启动即加载 autowired
    @Autowired
    private SqlSessionTemplate template;


    @RequestMapping(value = "/getUSerCount",method = RequestMethod.GET)
    @ApiOperation(value = "获取到用户总数",httpMethod = "GET")
    public int getUSerCount(){
        return template.selectOne("getUserCount");

    }


}
