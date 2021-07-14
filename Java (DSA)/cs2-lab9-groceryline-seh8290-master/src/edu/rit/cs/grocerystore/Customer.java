package edu.rit.cs.grocerystore;

public class Customer implements Runnable {

    // Static Int So Customers Increase Each Time
    public static Integer ID = 1;

    // Member Variables
    private double delay;
    private Cart cart;
    private TSQueue<Cart> queue;
    private Integer identifier;

    // Constructor
    public Customer(Integer ID, double delay, Cart cart, TSQueue<Cart> queue) {
        this.identifier = ID;
        this.delay = delay;
        this.cart = cart;
        this.queue = queue;
    }

    // Customer Factory With Incrementing ID Numbers
    public static Customer CreateCustomer(double delay, Cart cart, TSQueue<Cart> queue) {
        Customer.ID++;
        return new Customer(ID, delay, cart, queue);
    }

    public Cart getCart() {
        return cart;
    }

    // The Fun Thread Stuff
    public void run() {
        // Sleep for the given delay time.
        try
        {
            Thread.sleep((long) delay);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        // Put the given Cart in the checkout queue.
        queue.enqueue(this.cart);

        // Print a message announcing the above has been done.
        Utilities.printf(this.toString() + " is Now In Checkout Queue" + "\n");
    }

    public String toString() {
        return "Customer " + identifier + " with " + cart.toString();
    }
}