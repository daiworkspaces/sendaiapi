package com.course.testng;

import org.testng.annotations.Test;

public class TimeOutTest {

    //单位为毫秒
    @Test(timeOut = 3000)
    public void testSuccess() throws InterruptedException {
        Thread.sleep(2000);//线程休息200毫秒，还没有触发超时3000，运行成功

    }

    @Test(timeOut = 2000)
    public void testFail()throws InterruptedException{
        Thread.sleep(3000);//线程休息300毫秒，有触发超时2000，运行失败
    }
}
