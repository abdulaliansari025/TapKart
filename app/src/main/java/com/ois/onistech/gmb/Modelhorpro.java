package com.ois.onistech.gmb;


public class Modelhorpro {

    String pid,pname,catid,img;


    public Modelhorpro() {
    }


    public Modelhorpro(String pid, String catid, String pname, String img) {

        this.pid = pid;
        this.catid=catid;
        this.pname=pname;
        this.img = img;

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

    public String getCatid() {
        return catid;
    }


    public void setCatid(String catid) {
        this.catid = catid;
    }


    public String getImg()
    {
        return img;
    }


    public void setImg(String img) {
        this.img = img;
    }
}
