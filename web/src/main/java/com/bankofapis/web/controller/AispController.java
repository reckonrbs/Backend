package com.bankofapis.web.controller;

import com.bankofapis.core.model.accounts.*;
import com.bankofapis.web.service.AispService;
import com.bankofapis.web.service.CustomerDataService;
import com.bankofapis.web.service.calculator.CalculatorEngineDelegator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bankofapis.core.model.common.Constants.CONSENT_ID_HEADER;
import static com.bankofapis.remote.common.Endpoints.*;

@RestController
@RequestMapping("/open-banking/*/aisp")
public class AispController {

    private AispService aispService;

    @Autowired
    public AispController(AispService aispService) {
        this.aispService = aispService;
    }

    @Autowired
    private CustomerDataService customerDataService;
    @Autowired
    private CalculatorEngineDelegator calculatorEngineDelegator;

    @GetMapping(value = OB_JOURNEY_INIT)
    public String initialize(HttpServletResponse response) {
        return aispService.initialize();
    }

    @PostMapping(value = ACCOUNT_ACCESS_CONSENT_ENDPOINT)
    public OBReadDomesticConsentResponse aispConsentSetup(
        @RequestBody OBReadDomesticConsent obReadDataDomesticConsent) {
        return aispService.createAispConsent(obReadDataDomesticConsent);
    }

    @GetMapping(value = AUTHORIZATION_OAUTH2_ENDPOINT)
    public String aispConsentAuthUrl(HttpServletRequest request) {
        return aispService.createAuthorizeUri(request.getParameter(CONSENT_ID_HEADER));
    }

    @GetMapping(value = ACCOUNT_LIST_ENDPOINT)
    public OBReadDataResponse<OBReadAccountList> getAccounts() {
        return aispService.getAccountResponse();
    }

    @GetMapping(value = ACCOUNT_ID_ENDPOINT)
    public OBReadDataResponse<OBReadAccountList> getAccountById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getAccountById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_BALANCES_ENDPOINT)
    public OBReadDataResponse<OBReadBalanceList> getBalance(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getBalanceById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_TRANSACTIONS_ENDPOINT)
    public OBReadDataResponse<OBReadTransactionList> getTransactions(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getTransactionsById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_BENEFICIARIES_ENDPOINT)
    public OBReadDataResponse<OBReadBeneficiaryList> getBeneficiaries(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getBeneficiariesById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_DIRECT_DEBITS_ENDPOINT)
    public OBReadDataResponse<OBReadDirectDebitList> getDirectDebitsById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getDirectDebitsById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_STANDING_ORDERS_ENDPOINT)
    public OBReadDataResponse<OBReadStandingOrderList> getStandingOrderById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getStandingOrdersById(accountId);
    }

    @GetMapping(value = ACCOUNT_ID_PRODUCT_ENDPOINT)
    public OBReadDataResponse<OBReadProductList> getProductById(
        @PathVariable(value = "accountId") String accountId) {
        return aispService.getProductById(accountId);
    }

    @GetMapping(value = ACCOUNT_RECOMMENDATION_LIST_ENDPOINT)
    public OBReadDataResponse<String> getAccountRecommendationListById(
            @PathVariable(value = "accountId") String accountId) throws JsonProcessingException {

        customerDataService.fetchAccProductInfo();
        customerDataService.fetchAccTxnInfo();
        calculatorEngineDelegator.executeFlow(customerDataService);
        ObjectMapper  objectMapper  =  new ObjectMapper();
        String json= objectMapper.writeValueAsString(calculatorEngineDelegator.getProductsToOffer());
        return new OBReadDataResponse().data(json);
        //return aispService.getProductById(accountId);
    }

    @GetMapping(value = ACCOUNT_RECOMMENDATION_ENDPOINT)
    public OBReadDataResponse<String> getAccountRecommendationById() throws JsonProcessingException {
        String json="{     \"AccountBalances\": {         \"55b16334-bf2c-4443-92e3-29df8182ac18\": 28.75,         \"6a37fad9-d1a1-4ca4-9bf3-7e4bf0c36c8d\": 343.3,         \"b69afadd-a033-4fb6-87ba-1385b922d8b4\": 358.4,         \"c60a3b00-63b5-469f-89ea-5154e26483c1\": 8754.34,         \"e4fed049-a220-4cbf-b91a-ce778127ea6f\": 4534     },     \"Matrix\": {         \"Task\": \"Hours per Day\",         \"Health\": 11,         \"Investment\": 2,         \"Bank\": 2     },     \"Recomendation\": {         \"Health Products\": {             \"Covid19#100\": {                 \"ProductName\": \"covid19Product\",                 \"ProductId\": \"Covid19#100\",                 \"ProductType\": \"Health\",                 \"roi\": 3.14,                 \"additionalBenifits\": \"additionalBenifits\"             },             \"CancerTreatment#100\": {                 \"ProductName\": \"CancerTreatmentProductProduct\",                 \"ProductId\": \"CancerTreatment#100\",                 \"ProductType\": \"Health\",                 \"roi\": 3.5,                 \"additionalBenifits\": \"additionalBenifits\"             },             \"HeartTreatment#100\": {                 \"ProductName\": \"HeartTreatmentProductProduct\",                 \"ProductId\": \"HeartTreatment#100\",                 \"ProductType\": \"Health\",                 \"roi\": 4.5,                 \"additionalBenifits\": \"additionalBenifits\"             }         },         \"Investment Products\": {             \"FDDeposit#101\": {                 \"ProductName\": \"RDDepositProduct\",                 \"ProductId\": \"FDDeposit#101\",                 \"ProductType\": \"Investment\",                 \"roi\": 7.0,                 \"additionalBenifits\": \"additionalBenifits\"             },             \"RDDepositProduct#100\": {                 \"ProductName\": \"RDDepositProduct\",                 \"ProductId\": \"RDDepositProduct#100\",                 \"ProductType\": \"Investment\",                 \"roi\": 8.0,                 \"additionalBenifits\": \"additionalBenifits\"             },             \"retirementPlanProduct#102\": {                 \"ProductName\": \"retirementPlanProduct\",                 \"ProductId\": \"retirementPlanProduct#102\",                 \"ProductType\": \"Investment\",                 \"roi\": 6.0,                 \"additionalBenifits\": \"additionalBenifits\"             }         }     } }";
                  return new OBReadDataResponse().data(json);
    }
    }