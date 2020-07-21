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

    public void investmentProductEnricher() {

        rDDeposit = new OBReadProductROI();
        rDDeposit.setProductId("RDDepositProduct#100");
        rDDeposit.setProductName("RDDepositProduct");
        rDDeposit.setProductType("Investment");
        rDDeposit.setRoi(8.00);
        rDDeposit.setAdditionalBenifits("additionalBenifits");

        fDDeposit = new OBReadProductROI();
        fDDeposit.setProductId("FDDeposit#101");
        fDDeposit.setProductName("RDDepositProduct");
        fDDeposit.setProductType("Investment");
        fDDeposit.setRoi(7.00);
        fDDeposit.setAdditionalBenifits("additionalBenifits");

        retirementPlan = new OBReadProductROI();
        retirementPlan.setProductId("retirementPlanProduct#102");
        retirementPlan.setProductName("retirementPlanProduct");
        retirementPlan.setProductType("Investment");
        retirementPlan.setRoi(6.00);
        retirementPlan.setAdditionalBenifits("additionalBenifits");

        oBReadInvestmentProductMap.put(rDDeposit.getProductId(), rDDeposit);
        oBReadInvestmentProductMap.put(fDDeposit.getProductId(), fDDeposit);
        oBReadInvestmentProductMap.put(retirementPlan.getProductId(), retirementPlan);

    }

    public Map<String, OBReadProduct> getoBReadInvestmentProductMap() {
        return oBReadInvestmentProductMap;
    }
}
