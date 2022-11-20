package com.course.model;

import lombok.Data;

@Data
public class GetUserListCase {

    private int id;
    private String userName;
    private int age;
    private String sex;
    private String expected;

    @Override
    public String toString(){
        return (
                "{id:"+id+","+
                "userName:"+userName+","+
                "age:"+age+","+
                "sex:"+sex+","+
                "expected:"+expected+"}"
        );
    }

}
