package com.bankofapis.web.service;

import com.bankofapis.core.model.accounts.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerDataService {
    @Autowired
    private AispService aispService;
    Map<OBReadAccountInformation, List<OBReadTransaction>> obReadAccountInformationListTransactionMap;
    Map<OBReadAccountInformation,List<OBReadProduct>> obReadAccountInformationListProductMap;
    Map<String,String> accAmountInfo = new LinkedHashMap();
    OBReadDataResponse<OBReadAccountList> accountResponse;
    //Map<String,String> accBalanceInfo = new LinkedHashMap<>();

    public Map<OBReadAccountInformation, List<OBReadProduct>> getObReadAccountInformationListProductMap() {
        return obReadAccountInformationListProductMap;
    }

    public Map<OBReadAccountInformation, List<OBReadTransaction>> getObReadAccountInformationListTransactionMap() {
        return obReadAccountInformationListTransactionMap;
    }
    //get Accounts List
    public void fetchAccTxnInfo(){
        accountResponse = aispService.getAccountResponse();
        OBReadAccountList obReadAccountList  = accountResponse.getData();
        List<OBReadAccountInformation> obReadAccountListAccount = obReadAccountList.getAccount();
        obReadAccountInformationListTransactionMap = new LinkedHashMap<>();
        obReadAccountListAccount.forEach(obReadAccountInformation -> {
            OBReadDataResponse<OBReadTransactionList> obReadTransactionListOBReadDataResponse  = aispService.getTransactionsById(obReadAccountInformation.getAccountId());
            OBReadTransactionList obReadTransactionList= obReadTransactionListOBReadDataResponse.getData();
            obReadAccountInformationListTransactionMap.put(obReadAccountInformation,obReadTransactionList.getTransactionList());
        });
    }

    public void fetchAccProductInfo(){
        OBReadAccountList obReadAccountList;
        if(null != accountResponse){
             obReadAccountList  = accountResponse.getData();
        }else{
            accountResponse  = aispService.getAccountResponse();
        }
         obReadAccountList  = accountResponse.getData();
        List<OBReadAccountInformation> obReadAccountListAccount = obReadAccountList.getAccount();
        obReadAccountInformationListProductMap = new LinkedHashMap<>();
        obReadAccountListAccount.forEach(obReadAccountInformation -> {
            OBReadDataResponse<OBReadProductList> obReadProductListOBReadDataResponse  = aispService.getProductById(obReadAccountInformation.getAccountId());
            OBReadProductList obReadProductList = obReadProductListOBReadDataResponse.getData();
            obReadAccountInformationListProductMap.put(obReadAccountInformation,obReadProductList.getProductList());
        });
    }
    public List<OBReadProduct> fetchAccProductInfo(String accountId){
        OBReadDataResponse<OBReadProductList> obReadProductListOBReadDataResponse = aispService.getProductById(accountId);
        OBReadProductList obReadProductList=obReadProductListOBReadDataResponse.getData();
        return obReadProductList.getProductList();
    }
    public List<OBReadTransaction> fetchAccTxnInfo(String accountId){
        OBReadDataResponse<OBReadTransactionList> obReadTransactionListOBReadDataResponse= aispService.getTransactionsById(accountId);
        OBReadTransactionList obReadTransactionList = obReadTransactionListOBReadDataResponse.getData();
        return obReadTransactionList.getTransactionList();
    }

    public List<OBReadBalance> getBalance(String accountId){
        OBReadDataResponse<OBReadBalanceList> obReadBalanceListOBReadDataResponse =aispService.getBalanceById(accountId);
        OBReadBalanceList obReadBalanceList= obReadBalanceListOBReadDataResponse.getData();
        return obReadBalanceList.getAccount();
    }
    public Map<String,String> fetchAcBalance(){
        accountResponse = aispService.getAccountResponse();
        OBReadAccountList obReadAccountList  = accountResponse.getData();
        List<OBReadAccountInformation> obReadAccountListAccount = obReadAccountList.getAccount();
        obReadAccountListAccount.forEach(obReadAccountInformation -> {
            List<OBReadBalance> obReadBalances =getBalance(obReadAccountInformation.getAccountId());
            obReadBalances.forEach(obReadBalance -> {
               // String accAmount = obReadAccountInformation.getAccountId()+obReadBalance.getAmount().getAmount();
                accAmountInfo.put(obReadAccountInformation.getAccountId(),obReadBalance.getAmount().getAmount());
            });
        });

        return accAmountInfo;
    }
}
