package com.mohammed.sellersapp.Model;

import java.util.ArrayList;

public class retreiveordermodel {

    ArrayList<fbordermodel> Order_keys;
    String User_key;
    String Location;

    public retreiveordermodel(ArrayList<fbordermodel> order_keys, String user_key,String location) {
        Order_keys = order_keys;
        User_key = user_key;
        Location = location;
    }

    public retreiveordermodel() {
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public ArrayList<fbordermodel> getOrder_keys() {
        return Order_keys;
    }

    public void setOrder_keys(ArrayList<fbordermodel> order_keys) {
        Order_keys = order_keys;
    }

    public String getUser_key() {
        return User_key;
    }

    public void setUser_key(String user_key) {
        User_key = user_key;
    }
}
