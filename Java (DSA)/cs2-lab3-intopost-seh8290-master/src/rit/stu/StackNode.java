package rit.stu;

import rit.cs.Node;
import rit.cs.Stack;

/**
 * A stack implementation that uses a Node to represent the structure.
 *
 * @param <T> The type of data the stack will hold
 * @author RIT CS
 */
public class StackNode<T> implements Stack<T> {

    public int size;
    private Node<T> top;

    /**
     * Create an empty stack.
     */
    public StackNode() {
        top = null;
        size = 0;
    }

    @Override
    public boolean empty() {
        return top == null;
    }

    @Override
    public T pop() {
        assert (!empty());

        Node<T> current = top;

        if (top == null)
        {
            return (T)"";
        }

        // Stack With One Node
        if (top.getNext() == null)
        {
            top = null;
        }
        // Stack With More >1 Nodes
        else
        {
            top = top.getNext();
        }

        size--;

        return current.getData();
    }

    @Override
    public void push(T element) {
        // This Seems Deceptively Simple
        top = new Node<>(element, top);
        size++;
    }

    public boolean checkForFuckery() {
        try
        {
            var x = top.getNext();
        } catch (NullPointerException e)
        {
            return true;
        }
        return false;
    }

    @Override
    public T top() {
        assert (top != null);
        return top.getData();
    }

    @Override
    public String toString() {
        assert (!empty());

        if (size == 0)
        {
            return "";
        } else if (size == 1)
        {
            return "" + top.getData();
        } else
        {

            Node current = top;

            StringBuilder out = new StringBuilder();

            while (current.getNext() != null)
            {
                out.append(current.getData());
                current = current.getNext();
            }

            return out.toString();
        }
    }
}
