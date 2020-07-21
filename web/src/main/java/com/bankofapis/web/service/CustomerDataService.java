package com.bankofapis.web.service;

import com.bankofapis.core.model.accounts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerDataService {
    @Autowired
    private AispService aispService;
    Map<OBReadAccountInformation, List<OBReadTransaction>> obReadAccountInformationListTransactionMap = new LinkedHashMap<>();
    Map<OBReadAccountInformation,List<OBReadProduct>> obReadAccountInformationListProductMap = new LinkedHashMap<>();
    OBReadDataResponse<OBReadAccountList> accountResponse;

    public Map<OBReadAccountInformation, List<OBReadProduct>> getObReadAccountInformationListProductMap() {
        return obReadAccountInformationListProductMap;
    }

    public Map<OBReadAccountInformation, List<OBReadTransaction>> getObReadAccountInformationListTransactionMap() {
        return obReadAccountInformationListTransactionMap;
    }
}