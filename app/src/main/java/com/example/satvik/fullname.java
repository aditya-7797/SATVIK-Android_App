package com.example.satvik;

public class fullname {

    private String Username,Fullname,Address,Category,Profile_Img;

    public fullname() {
    }

    public fullname(String username, String fullname, String address, String category, String profile_Img) {
        Username = username;
        Fullname = fullname;
        Address = address;
        Category = category;
        Profile_Img = profile_Img;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getProfile_Img() {
        return Profile_Img;
    }

    public void setProfile_Img(String profile_Img) {
        Profile_Img = profile_Img;
    }
}
