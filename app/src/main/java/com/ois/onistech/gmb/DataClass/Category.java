package com.ois.onistech.gmb.DataClass;

import java.util.List;

public class Category {

    public String cat_id;
    public String cat_name;

    @Override
    public String toString() {
        return "Category{" +
                "cat_id='" + cat_id + '\'' +
                ", cat_name='" + cat_name + '\'' +
                ", cat_parent='" + cat_parent + '\'' +
                ", cat_desc='" + cat_desc + '\'' +
                ", cat_img='" + cat_img + '\'' +
                ", sub_category=" + sub_category +
                '}';
    }

    public String cat_parent;

    public Category(String cat_id, String cat_name, String cat_parent, String cat_desc, String cat_img, List<SubCategory> sub_category) {
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.cat_parent = cat_parent;
        this.cat_desc = cat_desc;
        this.cat_img = cat_img;
        this.sub_category = sub_category;
    }

    public String cat_desc;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_parent() {
        return cat_parent;
    }

    public void setCat_parent(String cat_parent) {
        this.cat_parent = cat_parent;
    }

    public String getCat_desc() {
        return cat_desc;
    }

    public void setCat_desc(String cat_desc) {
        this.cat_desc = cat_desc;
    }

    public String getCat_img() {
        return cat_img;
    }

    public void setCat_img(String cat_img) {
        this.cat_img = cat_img;
    }

    public List<SubCategory> getSub_category() {
        return sub_category;
    }

    public void setSub_category(List<SubCategory> sub_category) {
        this.sub_category = sub_category;
    }

    public String cat_img;
    public List<SubCategory> sub_category;

}
