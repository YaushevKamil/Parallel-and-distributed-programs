package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        StorageInfo storageInfo = new StorageInfo(storageId, firstIndex, lastIndex);
        for (StorageInfo storedStorages : storages) {
            if (storedStorages.equals(storageInfo)) {
                return;
            }
        }
        storages.add(storageInfo);
    }

    public static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }

    public List<ZFrame> getAliveStorages(int index) {
        List<StorageInfo> indInsideStorages;
        storages.stream()
                .filter(s -> s.isIndexInside(index))
                .filter(not(StorageInfo::isAlive))
                .collect(Collectors.toList())
                .forEach(s -> storages.remove(s));
        return storages.stream()
                .filter(s -> s.);
    }
}
