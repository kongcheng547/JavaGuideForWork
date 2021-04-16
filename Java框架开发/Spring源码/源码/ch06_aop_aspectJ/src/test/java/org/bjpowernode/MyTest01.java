package org.bjpowernode;

import org.bjpowernode.ba01.SomeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest01 {
    @Test
    public void myTest01(){
        String config="ApplicationContext.xml";
        ApplicationContext ctx=new ClassPathXmlApplicationContext(config);
        SomeService proxy= (SomeService) ctx.getBean("someService");
        proxy.doSome("Zhangsan",20);
    }
}
