package DataAccessObject;

import database.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import model.Account;

public class AccountDAO{
    
    // Tạo tài khoản ngân hàng mới
    public Account createAccount(int customerID, String accountNumber, String pinSmart, BigDecimal balance) throws SQLException{
        String sql = "INSERT INTO accounts(customer_id, account_number, balance, pin_smart, account_status) VALUES(?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, customerID);
            ps.setString(2, accountNumber);
            ps.setBigDecimal(3, balance);
            ps.setString(4, pinSmart);
            ps.setString(5, "ACTIVE");
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                Account a = new Account();
                if(rs.next())
                    a.setAccountID(rs.getInt(1));
                a.setCustomerID(customerID);
                a.setAccountNumber(accountNumber);
                a.setBalance(balance);
                a.setPinSmart(pinSmart);
                a.setAccountStatus("ACTIVE");
                return a;
            }
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    // Tìm tài khoản ngân hàng bằng ID
    public Account findByID(int id) throws SQLException{
        String sql = "SELECT * FROM accounts WHERE account_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return extract(rs);
            }
            return null;
        }
    }
    
    // Tìm tài khoản ngân hàng bằng số tài khoản
    public Account findByAccountNumber(String num) throws SQLException{
        String sql = "SELECT * FROM accounts WHERE account_number=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, num);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return extract(rs);
            }
            return null;
        }
    }
    
    // Xem tất cả tài khoản ngân hàng của người dùng
    public List<Account> findByCustomer(int customerID) throws SQLException{
        String sql = "SELECT * FROM accounts WHERE customer_id=? ORDER BY account_id DESC";
        List<Account> list = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(extract(rs));
        }
        return list;
    }
    
    // Xem toàn bộ danh sách tài khoản
    public List<Account> findAccountAll() throws SQLException{
        String sql = "SELECT * FROM accounts ORDER BY account_id DESC";
        List<Account> list = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection();
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql)){
            while(rs.next()){
                list.add(extract(rs));
            }
        }
        return list;
    }
    // Cập nhật trạng thái tài khoản
    public void update(Account a) throws SQLException{
        String sql = "UPDATE accounts SET balance=?, pin_smart=?, account_status=? WHERE account_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setBigDecimal(1, a.getBalance());
            ps.setString(2, a.getPinSmart());
            ps.setString(3, a.getAccountStatus());
            ps.setInt(4, a.getAccountID());
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.err.println("Loi: " + e.getMessage());
            throw e;
        }
    }
    
    // Cập nhật số dư tài khoản ngân hàng
    public void updateBalance(int id, BigDecimal balance) throws SQLException{
        String sql = "UPDATE accounts SET balance=? WHERE account_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setBigDecimal(1, balance);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    
    public void updateBalance_service(Connection conn, int id, BigDecimal balance) throws SQLException{
        String sql = "UPDATE accounts SET balance=? WHERE account_id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setBigDecimal(1, balance);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    
    // Cập nhật trạng thái tài khoản ngân hàng
    public void updateStatus(int id, String status) throws SQLException{
        String sql = "UPDATE accounts SET account_status=? WHERE account_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    
    // Cập nhật mã PIN tài khoản ngân hàng
    public void updatePIN(int id, String pin) throws SQLException{
        String sql = "UPDATE accounts SET pin_smart=? WHERE account_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, pin);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    
    // Xoá tài khoản ngân hàng theo ID 
    public void delete(int id) throws SQLException{
        String sql = "DELETE FROM accounts WHERE account_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    private Account extract(ResultSet rs) throws SQLException{
        Account a = new Account();
        a.setAccountID(rs.getInt("account_id"));
        a.setCustomerID(rs.getInt("customer_id"));
        a.setAccountNumber(rs.getString("account_number"));
        a.setBalance(rs.getBigDecimal("balance"));
        a.setPinSmart(rs.getString("pin_smart"));
        a.setAccountStatus(rs.getString("account_status"));
        return a;
    }
}
