package com.example.satvik;

public class item {

    String name;
    String address;
    String category;
    int profile_image;

    public item(String name, String address, String category, int profile_image) {
        this.name = name;
        this.address = address;
        this.category = category;
        this.profile_image = profile_image;


    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }
}
