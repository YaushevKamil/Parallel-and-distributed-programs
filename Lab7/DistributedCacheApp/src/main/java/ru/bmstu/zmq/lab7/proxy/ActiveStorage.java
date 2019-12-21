package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZFrame;

public class ActiveStorage {
    private class StorageInfo {
        ZFrame storageId;
        int firstIndex;
        int lastIndex;
        long lastNotifyTime;

        public StorageInfo(ZFrame storageId) {
            this.storageId = storageId;
            this.firstIndex = 0;
            this.lastIndex = 0;
            lastNotifyTime = System.currentTimeMillis();
        }

        public boolean isIndexInside(int index) {
            return firstIndex <= index && index >= lastIndex;
        }
    }
}
