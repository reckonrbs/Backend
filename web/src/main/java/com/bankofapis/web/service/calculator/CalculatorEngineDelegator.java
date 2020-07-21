package com.bankofapis.web.service.calculator;

import com.bankofapis.core.model.accounts.OBReadAccountInformation;
import com.bankofapis.core.model.accounts.OBReadProduct;
import com.bankofapis.core.model.accounts.OBReadTransaction;
import com.bankofapis.web.service.CustomerDataService;
import com.bankofapis.web.service.recommendation.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Service
public class CalculatorEngineDelegator {

    @Autowired
    private CustomerDataService customerDataService;
    @Autowired
    private ProductSelectionCalculatorFactory productSelectionCalculatorFactory;
    Map<String , OBReadProduct> productsToOffer = new LinkedHashMap<>();

    public void executeFlow(CustomerDataService customerDataService){
        Map<OBReadAccountInformation, List<OBReadTransaction>> obReadAccountInformationListMap
                =customerDataService.getObReadAccountInformationListTransactionMap();
        obReadAccountInformationListMap.forEach((obReadAccountInformation, obReadTransactions) -> {
            HealthProductCalculator healthProductCalculator = (HealthProductCalculator) productSelectionCalculatorFactory.getProductSelectionCalculator(ProductType.HEALTH.getProductName());
            LoanProductCalculator loanProductCalculator =(LoanProductCalculator)productSelectionCalculatorFactory.getProductSelectionCalculator(ProductType.LOAN.getProductName());
            InvestmentProductCalculator investmentProductCalculator= (InvestmentProductCalculator) productSelectionCalculatorFactory.getProductSelectionCalculator(ProductType.INVESTMENT.getProductName());
            healthProductCalculator.setAccountId(obReadAccountInformation.getAccountId());
            loanProductCalculator.setAccountId(obReadAccountInformation.getAccountId());
            investmentProductCalculator.setAccountId(obReadAccountInformation.getAccountId());
            //execute only for saving bank product further can be checked for current bank & credit card
            productsToOffer.putAll(healthProductCalculator.execute());
            //productsToOffer.putAll(loanProductCalculator.execute());
            //productsToOffer.putAll(investmentProductCalculator.execute());
        });
    }

    public Map<String, OBReadProduct> getProductsToOffer() {
        return productsToOffer;
    }
}
