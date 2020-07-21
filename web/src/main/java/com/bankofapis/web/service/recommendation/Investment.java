package com.bankofapis.web.service.recommendation;

import com.bankofapis.core.model.accounts.OBReadProduct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Investment {
    OBReadProductROI rDDeposit;
    OBReadProductROI fDDeposit;
    OBReadProductROI retirementPlan;
    
    Map<String, OBReadProduct> oBReadInvestmentProductMap= new ConcurrentHashMap<>();

    public Investment(){
        investmentProductEnricher();
    }

    public void investmentProductEnricher(){

        rDDeposit= new OBReadProductROI();
    }
}
