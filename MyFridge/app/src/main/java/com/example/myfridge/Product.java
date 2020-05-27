package com.example.myfridge;

public class Product {

    private String name;
    private String expiryDate;

    public Product(String nm, String ed){
        name=nm;
        expiryDate=ed;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getName() {
        return name;
    }
}
