package com.whu.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
//    购物车封装
    private Map<String, CartItem> cartItems = new HashMap<>();
//    总计
    private double total;

    public Map<String, CartItem> getCartItems() {
        return cartItems;
    }

    public Cart() {
    }

    public Cart(Map<String, CartItem> cartItems, double total) {
        this.cartItems = cartItems;
        this.total = total;
    }

    public void setCartItems(Map<String, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
