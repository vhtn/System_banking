package DataAccessObject;

import database.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import model.Transaction;

public class TransactionDAO{
    
    // Tạo giao dịch mới
    public Transaction createTransaction(Integer fromAccontID, Integer toAccountID, BigDecimal amount, String transactionType, String transactionContent) throws SQLException{
        String sql = "INSERT INTO transactions(from_account_id, to_account_id, amount, transaction_type, transaction_content, transaction_status) VALUES(?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setObject(1, fromAccontID);
            ps.setObject(2, toAccountID);
            ps.setBigDecimal(3, amount);
            ps.setString(4, transactionType);
            ps.setString(5, transactionContent);
            ps.setString(6, "SUCCESS");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            Transaction t = new Transaction();
            if(rs.next()){
                t.setTransactionID(rs.getInt(1));
            }
            t.setFromAccountID(fromAccontID);
            t.setToAccountID(toAccountID);
            t.setAmount(amount);
            t.setTransactionType(transactionType);
            t.setTransactionContent(transactionContent);
            t.setTransactionStatus("SUCCESS");
            return t;
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    public Transaction createTransaction_service(Connection conn, Integer fromAccountID, Integer toAccountID, BigDecimal amount, String transactionType, String transactionContent) throws SQLException{
        String sql = "INSERT INTO transactions(from_account_id, to_account_id, amount, transaction_type, transaction_content, transaction_status) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setObject(1, fromAccountID);
            ps.setObject(2, toAccountID);
            ps.setBigDecimal(3, amount);
            ps.setString(4, transactionType);
            ps.setString(5, transactionContent);
            ps.setString(6, "SUCCESS");
            ps.executeUpdate();
            Transaction t = new Transaction();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if(rs.next()){
                    t.setTransactionID(rs.getInt(1));
                }
            }
            t.setFromAccountID(fromAccountID);
            t.setToAccountID(toAccountID);
            t.setAmount(amount);
            t.setTransactionType(transactionType);
            t.setTransactionContent(transactionContent);
            t.setTransactionStatus("SUCCESS");
            return t;
        }
    }

    
    // Lịch sử giao dịch của tài khoản ngân hàng
    public List<Transaction> getTransactionsByAccount(int accID) throws SQLException{
        String sql = "SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id = ? ORDER BY created_at DESC";
        List<Transaction> list = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, accID);
            ps.setInt(2, accID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(extract(rs));
            }
        }
        return list;
    }
    private Transaction extract(ResultSet rs) throws SQLException{
        Transaction t = new Transaction();
        t.setTransactionID(rs.getInt("transaction_id"));
        t.setFromAccountID(rs.getObject("from_account_id") != null ? rs.getInt("from_account_id") : null);
        t.setToAccountID(rs.getObject("to_account_id") != null ? rs.getInt("to_account_id") : null);
        t.setAmount(rs.getBigDecimal("amount"));
        t.setTransactionType(rs.getString("transaction_type"));
        t.setTransactionContent(rs.getString("transaction_content"));
        t.setTransactionStatus(rs.getString("transaction_status"));
        Timestamp ts = rs.getTimestamp("created_at");
        if(ts != null){
            t.setCreatedAt(ts.toLocalDateTime());
        }
        return t;
    }
}
