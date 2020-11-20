package com.ois.onistech.gmb.DataClass;

public class HomeCat {
    private String cat_id;

    public String getCat_id() {
        return cat_id;
    }

    @Override
    public String toString() {
        return "HomeCat{" +
                "cat_id='" + cat_id + '\'' +
                ", c_name='" + c_name + '\'' +
                '}';
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public HomeCat(String cat_id, String c_name) {
        this.cat_id = cat_id;
        this.c_name = c_name;
    }

    private String c_name;

}
