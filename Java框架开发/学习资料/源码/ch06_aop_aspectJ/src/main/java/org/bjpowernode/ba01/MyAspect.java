package org.bjpowernode.ba01;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class MyAspect {
    @Before(value = "execution(public void org.bjpowernode.ba01.SomeServiceImpl.doSome(..))")
    public void addSome(){
        System.out.println("---添加功能---");
    }
}
