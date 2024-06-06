package com.example.satvik;

public class OrderHistoryMenuItem {
    private String orderedDate;
    private String itemName;
    private String itemPrice;
    private String Suppliers_Name;
    private String Suppliers_Contact;

    private String Quantity;

    public OrderHistoryMenuItem() {
        // Default constructor required for calls to DataSnapshot.getValue(OrderHistoryMenuItem.class)
    }



    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setSuppliers_Name(String suppliers_Name) {
        this.Suppliers_Name = suppliers_Name;
    }

    public void setSuppliers_Contact(String suppliers_Contact) {
        this.Suppliers_Contact = suppliers_Contact;
    }

    public OrderHistoryMenuItem(String orderedDate, String itemName, String itemPrice,
                                String supplierName, String contact,String quantity) {
        this.orderedDate = orderedDate;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.Suppliers_Name = supplierName;
        this.Suppliers_Contact = contact;
        this.Quantity= quantity;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getSuppliers_Name() {
        return Suppliers_Name;
    }

    public String getSuppliers_Contact() {
        return Suppliers_Contact;
    }

    public String getQuantity() {
        return Quantity;
    }


}
