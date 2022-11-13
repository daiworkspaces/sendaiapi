package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/")
public class MyGetMethod {

    @RequestMapping(value = "/getCookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取cookies",httpMethod ="GET" )
    public String getCookies(HttpServletResponse response){
        //HttpServerletRequest 装请求信息的类
        //HTTPServerletResponse 装响应信息的类
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);

        return "恭喜你获取cookies成功";

    }

    /**
     * 要求客户端携带cookies访问
     * 这是一个需要携带cookies信息才能访问的get请求
     */

    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookie进行访问",httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)){
            return "你必须携带cookies信息来";

        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("login")
                    && cookie.getValue().equals("true")){
                return "恭喜你访问成功!";
            }
        }

        return "你必须携带cookies信息来";

    }

    /**
     * 开发一个需要携带参数才能访问的get请求
     * 第一种实现方式 url:key=value&key=value
     * 我们来模拟获取商品列表
     * http://localhost:8080/get/with/param?start=1&end=10
     */

    @RequestMapping(value = "/get/with/param",method = RequestMethod.GET)
    @ApiOperation(value = "需要携带参数的get请求",httpMethod = "GET")
    public Map<String,Integer> getList(@RequestParam Integer start,
                                       @RequestParam Integer end){
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("衬衫",300);

        return myList;
    }

    /**
     * 第二种需要携带参数访问的get请求,及返回cookie信息
     * url:ip:port/get/with/param/10/20
     */

    @RequestMapping(value ="/get/with/param/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation(value = "返回cookie及需要携带参数的GET请求",httpMethod = "GET")
    public Map myGetList(@PathVariable Integer start,
                         @PathVariable Integer end,
                         HttpServletResponse response){
        Cookie cookie = new Cookie("login","true1111");
        response.addCookie(cookie);
        Map<String,Integer> myList = new HashMap<>();
        myList.put("鞋",400);
        myList.put("干脆面",1);
        myList.put("衬衫",300);

        return myList;


    }

}
