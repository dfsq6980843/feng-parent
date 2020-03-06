package com.fengx.test;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * @author fengXiong
 * @date 2019/12/12 3:57 下午
 */
public class ConnectionPool {

    private LinkedList<Connection> linkedList = Lists.newLinkedList();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                linkedList.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (linkedList) {
                linkedList.addLast(connection);
                linkedList.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws Exception {
        synchronized (linkedList) {
            if (mills > 0) {
                while (linkedList.isEmpty()) {
                    linkedList.wait();
                }
                return linkedList.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while (linkedList.isEmpty() && remaining > 0) {
                    linkedList.wait(remaining);
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!linkedList.isEmpty()) {
                    result = linkedList.removeFirst();
                }
                return result;
            }
        }
    }
}
