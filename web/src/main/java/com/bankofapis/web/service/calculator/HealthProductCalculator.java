package com.bankofapis.web.service.calculator;

import com.bankofapis.core.model.accounts.OBReadAmount;
import com.bankofapis.core.model.accounts.OBReadProduct;
import com.bankofapis.core.model.accounts.OBReadTransaction;
import com.bankofapis.web.service.CustomerDataService;
import com.bankofapis.web.service.recommendation.Health;
import com.bankofapis.web.service.recommendation.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
@Service
public class HealthProductCalculator implements ProductSelectionCalculator {
    @Autowired
    private CustomerDataService customerDataService;

    private Map<String,OBReadProduct> savingProds;
    private List<OBReadProduct> recomendHealthProds;

    private String accountId;

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    @Override
    public Map<String, OBReadProduct> execute() {
      List<OBReadProduct> obReadProducts =getProductDetails(this.accountId);
      obReadProducts.forEach(obReadProduct -> {
          if(obReadProduct.getProductName().equals(ProductType.SAVING_BANK.getProductName())){
              savingProds.put(obReadProduct.getProductId(),obReadProduct);
          }
      });
             List<OBReadTransaction> obReadTransactions=  getTxnDetails(this.accountId);
        LocalDate currentDate = LocalDate.now();
        LocalDate lastYearDate = currentDate.minusMonths(12L);
        boolean isRecomdProduct = false;
        for(OBReadTransaction obReadTransaction :obReadTransactions){
            String dateTime = obReadTransaction.getBookingDateTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDate txnDate = LocalDate.parse(dateTime,dateTimeFormatter);

            if(currentDate.isAfter(txnDate) && lastYearDate.isBefore(txnDate)){
                OBReadProduct obReadProduct = obReadTransaction.getProductId() == null ? null
                        :savingProds.get(obReadTransaction.getProductId());
                OBReadAmount obReadAmount = obReadTransaction.getAmount();
                Double aDouble = new Double(obReadAmount.getAmount());
                if(aDouble>10000 && obReadProduct.getProductId().equals(obReadTransaction.getProductId())){
                    String pattern = "Health*";
                     String transactionInformation = obReadTransaction.getTransactionInformation();
                    Pattern regex = Pattern.compile(pattern);
                    if(regex.matcher(transactionInformation).matches()){
                        isRecomdProduct = true;
                        break;
                    }
                }
            }
        }
        if(isRecomdProduct){
            Health  health = new Health();
            return health.getoBReadHealthProductMap();
        }
        return null;
    }

    private List<OBReadProduct> getProductDetails(String accountId){
        return customerDataService.fetchAccProductInfo(accountId);
    }
    private List<OBReadTransaction> getTxnDetails(String accountId){
        return  customerDataService.fetchAccTxnInfo(accountId);
    }
}
