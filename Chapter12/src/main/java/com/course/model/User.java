package com.course.model;

//将创建的数据库表映射到此 创建表对应的字段。然后使用lombok工具直接使用数据

import lombok.Data;

@Data
public class User {

    private int id;
    private String userName;
    private String password;
    private String sex;
    private int age;
    private int permission;
    private int isDelete;

    @Override
    public String toString(){
        return (
                "{id:"+id+","+
                "userName:"+userName+","+
                "password:"+password+","+
                "sex:"+sex+","+
                "age:"+age+","+
                "permission:"+permission+","+
                "isDelete:"+isDelete+"}"
                );
    }
}
