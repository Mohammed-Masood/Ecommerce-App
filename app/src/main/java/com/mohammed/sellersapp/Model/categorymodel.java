package com.mohammed.sellersapp.Model;

public class categorymodel {

    private String imageurl;
    public categorymodel(){

    }

    public categorymodel(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
