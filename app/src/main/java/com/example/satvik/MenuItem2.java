package com.example.satvik;

public class MenuItem2 {
    private String name;
    private String price;
    private String imageUrl;
    private String mobile;

    private String user_mobile;

    private String user_name;

    // Constructor
    public MenuItem2(String name, String price, String imageUrl, String mobile, String user_mobile, String user_name) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.mobile = mobile;
        this.user_mobile= user_mobile;
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
