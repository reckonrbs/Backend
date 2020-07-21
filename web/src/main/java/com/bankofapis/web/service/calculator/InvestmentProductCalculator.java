package com.bankofapis.web.service.calculator;

import com.bankofapis.core.model.accounts.OBReadAmount;
import com.bankofapis.core.model.accounts.OBReadProduct;
import com.bankofapis.core.model.accounts.OBReadTransaction;
import com.bankofapis.web.service.CustomerDataService;
import com.bankofapis.web.service.recommendation.Investment;
import com.bankofapis.web.service.recommendation.Loan;
import com.bankofapis.web.service.recommendation.ProductType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class InvestmentProductCalculator implements ProductSelectionCalculator {

    @Autowired
    CustomerDataService customerDataService;

    private Map<String, OBReadProduct> savingPods;
    private List<OBReadProduct> recommentLoadPods;
    private  String accountId;
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public Map<String, OBReadProduct> execute() {
        List<OBReadProduct> obReadProducts= getProductDetails(this.accountId);

        //select only loan products
        obReadProducts.forEach(obReadProduct -> {
            if(obReadProduct.getProductName().equals(ProductType.SAVING_BANK.getProductName())){
                savingPods.put(obReadProduct.getProductId(), obReadProduct);
            }
        });

        //Logic to select the product based  on txn
        List<OBReadTransaction> obReadTransactions= getTxnDetails(accountId);

        //Collect all the transaction of last month and check transaction amount is greater than 40000 and recommend load product
        //get transaction checkless than current day and fetch last month transaction
        //current date
        LocalDate currentDate = LocalDate.now();
        //Last year
        LocalDate lastYearDate= currentDate.minusMonths(12L);
        Boolean isRecomProduct= false;
        for(OBReadTransaction obReadTransaction: obReadTransactions){
            String dateTime= obReadTransaction.getBookingDateTime();
            DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("d/MM/yyyy");
            LocalDate txnDate= LocalDate.parse(dateTime,dateTimeFormatter);
            if(currentDate.isAfter(txnDate) && lastYearDate.isBefore(txnDate)){
                OBReadProduct obReadProduct = savingPods.get(obReadTransaction.getProductId());
                OBReadAmount ObReadAmount= obReadTransaction.getAmount();

                //If amount is greater than 50000
                Double aDouble= new Double(ObReadAmount.getAmount());
                if(aDouble> 50000 && obReadProduct.getProductId().equals(obReadTransaction.getProductId())){
                    String pattern ="Investment*";
                    String transactionInformation= obReadTransaction.getTransactionInformation();

                    Pattern regex= Pattern.compile(pattern);
                    if(regex.matcher(transactionInformation).matches()){
                        isRecomProduct= true;
                        break;
                    }
                }

            }
        }
        if(isRecomProduct){
            final Investment investment= new Investment();
            return investment.getoBReadInvestmentProductMap();
        }
        return null;

    }

    private List<OBReadProduct> getProductDetails(String accountId){
        return customerDataService.fetchAccProductInfo(accountId);
    }
     private List<OBReadTransaction> getTxnDetails(String accountId){
         return customerDataService.fetchAccTxnInfo(accountId);

     }
}
