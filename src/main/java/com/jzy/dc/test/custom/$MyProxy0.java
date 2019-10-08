package com.jzy.dc.test.custom;
import java.lang.reflect.Method;
public class $MyProxy0 implements com.jzy.dc.test.service.Person{
private DcInvocationHandler handler;
public $MyProxy0 (DcInvocationHandler handler) {this.handler = handler; }
public void findJob() throws Throwable {
Method method1 = com.jzy.dc.test.service.Person.class.getMethod("findJob", new Class[]{});
this.handler.invoke(this, method1, null);
}
public void findLove() throws Throwable {
Method method1 = com.jzy.dc.test.service.Person.class.getMethod("findLove", new Class[]{});
this.handler.invoke(this, method1, null);
}
}