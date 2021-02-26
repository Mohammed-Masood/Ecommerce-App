package com.mohammed.sellersapp.Model;

public class OrderModel {

    int AmountRequired;
    String Categoryname;
    String ItemKey;
    String OrderKey;
    int TotalPrice;


    public OrderModel(int amountRequired, String itemKey, String orderKey, int totalPrice,String Categoryname) {
        AmountRequired = amountRequired;
        ItemKey = itemKey;
        OrderKey = orderKey;
        TotalPrice = totalPrice;
        this.Categoryname = Categoryname;
    }

    public OrderModel() {
    }

    public String getCategoryname() {
        return Categoryname;
    }

    public void setCategoryname(String categoryname) {
        Categoryname = categoryname;
    }

    public int getAmountRequired() {
        return AmountRequired;
    }

    public void setAmountRequired(int amountRequired) {
        AmountRequired = amountRequired;
    }

    public String getItemKey() {
        return ItemKey;
    }

    public void setItemKey(String itemKey) {
        ItemKey = itemKey;
    }

    public String getOrderKey() {
        return OrderKey;
    }

    public void setOrderKey(String orderKey) {
        OrderKey = orderKey;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }
}
