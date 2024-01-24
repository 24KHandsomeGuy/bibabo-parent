package com.bibabo.abtest.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 17:00
 * @Description
 */
public abstract class AbThreadLocalTask implements Runnable {

    private Object obj;
    private static volatile Field inheritableThreadLocalsField;
    private static volatile Class threadLocalMapClazz;
    private static volatile Method createInheritedMapMethod;
    private static final Object accessLock = new Object();


    public AbThreadLocalTask() {
        try {
            Thread currentThread = Thread.currentThread();
            Field field = getInheritableThreadLocalsField();
            // 得到当前线程中的inheritableThreadLocals熟悉值ThreadLocalMap, key是各种inheritableThreadLocal，value是值
            Object threadLocalMapObj = field.get(currentThread);
            if (threadLocalMapObj != null) {
                Class threadLocalMapClazz = getThreadLocalMapClazz();
                Method method = getCreateInheritedMapMethod(threadLocalMapClazz);
                // 创建一个新的ThreadLocalMap
                Object newThreadLocalMap = method.invoke(ThreadLocal.class, threadLocalMapObj);
                obj = newThreadLocalMap;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private Class getThreadLocalMapClazz() {
        if (inheritableThreadLocalsField == null) {
            return null;
        }
        if (threadLocalMapClazz == null) {
            synchronized (accessLock) {
                if (threadLocalMapClazz == null) {
                    threadLocalMapClazz = inheritableThreadLocalsField.getType();
                }
            }
        }
        return threadLocalMapClazz;
    }

    private Field getInheritableThreadLocalsField() {
        if (inheritableThreadLocalsField != null) {
            return inheritableThreadLocalsField;
        }
        synchronized (accessLock) {
            if (inheritableThreadLocalsField == null) {
                try {
                    Field field = Thread.class.getDeclaredField("inheritableThreadLocals");
                    field.setAccessible(true);
                    inheritableThreadLocalsField = field;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return inheritableThreadLocalsField;
    }

    private Method getCreateInheritedMapMethod(Class threadLocalMapClazz) {
        if (threadLocalMapClazz == null || createInheritedMapMethod != null) {
            return createInheritedMapMethod;
        }
        synchronized (accessLock) {
            if (createInheritedMapMethod == null) {
                try {
                    Method method = ThreadLocal.class.getDeclaredMethod("createInheritedMap", threadLocalMapClazz);
                    method.setAccessible(true);
                    createInheritedMapMethod = method;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
        return createInheritedMapMethod;
    }

    public abstract void runTask();

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        Field field = getInheritableThreadLocalsField();
        try {
            if (obj != null && field != null) {
                field.set(currentThread, obj);
                obj = null;
                boolean isSet = true;
            }
            // 执行任务
            runTask();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            // 最后将线程中的InheritableThreadLocals设置为null
            try {
                field.set(currentThread, null);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
