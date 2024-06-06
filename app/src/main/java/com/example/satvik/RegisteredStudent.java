package com.example.satvik;

public class RegisteredStudent {
    private String name;
    private String mobileNo;

    public RegisteredStudent(String name, String mobileNo) {
        this.name = name;
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public String getMobileNo() {
        return mobileNo;
    }
}
