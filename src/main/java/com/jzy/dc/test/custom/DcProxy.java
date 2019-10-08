package com.jzy.dc.test.custom;

import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * DcProxy相当于是JDK中的Proxy。
 * 1、根据interfaces使用反射机制构造动态代理类需要的方法
 * 2、将生成动态代理类进行编译生成字节码文件，利用自定义的类加载器进行加载到JVM中
 * 3、使用反射机制实例化动态代理类，并在实例化时，初始化handler
 * @author dc
 */
public class DcProxy {

    private static final String rn = "\r\n";

    public static Object newProxyInstance(DcClassLoader classLoader, Class<?> interfaces, DcInvocationHandler handler) {
        Assert.notNull(handler, "handler is null");
        // 生成源文件
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(classLoader.getProxyClassPackage()).append(";").append(rn);
        sb.append("import java.lang.reflect.Method;").append(rn);
        sb.append("public class $MyProxy0 implements ").append(interfaces.getName()).append("{").append(rn);
        sb.append("private DcInvocationHandler handler;").append(rn);
        sb.append("public $MyProxy0 (DcInvocationHandler handler) {").append("this.handler = handler; }").append(rn);
        sb.append(constructMethod(interfaces));
        sb.append("}");
        String fileName = classLoader.getDir() + File.separator + "$MyProxy0.java";
        File proxyFile = new File(fileName);
        try {
            // 编译
            compile(sb, proxyFile);
            // 使用自定义类加载器进行加载
            Class proxyClass = classLoader.findClass("$MyProxy0");
            // 指定handler，创造代理类的实例
            Constructor constructor = proxyClass.getConstructor(DcInvocationHandler.class);
            return constructor.newInstance(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造方法
     * @param interfaces
     */
    private static String constructMethod(Class<?> interfaces){
        StringBuilder sb = new StringBuilder();
        for (Method method : interfaces.getMethods()) {
            sb.append("public void ").append(method.getName()).append("() throws Throwable {").append(rn);
            sb.append("Method method1 = ").append(interfaces.getName()).append(".class.getMethod(\"").append(method.getName()).append("\", new Class[]{});").append(rn);
            sb.append("this.handler.invoke(this, method1, null);").append(rn);
            sb.append("}").append(rn);
        }
        return sb.toString();
    }

    /**
     * 将java源文件编译成字节码文件
     * @param sb
     * @param proxyFile
     * @throws IOException
     */
    private static void compile(StringBuilder sb, File proxyFile) throws IOException {
        FileCopyUtils.copy(sb.toString().getBytes(), proxyFile);
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
        Iterable javaFile = standardJavaFileManager.getJavaFileObjects(proxyFile);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null, null, null, javaFile);
        task.call();
        standardJavaFileManager.close();
    }

}
