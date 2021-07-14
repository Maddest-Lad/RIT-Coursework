package edu.rit.cs.grocerystore;

public class Cart extends TimedObject implements Comparable<Cart> {

    private int numItems;

    public Cart() {
        this.numItems = 0;
    }

    public Cart(int numItems) {
        this.numItems = numItems;
    }

    public int getCartSize() {
        return numItems;
    }

    @Override
    public int compareTo(Cart other) {
        return Integer.compare(this.getCartSize(), other.getCartSize());
    }

    @Override
    public String toString() {
        return "Cart(" + numItems + ")";
    }
}
