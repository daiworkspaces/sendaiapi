package com.course.testng.paramter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {
    @Test(dataProvider = "data")
    public void testDataProvider(String name,int age){
        System.out.println("name= "+ name +" age="+age);
    }

    @DataProvider(name = "data")
    public Object[][] providerData(){
        Object[][] o = new Object[][]{
                {"zhangsan",10},
                {"lisi",20},
                {"wangwu",30}
        };
        return o;
    }

    // 通过方法名的参数传递 方法test1，test2 传递对呀的参数。
    @Test(dataProvider = "methodData")
    public void test1(String name,int age){
        System.out.println("test1111 方法的name= "+ name +" age="+age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name,int age){
        System.out.println("test2222 方法的name= "+ name +" age="+age);
    }


    @DataProvider(name = "methodData")
    public Object[][] methodDataTest(Method method){
        Object[][] result = null;

        if (method.getName().equalsIgnoreCase("test1")){
            result = new Object[][]{
                    {"zhangsan",23},
                    {"lisi",26}
            };
        }else if (method.getName().equalsIgnoreCase("test2")){
            result = new  Object[][]{
                    {"wangwu",35}
            };
        }return result;

    }
}
