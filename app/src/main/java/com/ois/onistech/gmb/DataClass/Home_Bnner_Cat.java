package com.ois.onistech.gmb.DataClass;

import java.util.List;

public class Home_Bnner_Cat {
    List<Banner> banner;
    List<HomeCat> cart;

    @Override
    public String toString() {
        return "Home_Bnner_Cat{" +
                "banner=" + banner +
                ", cart=" + cart +
                '}';
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<HomeCat> getCart() {
        return cart;
    }

    public void setCart(List<HomeCat> cart) {
        this.cart = cart;
    }

    public Home_Bnner_Cat(List<Banner> banner, List<HomeCat> cart) {
        this.banner = banner;
        this.cart = cart;
    }
}
