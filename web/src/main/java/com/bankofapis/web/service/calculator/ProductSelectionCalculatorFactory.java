package com.bankofapis.web.service.calculator;

import com.bankofapis.web.service.recommendation.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSelectionCalculatorFactory {
    @Autowired
    private HealthProductCalculator healthProductCalculator;
    @Autowired
    private LoanProductCalculator loanProductCalculator;
    @Autowired
    private InvestmentProductCalculator investmentProductCalculator;

    public ProductSelectionCalculator getProductSelectionCalculator(String productType){
        if(ProductType.HEALTH.getProductName().equals(productType)){
            return healthProductCalculator;
        }else if(ProductType.LOAN.getProductName().equals(productType)){
            return loanProductCalculator;
        }else if(ProductType.LOAN.getProductName().equals(productType)){
            return   investmentProductCalculator;
        }
        return  null;
    }
}
