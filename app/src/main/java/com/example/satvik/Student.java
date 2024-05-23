package com.example.satvik;


public class Student {
    private String name;
    private String mobileNo;

    // Required empty constructor for Firestore
    public Student() {}

    public Student(String name, String mobileNo) {
        this.name = name;
        this.mobileNo = mobileNo;
    }

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
