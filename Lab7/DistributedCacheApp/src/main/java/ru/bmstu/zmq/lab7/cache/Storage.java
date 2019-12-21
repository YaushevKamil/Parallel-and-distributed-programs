package ru.bmstu.zmq.lab7.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Storage {
    private List<Integer> storage;
    private int firstInd;
    private int lastInd;

    public Storage(int firstInd, int lastInd, int initialValue) {
        this.firstInd = firstInd;
        this.lastInd = lastInd;
        storage = new ArrayList<>(Collections.nCopies((lastInd - firstInd) + 1, initialValue));
    }

    public int get(int ind) {
        return storage.get(ind - firstInd);
    }

    public int put(int ind, int value) {
        return storage.set(ind - firstInd, value);
    }

    public int getFirstInd() {
        return this.firstInd;
    }

    public int getLastInd() {
        return this.lastInd;
    }
}
