package com.mohammed.sellersapp.Model;

public class itemmodel {

    boolean InStock;
    String ItemName;
    String Price;

    public itemmodel(boolean inStock, String itemName, String price) {
        InStock = inStock;
        ItemName = itemName;
        Price = price;
    }

    public itemmodel() {
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
