package com.mohammed.sellersapp.Model;

public class fbordermodel {

    int AmountRequired;
    String ItemKey;
    int TotalPrice;
    String Categoryname;


    public fbordermodel(int amountRequired, String itemKey, int totalPrice,String Categoryname) {
        AmountRequired = amountRequired;
        ItemKey = itemKey;
        TotalPrice = totalPrice;
        this.Categoryname = Categoryname;
    }

    public fbordermodel() {
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

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }
}
