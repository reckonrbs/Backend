package com.bankofapis.web.service.calculator;

import com.bankofapis.core.model.accounts.OBReadAmount;
import com.bankofapis.core.model.accounts.OBReadProduct;
import com.bankofapis.core.model.accounts.OBReadTransaction;
import com.bankofapis.web.service.CustomerDataService;
import com.bankofapis.web.service.recommendation.Loan;
import com.bankofapis.web.service.recommendation.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
@Service
public class LoanProductCalculator implements ProductSelectionCalculator {

    @Autowired
    CustomerDataService customerDataService;

    private Map<String, OBReadProduct> savingPods= new HashMap();
    private List<OBReadProduct> recommentLoadPods;
    private  String accountId;
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public Map<String, OBReadProduct> execute() {
        System.out.println("this.accountId-----"+this.accountId);
        List<OBReadProduct> obReadProducts= getProductDetails(this.accountId);

        //select only loan products
        obReadProducts.forEach(obReadProduct -> {
            if(obReadProduct.getProductName().equals(ProductType.PersonalCurrentAccount.getProductName())){
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
        LocalDate lastYearDate= currentDate.minusMonths(36L);
        Boolean isRecomProduct= false;
        System.out.println("tx-----"+obReadTransactions);
        for(OBReadTransaction obReadTransaction: obReadTransactions){
            String dateTime= obReadTransaction.getBookingDateTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            LocalDate txnDate= LocalDate.parse(dateTime,dateTimeFormatter);
            if(currentDate.isAfter(txnDate) && lastYearDate.isBefore(txnDate)){
                //obReadTransaction.setProductId("123");
                OBReadProduct obReadProduct = obReadTransaction.getProductId() == null ? null
                        :savingPods.get(obReadTransaction.getProductId());
                OBReadAmount ObReadAmount= obReadTransaction.getAmount();

                //If amount is greater than 40000
                Double aDouble= new Double(ObReadAmount.getAmount());
                //dummy value
                //obReadTransaction.setTransactionInformation("Loan test");
                if(obReadProduct!=null && aDouble>2000 && obReadProduct.getProductId().equals(obReadTransaction.getProductId())){
                    String pattern ="Salary.*";
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
            final Loan loan= new Loan();
            return loan.getOBReadLoanProductMap();
        }
        return new HashMap<>();

    }

    private List<OBReadProduct> getProductDetails(String accountId){
        return customerDataService.fetchAccProductInfo(accountId);
    }
    private List<OBReadTransaction> getTxnDetails(String accountId){
        return customerDataService.fetchAccTxnInfo(accountId);

    }
}
