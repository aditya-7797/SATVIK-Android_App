package com.example.satvik;
public class OrderItem {
    private String itemName;
    private double itemPrice;
    private int quantity;
    private String userMobileNumber;
    private String documentId;
    private String supplierContact;  // Add this field

    public OrderItem(String itemName, double itemPrice, int quantity, String userMobileNumber, String documentId, String supplierContact) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.userMobileNumber = userMobileNumber;
        this.documentId = documentId;
        this.supplierContact = supplierContact;  // Initialize this field
    }

    // Getter and Setter methods

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getSupplierContact() {  // Add this getter method
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {  // Add this setter method
        this.supplierContact = supplierContact;
    }
}
