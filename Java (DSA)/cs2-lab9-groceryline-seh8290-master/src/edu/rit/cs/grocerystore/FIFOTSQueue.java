package edu.rit.cs.grocerystore;

import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

public class FIFOTSQueue<E extends TimedObject> implements TSQueue<E> {

    private Queue<E> queue;

    public FIFOTSQueue() {
        this.queue = new SynchronousQueue<E>();
    }

    @Override
    public synchronized int enqueue(E value) {
        this.queue.offer(value);
        return this.queue.size();
    }

    @Override
    public synchronized E dequeue() {
        if (this.queue.size() >= 1)
        {
            return this.queue.poll();
        }
        return null;
    }

    @Override
    public int size() {
        return queue.size();
    }
}

