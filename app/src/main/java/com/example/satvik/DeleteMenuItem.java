package com.example.satvik;

public class DeleteMenuItem {
    private String imgURL;
    private String name;
    private String price;
    private String documentId;

    public DeleteMenuItem() {
        // Required for Firestore
    }

    public DeleteMenuItem(String imgURL, String name, String price) {
        this.imgURL = imgURL;
        this.name = name;
        this.price = price;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
