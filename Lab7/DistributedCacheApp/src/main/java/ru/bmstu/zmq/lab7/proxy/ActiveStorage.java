package ru.bmstu.zmq.lab7.proxy;

import org.zeromq.ZFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ActiveStorage {
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

    public ZFrame getStorage(int index) {
        return getStorages(index)
                .get(0)
    }

    public List<ZFrame> getStorages(int index) {
        storages.stream()
                .filter(s -> s.isIndexInside(index))
                .filter(not(StorageInfo::isAlive))
                .collect(Collectors.toList())
                .forEach(s -> storages.remove(s));
        return storages
                .stream()
                .filter(s -> s.isIndexInside(index))
                .map(StorageInfo::getStorageId)
                .collect(Collectors.toList());
    }
}
