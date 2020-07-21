package com.bankofapis.web.service.recommendation;

import com.bankofapis.core.model.accounts.OBReadProduct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Loan {
    OBReadProductROI secondCarLoan;
    OBReadProductROI homeLoan;
    OBReadProductROI businessLoan;


    Map<String, OBReadProduct> oBReadLoanProductMap= new ConcurrentHashMap<>();

    public Loan(){
        loanProductEnricher();
    }

    public void loanProductEnricher() {
        secondCarLoan = new OBReadProductROI();
        secondCarLoan.setProductId("SecondCarLoan#100");
        secondCarLoan.setProductName("SecondCarLoanProduct");
        secondCarLoan.setProductType("Loan");
        secondCarLoan.setRoi(8.00);
        secondCarLoan.setAdditionalBenifits("additionalBenifits");

        homeLoan = new OBReadProductROI();
        homeLoan.setProductId("HomeLoan#101");
        homeLoan.setProductName("HomeLoanProduct");
        homeLoan.setProductType("Loan");
        homeLoan.setRoi(9.8);
        homeLoan.setAdditionalBenifits("additionalBenifits");

        businessLoan = new OBReadProductROI();
        businessLoan.setProductId("BusinessLoan#102");
        businessLoan.setProductName("BusinessLoanProduct");
        businessLoan.setProductType("Loan");
        businessLoan.setRoi(10.8);
        businessLoan.setAdditionalBenifits("additionalBenifits");

        oBReadLoanProductMap.put(secondCarLoan.getProductId(), secondCarLoan);
        oBReadLoanProductMap.put(homeLoan.getProductId(), homeLoan);
        oBReadLoanProductMap.put(businessLoan.getProductId(), businessLoan);

    }

    public Map<String, OBReadProduct> getOBReadLoanProductMap() {
        return oBReadLoanProductMap;
    }

    }
