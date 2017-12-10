package com.whu.domain;

public class CartItem {
    private Product product;
    private int buyNum;
    private double subTotal;


    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public CartItem(Product product, int buyNum, double subTotal) {
        this.product = product;
        this.buyNum = buyNum;
        this.subTotal = subTotal;
    }
}
