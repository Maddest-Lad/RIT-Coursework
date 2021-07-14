package edu.rit.cs.grocerystore;

public class Clerk implements Runnable {

    TSQueue<Cart> checkoutLine;

    public Clerk(TSQueue<Cart> checkoutLine) {
        this.checkoutLine = checkoutLine;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) {}

        while (checkoutLine.size() > 0)
        {
            Cart current = checkoutLine.dequeue();
            System.out.println("Now Processing : " + current.toString());

            try {
                Thread.sleep(current.getCartSize() * Utilities.TIME_PER_CART_ITEM);
            } catch (InterruptedException ignored) {}
            current.servicingDone();

        }
    }

}
