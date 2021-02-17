package com.mohammed.sellersapp.Model;

import android.net.Uri;

public class additemmodel {

    String CategoryName;


    public additemmodel(String categoryname) {
        this.CategoryName = categoryname;

    }

    public additemmodel() {
    }

    public String getCategoryname() {
        return CategoryName;
    }

    public void setCategoryname(String categoryname) {
        this.CategoryName = categoryname;
    }

}
