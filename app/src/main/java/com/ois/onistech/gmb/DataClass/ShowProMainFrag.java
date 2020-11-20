package com.ois.onistech.gmb.DataClass;

import java.util.List;

public class ShowProMainFrag {

    List<Featured_Cat> data;

    @Override
    public String toString() {
        return "Product{" +
                "data=" + data +
                ", cart='" + cart + '\'' +
                '}';
    }

    public List<Featured_Cat> getData() {
        return data;
    }

    public void setData(List<Featured_Cat> data) {
        this.data = data;
    }

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String cart;

    public ShowProMainFrag(List<Featured_Cat> data, String cart)
    {
        this.data = data;
        this.cart = cart;

    }
}
