package logic;

import gui.CartEntry;

import static logic.OrderStatus.IN_PROCESS;

public class Order {

    public Customer getCustomer() {
        return customer;
    }

    public CartEntry[] getCart() {
        return cart;
    }

    public Address getAddress() {
        return address;
    }

    public double getCost() {
        return cost;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    private Customer customer;
    private CartEntry[] cart;
    private Address address;
    private double cost;
    private long orderTime;
    private OrderStatus status;

    //variable for driver

    public Order(Customer customer, CartEntry[] cart) {
        this.customer = customer;
        this.cart = cart;

        address = customer.getAddress();
        orderTime = System.currentTimeMillis();
        status = IN_PROCESS;

        for (CartEntry item : cart) {
            cost += item.getPrice();
        }
    }


}
