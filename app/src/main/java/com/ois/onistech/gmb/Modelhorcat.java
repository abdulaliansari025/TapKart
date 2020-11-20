package com.ois.onistech.gmb;


public class Modelhorcat {

    String sid,sname,cid,image;


    public Modelhorcat() {
    }


    public Modelhorcat(String sid, String cid, String sname, String image) {

        this.sid = sid;
        this.cid=cid;
        this.sname=sname;
        this.image = image;

    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
