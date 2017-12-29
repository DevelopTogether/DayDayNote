package com.daydaynote.other;

/**
 * Created by Administrator on 2017/3/1.
 */

public class Fuilt {
    String  name;
    int   imageID;

    public Fuilt(String s, int qaa) {
        this.name=s;
        this.imageID=qaa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
