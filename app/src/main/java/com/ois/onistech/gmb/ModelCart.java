package com.ois.onistech.gmb;

public class ModelCart {

    String pid,pname,price,image,unit,qty,status,rid;

    public ModelCart() {
    }

    public ModelCart(String pid, String pname, String price, String image, String unit, String qty, String status,String rid) {

        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.image = image;
        this.unit=unit;
        this.qty=qty;
        this.status = status;
        this.rid = rid;

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
