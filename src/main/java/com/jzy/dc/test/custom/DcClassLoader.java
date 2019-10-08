package com.jzy.dc.test.custom;

import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 自定义类加载器
 * 目的：因为是手写JDK动态代理，那么我们将自己在内存中生成动态代理类。需要使用到自定义的类加载器去加载
 * 通过重新defineClass方法，在指定路径下加载指定的字节码文件
 * @author dc
 */
public class DcClassLoader extends ClassLoader{
    /**
     * 代理类的加载路径
     */
    private File dir;
    /**
     * 代理类的包名
     */
    private String proxyClassPackage;

    public File getDir() {
        return dir;
    }

    public String getProxyClassPackage() {
        return proxyClassPackage;
    }

    public DcClassLoader(String dir, String proxyClassPackage) {
        this.dir = new File(dir);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (!StringUtils.isEmpty(dir)){
            File file = new File(dir, name + ".class");
            if (file.exists()) {
                try {
                    byte[] classBytes = FileCopyUtils.copyToByteArray(file);
                    return defineClass(proxyClassPackage + "." + name, classBytes, 0, classBytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }
}
