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
    }

    public Map<String, OBReadProduct> getProductsToOffer() {
        return productsToOffer;
    }
}
