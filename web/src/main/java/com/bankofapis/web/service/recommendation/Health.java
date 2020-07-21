package com.bankofapis.web.service.recommendation;

import com.bankofapis.core.model.accounts.OBReadProduct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Health {

    OBReadProductROI covid19Product;
    OBReadProductROI heartTreatmentProduct;
    OBReadProductROI cancerTreatmentProduct;

    Map<String, OBReadProduct> oBReadHealthProductMap = new ConcurrentHashMap<>();

    public Health(){
        healthProductEnricher();
    }

    public void healthProductEnricher() {

        covid19Product = new OBReadProductROI();
        covid19Product.setProductId("Covid19#100");
        covid19Product.setProductName("covid19Product");
        covid19Product.setProductType("Health");
        covid19Product.setRoi(3.14);
        covid19Product.setAdditionalBenifits("additionalBenifits");

        heartTreatmentProduct = new OBReadProductROI();
        heartTreatmentProduct.setProductId("HeartTreatment#100");
        heartTreatmentProduct.setProductName("HeartTreatmentProductProduct");
        heartTreatmentProduct.setProductType("Health");
        heartTreatmentProduct.setRoi(4.5);
        heartTreatmentProduct.setAdditionalBenifits("additionalBenifits");

        cancerTreatmentProduct = new OBReadProductROI();
        cancerTreatmentProduct.setProductId("CancerTreatment#100");
        cancerTreatmentProduct.setProductName("CancerTreatmentProductProduct");
        cancerTreatmentProduct.setProductType("Health");
        cancerTreatmentProduct.setRoi(3.5);
        cancerTreatmentProduct.setAdditionalBenifits("additionalBenifits");

        oBReadHealthProductMap.put(covid19Product.getProductId(), covid19Product);
        oBReadHealthProductMap.put(heartTreatmentProduct.getProductId(), heartTreatmentProduct);
        oBReadHealthProductMap.put(cancerTreatmentProduct.getProductId(), cancerTreatmentProduct);

    }

    public Map<String, OBReadProduct> getoBReadHealthProductMap() {
        return oBReadHealthProductMap;
    }
}
