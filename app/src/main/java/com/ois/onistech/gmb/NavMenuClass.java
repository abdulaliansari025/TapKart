package com.ois.onistech.gmb;

import android.view.Menu;

import java.util.ArrayList;

class NavMenuClass{
    public String id;
    public String name;

    public NavMenuClass(String id,String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}