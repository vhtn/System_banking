package Service;

import DataAccessObject.*;
import java.math.BigDecimal;
import model.*;
import java.sql.*;
import java.util.List;

public class AccountService{
    
    private final AccountDAO a = new AccountDAO();
    
    // Tạo tài khoản ngân hàng
    public Account createAccount(int customerID, String accountNumber, String pinSmart, BigDecimal balance) throws SQLException{
        Account existing = a.findByAccountNumber(accountNumber);
        if(existing != null){
            throw new IllegalArgumentException("Account number already exists.");
        }
        return a.createAccount(customerID, accountNumber, pinSmart, balance);
    }
    
    // Xem thông tin tài khoản theo ID
    public Account getAccountByID(int id) throws SQLException{
        Account acc = a.findByID(id);
        if(acc == null){
            throw new IllegalArgumentException("Account nout found.");
        }
        return acc;
    }
    
    // Xem thông tin tài khoản theo số tài khoản
    public Account getAccountByNumber(String num) throws SQLException{
        Account acc = a.findByAccountNumber(num);
        if(acc == null){
            throw new IllegalArgumentException("Account nout found.");
        }
        return acc;
    }
    
    // Cập nhật thông tin tài khoản
    public void updateInfor(Account acctomer) throws SQLException{
        Account acc = a.findByID(acctomer.getAccountID());
        if(acc == null){
            throw new IllegalArgumentException("Account does not exist.");
        }
        a.update(acctomer);
    }
    
    // Cập nhật số dư
    public void updateBalance(int id, BigDecimal amount) throws SQLException{
        Account acc = a.findByID(id);
        if(acc == null){
            throw new IllegalArgumentException("Account not found.");
        }
        a.updateBalance(id, amount);
    }
    
    // Cập nhật mã PIN
    public void updatePIN(int id, String pin) throws SQLException{
        Account acc = a.findByID(id);
        if(acc == null){
            throw new IllegalArgumentException("Account not found.");
        }
        a.updatePIN(id, pin);
    }
    
    // Cập nhật trạng thái
    public void updateStatus(int id, String status) throws SQLException{
        Account acc = a.findByID(id);
        if(acc == null){
            throw new IllegalArgumentException("Account not found.");
        }
        a.updateStatus(id, status);
    }
    
    // Xoá tài khoản người dùng
    public void delete(int id) throws SQLException{
        Account acc = a.findByID(id);
        if(acc == null){
            throw new IllegalArgumentException("Account does not exist.");
        }
        a.delete(id);
    }
    
    // Xem thông tin tài khoản của 1 người dùng
    public List<Account> getAccountByCustomer(int id) throws SQLException{
        return a.findByCustomer(id);
    }
    
    // Xem thông tin tất cả các tài khoản
    public List<Account> getAccountsAll() throws SQLException{
        return a.findAccountAll();
    }
}

