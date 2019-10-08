package com.jzy.dc.test.custom;

import java.lang.reflect.Method;

/**
 *
 * @author dc
 */
public interface DcInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
