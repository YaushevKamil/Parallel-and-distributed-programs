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
        storage = new ArrayList<Integer>(Collections.nCopies(lastInd-firstInd+1, initialValue));
    }
}
