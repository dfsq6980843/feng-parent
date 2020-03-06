package com.fengx.test;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author fengXiong
 * @date 2019/12/19 1:50 下午
 */
public class Cache {

    static Map<String, Object> map = Maps.newHashMap();

    static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    static Lock readLock = reentrantReadWriteLock.readLock();

    static Lock writeLock = reentrantReadWriteLock.writeLock();

    public static final Object get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static final Object put(String key, String value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public static final void clear() {
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }
}
