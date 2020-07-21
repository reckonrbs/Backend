package com.bankofapis.web.service.recommendation;

public enum ProductType {

    LOAN("Loan"),
    HEALTH("Health"),
    INVESTMENT("Investment");

    private String name;

    ProductType (String name){
        this.name=name;
    }
     public String getProductName(){
        return this.name;
     }
}
