package com.example.satvik;

public class NewOrder {
    private String itemName;
    private int quantity;
    private String userName;
    private String userContact;
    private String documentId;

    public NewOrder(String itemName, int quantity, String userName, String userContact, String documentId) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.userName = userName;
        this.userContact = userContact;
        this.documentId = documentId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public String getDocumentId() {
        return documentId;
    }
}
