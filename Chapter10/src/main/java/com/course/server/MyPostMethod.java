package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("v1")
@Api(value = "/")
public class MyPostMethod {
    //这个变量是用来装我们cookies信息的
    private static Cookie cookie;

    //用户登录成功获取到cookies，然后再访问其他接口获取到列表

    //produces 设置请求类型及中文乱码问题
    //当使用RequestParam 参数传递的参数就是表单的格式
    //如果用body参数 就是json格式的参数
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = {"text/plain;charset=UTF-8"})
    @ApiOperation(value = "登录接口，成功后获取cookies信息",httpMethod = "POST")
    public String login(HttpServletResponse response,
                            @RequestParam(value="userName",required = true) String userName,
                        @RequestParam(value="password",required = true) String password )
    {
        if (userName.equals("zhangsan") && password.equals("123456")) {
            cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "恭喜你登录成功了！";
        }
        return "用户名或密码错误！ ";

    }

    @RequestMapping(value = "/getUserLIst",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "登录成功后，获取用户列表",httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                            @RequestBody User u){
        User user ;
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies是否合法
        for (Cookie c : cookies){
            if (c.getName().equals("login")
                    && c.getValue().equals("true")
                    && u.getUserName().equals("zhangsan")
                    && u.getPassword().equals("123456")){
                user = new User();
                user.setName("lisi");
                user.setAge("18");
                user.setSex("man");
                return user.toString();
            }
        }
        return "参数不合法";

    }




}
