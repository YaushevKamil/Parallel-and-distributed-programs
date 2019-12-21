package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZFrame;

import java.util.ArrayList;
import java.util.List;

public class ActiveStorage {
    private static class StorageInfo {
        private static final int NOTIFY_DURATION_MS = 500;

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
            long currentTime = System.currentTimeMillis();
            return (currentTime - lastNotifyTime) < 2 * NOTIFY_DURATION_MS;
        }

        public ZFrame getStorageId() {
            return storageId;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof StorageInfo) && this.storageId == ((StorageInfo) o).storageId;
        }
    }

    private List<StorageInfo> storages;

    public  ActiveStorage() {
        storages = new ArrayList<>();
    }

    public void insertStorage(ZFrame storageId, int firstIndex, int lastIndex) {
        storages.
    }

}
