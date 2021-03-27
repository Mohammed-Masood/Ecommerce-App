package com.mohammed.sellersapp.Model;

public class orderdetailsmodel {

    String Location;
    String UserKey;
    String OrderKey;
    String Time;

    public orderdetailsmodel(String Location, String UserKey,String OrderKey,String time) {
       this.Location = Location;
       this.UserKey = UserKey;
       this.OrderKey = OrderKey;
       Time = time;
    }

    public orderdetailsmodel() {
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getOrderKey() {
        return OrderKey;
    }

    public void setOrderKey(String orderKey) {
        OrderKey = orderKey;
    }
}
