package edu.rit.cs.grocerystore;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PriorityTSQueue<E extends TimedObject & Comparable<E>> implements TSQueue<E> {

    private List<E> list;

    public PriorityTSQueue() {
        this.list = Collections.synchronizedList(new LinkedList<>());
    }

    @Override
    public synchronized int enqueue(E value) {
        this.list.add(value);
        return this.list.size();
    }

    @Override
    public synchronized E dequeue() {

        if (this.list.size() >= 1)
        {
            return this.list.get(0);
        }
        return null;
    }

    @Override
    public int size() {
        return list.size();
    }
}
