package com.yc.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyJunitRunner {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class cls=Class.forName("com.yc.MyCalculatorTest");


        Method[] ms=cls.getDeclaredMethods();
        List<Method> testMethods=new ArrayList<Method>();
        Method beforeMethod=null;
        Method afterMethod=null;
        Method beforeClassMethod=null;
        Method afterClassMethod=null;

        for(Method m:ms){
            if(m.isAnnotationPresent(MyTest.class)){
                testMethods.add(m);
            }
            if(m.isAnnotationPresent(MyBefore.class)){
                beforeMethod=m;
            }
            if(m.isAnnotationPresent(MyAfter.class)){
                afterMethod=m;
            }
            if(m.isAnnotationPresent(MyBeforeClass.class)){
                beforeClassMethod=m;
            }
            if(m.isAnnotationPresent(MyAfterClass.class)){
                afterClassMethod=m;
            }
        }

        if(testMethods==null || testMethods.size()<=0){
            throw new RuntimeException("没有要测试的方法");
        }
        Object o=cls.newInstance();
        beforeClassMethod.invoke(o,null);
        for(Method m:testMethods){
            if(beforeMethod!=null){
                beforeMethod.invoke(o,null);
            }
            m.invoke(o,null);
            if(afterMethod!=null){
                afterMethod.invoke(o,null);
            }
        }
        afterClassMethod.invoke(o,null);
    }
}
