package com.ois.onistech.gmb;

/**
 * Created by asus-pc on 5/7/2018.
 */

public class Model {

    String id,name,price,aprice,image,status,kg_pc,data,weight;


    public Model() {
    }

    public Model( String id, String name, String price, String aprice,String image,String status,String kg_pc,String data,String weight) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.aprice = aprice;
        this.image = image;
        this.status = status;
        this.kg_pc = kg_pc;
        this.data = data;
        this.weight=weight;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKg_pc() {
        return kg_pc;
    }

    public void setKg_pc(String kg_pc) {
        this.kg_pc = kg_pc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAprice() {
        return aprice;
    }

    public void setAprice(String aprice) {
        this.aprice = aprice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
