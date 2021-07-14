package edu.rit.cs.grocerystore;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CustomerPool {

    private List<Customer> customers;
    private List<Cart> carts;

    public CustomerPool(TSQueue<Cart> checkoutLine, int numCustomers, int avgLoad, double avgDelay) {
        customers = new LinkedList<>();
        carts = new LinkedList<>();

        Random rand = new Random();

        double delay = 0;

        // Build The Customers
        for (int i = 0; i < numCustomers; i++)
        {
            Cart cart = new Cart((int) Utilities.sinePDFDelay(rand, avgLoad));
            delay += Utilities.sinePDFDelay(rand, avgDelay);
            customers.add(Customer.CreateCustomer(delay, cart, checkoutLine));
            carts.add(cart);
        }
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public List<Cart> simulateCustomers() throws InterruptedException {
        LinkedList<Cart> carts = new LinkedList<>();
        for (Customer customer : customers)
        {
            Thread thread = new Thread(customer);
            thread.start();
            carts.add(customer.getCart());
            thread.join();
            customer.getCart().enterQueue();
        }

        return carts;
    }

}
