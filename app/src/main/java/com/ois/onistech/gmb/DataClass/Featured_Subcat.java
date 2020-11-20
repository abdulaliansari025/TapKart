package com.ois.onistech.gmb.DataClass;

public class Featured_Subcat {
    public String pgm_product;
    public String pgm_rate;
    public String pgm_img;
    public String brand_name;
    public String weight_name;
    public String discount;
    public String pmm_id;
    public String ct_id;
    public String ct_price;

    @Override
    public String toString() {
        return "Featured_Subcat{" +
                "pgm_product='" + pgm_product + '\'' +
                ", pgm_rate='" + pgm_rate + '\'' +
                ", pgm_img='" + pgm_img + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", weight_name='" + weight_name + '\'' +
                ", discount='" + discount + '\'' +
                ", pmm_id='" + pmm_id + '\'' +
                ", ct_id='" + ct_id + '\'' +
                ", ct_price='" + ct_price + '\'' +
                ", status=" + status +
                ", ct_qty='" + ct_qty + '\'' +
                ", selection=" + selection +
                '}';
    }

    public String getCt_id() {
        return ct_id;
    }

    public void setCt_id(String ct_id) {
        this.ct_id = ct_id;
    }

    public String getCt_price() {
        return ct_price;
    }

    public void setCt_price(String ct_price) {
        this.ct_price = ct_price;
    }

    public Featured_Subcat(String pgm_product, String pgm_rate, String pgm_img, String brand_name, String weight_name, String discount, String pmm_id, String ct_id, String ct_price, int status, String ct_qty, int selection) {
        this.pgm_product = pgm_product;
        this.pgm_rate = pgm_rate;
        this.pgm_img = pgm_img;
        this.brand_name = brand_name;
        this.weight_name = weight_name;
        this.discount = discount;
        this.pmm_id = pmm_id;
        this.ct_id = ct_id;
        this.ct_price = ct_price;
        this.status = status;
        this.ct_qty = ct_qty;
        this.selection = selection;
    }

    public int status;

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public String ct_qty;
    private int selection = 0;

    public Featured_Subcat(String pgm_product, String pgm_rate, String pgm_img, String brand_name, String weight_name, String discount, String pmm_id, int status, String ct_qty, int selection) {
        this.pgm_product = pgm_product;
        this.pgm_rate = pgm_rate;
        this.pgm_img = pgm_img;
        this.brand_name = brand_name;
        this.weight_name = weight_name;
        this.discount = discount;
        this.pmm_id = pmm_id;
        this.status = status;
        this.ct_qty = ct_qty;
        this.selection = selection;
    }

    public Featured_Subcat(String pgm_product, String pgm_rate, String pgm_img, String brand_name, String weight_name, String discount, String pmm_id, int status, String ct_qty) {
        this.pgm_product = pgm_product;
        this.pgm_rate = pgm_rate;
        this.pgm_img = pgm_img;
        this.brand_name = brand_name;
        this.weight_name = weight_name;
        this.discount = discount;
        this.pmm_id = pmm_id;
        this.status = status;
        this.ct_qty = ct_qty;
    }

    public String getPgm_product() {
        return pgm_product;
    }

    public void setPgm_product(String pgm_product) {
        this.pgm_product = pgm_product;
    }

    public String getPgm_rate() {
        return pgm_rate;
    }

    public void setPgm_rate(String pgm_rate) {
        this.pgm_rate = pgm_rate;
    }

    public String getPgm_img() {
        return pgm_img;
    }

    public void setPgm_img(String pgm_img) {
        this.pgm_img = pgm_img;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getWeight_name() {
        return weight_name;
    }

    public void setWeight_name(String weight_name) {
        this.weight_name = weight_name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPmm_id() {
        return pmm_id;
    }

    public void setPmm_id(String pmm_id) {
        this.pmm_id = pmm_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCt_qty() {
        return ct_qty;
    }

    public void setCt_qty(String ct_qty) {
        this.ct_qty = ct_qty;
    }
}
