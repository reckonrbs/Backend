package com.bankofapis.web.dvo;

import com.bankofapis.core.model.accounts.OBReadProduct;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDvo {

    @JsonProperty("Recomendation")
    private Map<String, Map<String, OBReadProduct>> recomendation;
    @JsonProperty("AccountBalances")
    private Map<String,Map<String,String>> accountBalances;
    @JsonProperty("Matrix")
    private Map<String,String> matrix;

    public Map<String, Map<String, OBReadProduct>> getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(Map<String, Map<String, OBReadProduct>> recomendation) {
        this.recomendation = recomendation;
    }

    public Map<String, Map<String, String>> getAccountBalances() {
        return accountBalances;
    }

    public void setAccountBalances(Map<String, Map<String, String>> accountBalances) {
        this.accountBalances = accountBalances;
    }

    public Map<String, String> getMatrix() {
        return matrix;
    }

    public void setMatrix(Map<String, String> matrix) {
        this.matrix = matrix;
    }



}
