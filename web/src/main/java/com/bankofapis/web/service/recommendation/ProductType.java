package com.bankofapis.web.service.recommendation;

public enum ProductType {

    LOAN("Loan"),
    HEALTH("Health"),
    INVESTMENT("Investment"),
    SAVING_BANK("Saving Bank"),
    CURRENT_BANK("Current BANK"),
    CREDIT_CARD("Credit Card");

    private String name;

    ProductType (String name){
        this.name=name;
    }
     public String getProductName(){
        return this.name;
     }
}
