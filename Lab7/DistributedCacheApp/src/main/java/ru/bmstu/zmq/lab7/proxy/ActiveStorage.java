package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZFrame;

public class ActiveStorage {
    private static class StorageInfo {
        ZFrame storageId;
        int firstIndex;
        int lastIndex;
        long lastNotifyTime;

        public StorageInfo(ZFrame storageId, int firstIndex, int lastIndex) {
            this.storageId = storageId;
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
//            lastNotifyTime = System.currentTimeMillis();
            updateNotifyTime();
        }

        public boolean isIndexInside(int index) {
            return firstIndex <= index && index <= lastIndex;
        }

        public void updateNotifyTime() { // f() => f(long newTimeValue)!!!
            lastNotifyTime = System.currentTimeMillis();
        }

        public boolean isAlive() {
            return 
        }
    }


}
