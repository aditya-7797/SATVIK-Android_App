package com.example.satvik;

public class Supplier {
    private String fullname;
    private String address;
    private String category;
    private String imageUrl;
    private String name;
    private String mobile; // New field for mobile number

    // Constructors, getters, and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Supplier() {
        // Empty constructor needed for Firestore
    }

    public Supplier(String fullname, String address, String category, String imageUrl, String mobile) {
        this.fullname = fullname;
        this.address = address;
        this.category = category;
        this.imageUrl = imageUrl;
        this.mobile = mobile; // Initialize mobile number
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
