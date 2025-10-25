package Service;

import DataAccessObject.*;
import database.DatabaseConnection;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;
import model.*;

public class TransactionService{

    private final AccountDAO a = new AccountDAO();
    private final TransactionDAO t = new TransactionDAO();

    // Nạp tiền
    public synchronized void deposit(String accountNumber, BigDecimal amount, String PIN, String cont) throws SQLException{
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than 0.");
        }
        try(Connection conn = DatabaseConnection.getConnection()){
            conn.setAutoCommit(false);
            try{
                Account acc = a.findByAccountNumber(accountNumber);
                if(acc == null){
                    throw new IllegalArgumentException("Account does not exist.");
                }
                if(acc.getAccountStatus().equals("LOCKED")){
                    throw new IllegalArgumentException("Account has been locked.");
                }
                if(!acc.getPinSmart().equals(PIN)){
                    throw new IllegalArgumentException("PIN code is incorrect.");
                }
                BigDecimal newBalance = acc.getBalance().add(amount);
                a.updateBalance_service(conn, acc.getAccountID(), newBalance);
                t.createTransaction_service(conn, null, acc.getAccountID(), amount, "DEPOSIT", cont);
                conn.commit();
            } catch(SQLException e){
                conn.rollback();
                throw e;
            }
        }
    }

    // Rút tiền
    public synchronized void withdraw(String accountNumber, BigDecimal amount, String PIN, String cont) throws SQLException{
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than 0.");
        }
        try(Connection conn = DatabaseConnection.getConnection()){
            conn.setAutoCommit(false);
            try{
                Account acc = a.findByAccountNumber(accountNumber);
                if(acc == null){
                    throw new IllegalArgumentException("Account does not exist.");
                }
                if(acc.getAccountStatus().equals("LOCKED")){
                    throw new IllegalArgumentException("Account has been locked.");
                }
                if(!acc.getPinSmart().equals(PIN)){
                    throw new IllegalArgumentException("PIN code is incorrect.");
                }
                if(acc.getBalance().compareTo(amount) < 0)
                    throw new IllegalArgumentException("Insufficident balance.");
                BigDecimal newBalance = acc.getBalance().subtract(amount);
                a.updateBalance_service(conn, acc.getAccountID(), newBalance);
                t.createTransaction_service(conn, acc.getAccountID(), null, amount, "WITHDRAW", cont);
                conn.commit();
            } catch(SQLException e){
                conn.rollback();
                throw e;
            }
        }
    }

    // Chuyển tiền
    public synchronized void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String PIN, String cont) throws SQLException{
        if(fromAccountNumber.equals(toAccountNumber)){
            throw new IllegalArgumentException("Invalid account.");
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Deposit amount must be greater than 0.");
        }
        try(Connection conn = DatabaseConnection.getConnection()){
            conn.setAutoCommit(false);
            try{
                Account from = a.findByAccountNumber(fromAccountNumber);
                Account to = a.findByAccountNumber(toAccountNumber);
                if(from == null || to == null){
                    throw new IllegalArgumentException("Account does not exist.");
                }
                if(from.getAccountStatus().equals("LOCKED") || to.getAccountStatus().equals("LOCKED")){
                    throw new IllegalArgumentException("Account has been locked.");
                }
                if(!from.getPinSmart().equals(PIN)){
                    throw new IllegalArgumentException("PIN code is incorrect.");
                }
                if(from.getBalance().compareTo(amount) < 0){
                    throw new IllegalArgumentException("Insufficient balance.");
                }
                BigDecimal newFromBalance = from.getBalance().subtract(amount);
                BigDecimal newToBalance = to.getBalance().add(amount);
                a.updateBalance_service(conn, from.getAccountID(), newFromBalance);
                a.updateBalance_service(conn, to.getAccountID(), newToBalance);
                t.createTransaction_service(conn, from.getAccountID(), to.getAccountID(), amount, "TRANSFER", cont);
                conn.commit();
            } catch(SQLException e){
                conn.rollback();
                throw e;
            }
        }
    }
    
    // Xem lịch sử giao dịch của một tài khoản
    public List<Transaction> viewTransactionHistory(int accountID) throws SQLException{
        return t.getTransactionsByAccount(accountID);
    }
    
    // Xác thực tài khoản và trạng thái trước khi giao dịch
    private void checkAccount(Account acc, String pin){
        if(acc == null){
            throw new IllegalArgumentException("Account does not exist.");
        }
        if("LOCKED".equalsIgnoreCase(acc.getAccountStatus())){
            throw new IllegalArgumentException("Account has been locked.");
        }
        if(!acc.getPinSmart().equals(pin)){
            throw new IllegalArgumentException("Incorrect PIN.");
        }
    }
}
