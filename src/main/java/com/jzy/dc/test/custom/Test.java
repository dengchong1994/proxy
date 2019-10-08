package com.jzy.dc.test.custom;

import com.jzy.dc.test.service.Dc;
import com.jzy.dc.test.service.Person;

public class Test {

    public static void main(String[] args) throws Throwable{
        Person person = new Dc();
        DcInvocationHandler handler = new MyDcInvocationHandler(person);
        Person proxyPerson = (Person) DcProxy.newProxyInstance(new DcClassLoader("D:\\dc-test\\src\\main\\java\\com\\jzy\\dc\\test\\custom", "com.jzy.dc.test.custom"), Person.class, handler);
        System.out.println(proxyPerson.getClass().getName());
        proxyPerson.findJob();
        proxyPerson.findLove();
    }

}
