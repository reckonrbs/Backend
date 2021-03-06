package com.bankofapis.web.service.calculator;

import com.bankofapis.core.model.accounts.OBReadAccountInformation;
import com.bankofapis.core.model.accounts.OBReadProduct;
import com.bankofapis.core.model.accounts.OBReadTransaction;
import com.bankofapis.web.service.CustomerDataService;
import com.bankofapis.web.service.recommendation.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Service
public class CalculatorEngineDelegator {

    @Autowired
    private CustomerDataService customerDataService;
    @Autowired
    private ProductSelectionCalculatorFactory productSelectionCalculatorFactory;
    Map<String, Map<String, OBReadProduct>> productsToOffer = new LinkedHashMap<>();
    private Map<String, List<Map<String, OBReadProduct>>> finalMap = new LinkedHashMap<>();
    private Map<String, String> matrixMap = new LinkedHashMap<>();

    public void executeFlow(CustomerDataService customerDataService) {
        matrixMap.put("Task","Product Transaction count");
        matrixMap.put("Health",String.valueOf(0));
        matrixMap.put("Investment",String.valueOf(0));
        matrixMap.put("Loan",String.valueOf(0));

        Map<OBReadAccountInformation, List<OBReadTransaction>> obReadAccountInformationListMap
                = customerDataService.getObReadAccountInformationListTransactionMap();
        for (Map.Entry<OBReadAccountInformation, List<OBReadTransaction>> obReadAccountInformationListEntry : obReadAccountInformationListMap.entrySet()) {
            OBReadAccountInformation obReadAccountInformation = obReadAccountInformationListEntry.getKey();
            List<OBReadTransaction> obReadTransactions = obReadAccountInformationListEntry.getValue();
            HealthProductCalculator healthProductCalculator = (HealthProductCalculator) productSelectionCalculatorFactory.getProductSelectionCalculator(ProductType.HEALTH.getProductName());
            LoanProductCalculator loanProductCalculator = (LoanProductCalculator) productSelectionCalculatorFactory.getProductSelectionCalculator(ProductType.LOAN.getProductName());
            InvestmentProductCalculator investmentProductCalculator = (InvestmentProductCalculator) productSelectionCalculatorFactory.getProductSelectionCalculator(ProductType.INVESTMENT.getProductName());
            healthProductCalculator.setAccountId(obReadAccountInformation.getAccountId());
            loanProductCalculator.setAccountId(obReadAccountInformation.getAccountId());
            investmentProductCalculator.setAccountId(obReadAccountInformation.getAccountId());
            //execute only for saving bank product further can be checked for current bank & credit card
            //size > 1 add
            Map<String, OBReadProduct> healthRec = healthProductCalculator.execute();
            Map<String, OBReadProduct> loanRecom = loanProductCalculator.execute();
            Map<String, OBReadProduct> investmentRec = investmentProductCalculator.execute();
            if(healthRec.size()>0) {
                productsToOffer.put("Health Products", healthRec);
            }
            if(loanRecom.size()>0) {
                productsToOffer.put("Loan Products", loanRecom);
            }
            if(investmentRec.size()>0) {
                productsToOffer.put("Investment Products", investmentRec);
            }
            matrixMap.put("Health",String.valueOf(Integer.valueOf(matrixMap.get("Health")) + healthProductCalculator.healthUsageTransaction));
            matrixMap.put("Investment",String.valueOf(Integer.valueOf(matrixMap.get("Investment")) + investmentProductCalculator.investmentUsageTransaction));
            matrixMap.put("Loan",String.valueOf(Integer.valueOf(matrixMap.get("Loan")) + loanProductCalculator.loanUsageTransaction));


//            if (productsToOffer.size() > 1) {
//                break;
//            }
        }
    }

    public Map<String, Map<String, OBReadProduct>> getProductsToOffer() {
        return productsToOffer;
    }

    public Map<String,String> getMatrixMap() {
        return matrixMap;
    }
}
