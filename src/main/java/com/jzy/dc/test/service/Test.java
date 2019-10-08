package com.jzy.dc.test.service;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Proxy;

public class Test {

    /**
     * JDK动态代理涉及如下角色：
     * 业务接口interface、被代理人（target）、业务处理类（handler）、JVM在内存中生成的动态代理类$Proxy
     * @param args
     */
    public static void main(String[] args) throws Throwable {
        Person person = new Dc();
        MyHandler myHandler = new MyHandler(person);
        // 1、Proxy通过传递给它的参数（interfaces/invocationHandler）生成代理类$Proxy0
        // 2、Proxy通过传递给它的参数（ClassLoader）来加载生成的代理类$Proxy0的字节码文件
        Person proxyPerson = (Person) Proxy.newProxyInstance(person.getClass().getClassLoader(), new Class[]{Person.class}, myHandler);
        System.out.println("代理类的全路径名为：" + proxyPerson.getClass().getName());
        proxyPerson.findJob();
        proxyPerson.findLove();
        printProxyClassFile(Person.class);
    }

    /**
     * 打印代理类的class文件
     * @param clazz
     */
    private static void printProxyClassFile(Class<?> clazz) {
        byte[] data = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{clazz});
        try {
            FileOutputStream fos = new FileOutputStream("@Proxy0.class");
            fos.write(data);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
