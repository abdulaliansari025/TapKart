package com.ois.onistech.gmb.DataClass;

public class Banner {

    private String b_id;

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getB_image() {
        return b_image;
    }

    public void setB_image(String b_image) {
        this.b_image = b_image;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "b_id='" + b_id + '\'' +
                ", b_image='" + b_image + '\'' +
                '}';
    }

    public Banner(String b_id, String b_image) {
        this.b_id = b_id;
        this.b_image = b_image;
    }

    private String b_image;
}
