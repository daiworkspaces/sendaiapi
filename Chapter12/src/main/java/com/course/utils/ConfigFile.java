package com.course.utils;

import com.course.model.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

public class ConfigFile {

    private static ResourceBundle bundle = ResourceBundle.getBundle("application", Locale.CHINA);

    //拼接url 传入枚举类定义的的接口名称。在model层中
    public static String getUrl(InterfaceName name){

        //application中配置的test.url
        String address = bundle.getString("test.url");
        String uri ="";

        //最终的测试地址
        String testUrl;

        if (name.equals(InterfaceName.LOGIN)){
            uri = bundle.getString("login.uri");
        }

        if (name.equals(InterfaceName.ADDUSER)){
            uri = bundle.getString("addUser.uri");
        }

        if (name.equals(InterfaceName.GETUSERINFO)){
            uri = bundle.getString("getUserInfo.uri");
        }

        if (name.equals(InterfaceName.GETUSERLIST)){
            uri = bundle.getString("getUserList.uri");
        }

        if (name.equals(InterfaceName.UPDATEUSERINFO)){
            uri = bundle.getString("updateUserInfo.uri");
        }

        testUrl = address +uri;
        return testUrl;

    }
}
