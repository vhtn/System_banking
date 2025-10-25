package model;

import java.math.BigDecimal;

public final class Account{
    private int accountID;
    private int customerID;
    private String accountNumber;
    private BigDecimal balance;
    private String pinSmart;
    private String accountStatus;
    
    public Account(){}
    
    public Account(int accountID, int customerID, String accountNumber, BigDecimal balance, String pinSmart){
        this.accountID = accountID;
        this.customerID = customerID;
        this.accountNumber = accountNumber;
        setBalance(balance);
        this.pinSmart = pinSmart;
    }
    
    public int getAccountID(){ 
        return accountID; 
    }
    
    public void setAccountID(int accountID){ 
        this.accountID = accountID; 
    }
    
    public int getCustomerID(){ 
        return customerID; 
    }
    
    public void setCustomerID(int customerID){ 
        this.customerID = customerID; 
    }

    public String getAccountNumber(){ 
        return accountNumber; 
    }
    
    public void setAccountNumber(String accountNumber){ 
        this.accountNumber = accountNumber; 
    }

    public BigDecimal getBalance(){ 
        return balance; 
    }
    
    public void setBalance(BigDecimal balance){ 
        this.balance = balance; 
    }

    public String getPinSmart(){ 
        return pinSmart; 
    }
    
    public void setPinSmart(String pinSmart){ 
        this.pinSmart = pinSmart; 
    }
    
    public String getAccountStatus(){
        return accountStatus;
    }
    
    public void setAccountStatus(String accountStatus){
        this.accountStatus = accountStatus;
    }
    
    @Override
    public String toString(){
        return "Account{" + "accountID=" + accountID + ", customerID=" + customerID + ", accountNumber='" + accountNumber + '\'' + '\'' + ", balance=" + balance + ", pinSmart='[HIDDEN]'" + ", accountStatus='" + accountStatus + '}';
    }
}