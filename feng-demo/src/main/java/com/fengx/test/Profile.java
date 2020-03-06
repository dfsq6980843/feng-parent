package com.fengx.test;

import java.util.concurrent.TimeUnit;

/**
 * @author fengXiong
 * @date 2019/12/12 2:47 下午
 */
public class Profile {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<>();

    protected Long initialValue() {
        return System.currentTimeMillis();
    }

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws Exception {
        Profile.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profile.end() + " miles");
    }

}
