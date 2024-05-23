package com.example.satvik;
public class MenuItem2 {
    private int imageResource;
    private String itemName;
    private String itemPrice;

    public MenuItem2( String itemName, String itemPrice) {
//        this.imageResource = imageResource;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
