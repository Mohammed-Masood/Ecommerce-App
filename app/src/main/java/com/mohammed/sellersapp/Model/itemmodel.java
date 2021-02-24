package com.mohammed.sellersapp.Model;

public class itemmodel {

    boolean InStock;
    String ItemName;
    String Price;
    String Amount;

    public itemmodel(boolean inStock, String itemName, String price,String Amount) {
        InStock = inStock;
        ItemName = itemName;
        Price = price;
        this.Amount = Amount;
    }


    public itemmodel() {
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public boolean isInStock() {
        return InStock;
    }

    public void setInStock(boolean inStock) {
        InStock = inStock;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
