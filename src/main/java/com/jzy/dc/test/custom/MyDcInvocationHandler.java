package com.jzy.dc.test.custom;

import com.jzy.dc.test.service.Person;

import java.lang.reflect.Method;

/**
 * @author dc
 */
public class MyDcInvocationHandler implements DcInvocationHandler {

    private Person person;

    public MyDcInvocationHandler(Person person) {
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(person, null);
        after();
        return invoke;
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }

}
