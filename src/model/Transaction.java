package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class Transaction{
    private int transactionID;
    private Integer fromAccountID;
    private Integer toAccountID;
    private BigDecimal amount;
    private String transactionType;
    private String transactionContent;
    private String transactionStatus;
    private LocalDateTime createdAt;

    public Transaction(){}

    public Transaction(int transactionID, Integer fromAccountID, Integer toAccountID, BigDecimal amount, String transactionType, String transactionContent, String transactionStatus, LocalDateTime createdAt){
        this.transactionID = transactionID;
        this.fromAccountID = fromAccountID;
        this.toAccountID = toAccountID;
        setAmount(amount);
        setTransactionType(transactionType);
        this.transactionContent = transactionContent;
        setTransactionStatus(transactionStatus);
        this.createdAt = createdAt;
    }

    public int getTransactionID(){
        return transactionID; 
    }
    
    public void setTransactionID(int transactionID){ 
        this.transactionID = transactionID; 
    }

    public Integer getFromAccountID(){ 
        return fromAccountID; 
    }
    
    public void setFromAccountID(Integer fromAccountID){ 
        this.fromAccountID = fromAccountID; 
    }

    public Integer getToAccountID(){ 
        return toAccountID; 
    }
    
    public void setToAccountID(Integer toAccountID){ 
        this.toAccountID = toAccountID; 
    }

    public BigDecimal getAmount(){ 
        return amount; 
    }
    
    public void setAmount(BigDecimal amount){ 
        this.amount = amount; 
    }

    public String getTransactionType(){ 
        return transactionType; 
    }
    
    public void setTransactionType(String transactionType){ 
        this.transactionType = transactionType; 
    }

    public String getTransactionContent(){ 
        return transactionContent; 
    }
    
    public void setTransactionContent(String transactionContent){ 
        this.transactionContent = transactionContent; 
    }

    public String getTransactionStatus(){ 
        return transactionStatus; 
    }
    
    public void setTransactionStatus(String transactionStatus){ 
        this.transactionStatus = transactionStatus; 
    }

    public LocalDateTime getCreatedAt(){ 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt){ 
        this.createdAt = createdAt; 
    }
    
    @Override
    public String toString(){
        return String.format("[%s] %s | %.2f | %s | %s -> %s", createdAt, transactionType, amount, transactionContent, fromAccountID == null ? "-" : fromAccountID, toAccountID == null ? "-" : toAccountID);
    }
    
}
