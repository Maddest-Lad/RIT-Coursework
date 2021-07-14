package rit.stu;

import rit.cs.Node;
import rit.cs.Queue;

/**
 * A queue implementation that uses a Node to represent the structure.
 *
 * @param <T> The type of data the queue will hold
 * @author RIT CS
 */
public class QueueNode<T> implements Queue<T> {

    private Node<T> front;
    private Node<T> back;
    public int size;

    // Constructor
    public QueueNode() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    @Override
    public T back() {
        assert (!empty());
        return back.getData();
    }

    @Override
    public T dequeue() {
        assert (!empty());

        var current = front.getData();
        front = front.getNext();

        size--;

        return current;
    }

    @Override
    public boolean empty() {
        return front == null;
    }


    @Override
    public void enqueue(T element) {
        Node newBack = new Node<>(element, null);

        if (size == 0)
        {
            front = newBack;
            back = newBack;
        } else if (size == 1)
        {
            front.setNext(newBack);
            back = front.getNext();
        } else
        {
            back.setNext(newBack);
            back = newBack;
        }

        size++;
    }

    @Override
    public T front() {
        assert (!empty());
        return front.getData();
    }

    @Override
    public String toString() {
        assert (!empty());

        Node current = front;

        StringBuilder out = new StringBuilder();

        while(current != back) {
            out.append(current.getData());
            current = current.getNext();
        }

        return out.toString();
    }

}
