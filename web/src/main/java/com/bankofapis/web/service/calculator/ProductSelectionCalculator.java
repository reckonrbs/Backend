package com.bankofapis.web.service.calculator;

import com.bankofapis.core.model.accounts.OBReadProduct;

import java.util.Map;

public interface ProductSelectionCalculator {

    Map<String, OBReadProduct> execute();
}
