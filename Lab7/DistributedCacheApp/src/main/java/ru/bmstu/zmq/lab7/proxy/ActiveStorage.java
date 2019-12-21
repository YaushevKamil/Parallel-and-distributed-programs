package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZFrame;

public class ActiveStorage {
    private class StorageInfo {
        ZFrame storageId;
        int firstIndex;
        int lastIndex;
        long lastNotifyTime;

        public StorageInfo(ZFrame storageId, int firstIndex, int lastIndex) {
            this.storageId = storageId;
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            lastNotifyTime = System.currentTimeMillis();
        }

        public boolean isIndexInside(int index) {
            return firstIndex <= index && index <= lastIndex;
        }

        public
    }
}
