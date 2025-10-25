package DataAccessObject;

import database.DatabaseConnection;
import java.sql.*;
import java.util.*;
import model.Customer;

public class CustomerDAO{
    
    // Tạo tài khoản người dùng mới
    public Customer registerCustomer(String username, String passwordHash, String citizenID, String fullName, String dateOfBirth, String sex, String nationality, String placeOfOrigin, String placeOfResidence, String email, String phoneNumber) throws SQLException{
        if(findByRegister(username, citizenID, email, phoneNumber) != null)
            throw new SQLException("Username already exists.");
        String sql = "INSERT INTO customers(username, password_hash, citizen_id, full_name, date_of_birth, sex, nationality, place_of_origin, place_of_residence, email, phone, user_status) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, citizenID);
            ps.setString(4, fullName);
            ps.setString(5, dateOfBirth);
            ps.setString(6, sex);
            ps.setString(7, nationality);
            ps.setString(8, placeOfOrigin);
            ps.setString(9, placeOfResidence);
            ps.setString(10, email);
            ps.setString(11, phoneNumber);
            ps.setString(12, "ACTIVE");
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            Customer k = new Customer();
            if (rs.next()){
                k.setCustomerID(rs.getInt(1));
            }
            k.setUsername(username);
            k.setPasswordHash(passwordHash);
            k.setCitizenID(citizenID);
            k.setFullName(fullName);
            k.setDateOfBirth(dateOfBirth);
            k.setSex(sex);
            k.setNationality(nationality);
            k.setPlaceOfOrigin(placeOfOrigin);
            k.setPlaceOfResidence(placeOfResidence);
            k.setEmail(email);
            k.setPhone(phoneNumber);
            k.setUserStatus("ACTIVE");
            return k;
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    // Tìm tài khoản người dùng bằng tên đăng nhập, email, sđt, số CCCD
    public Customer findByRegister(String username, String citizenID, String email, String phone) throws SQLException{
        String sql = "SELECT * FROM customers WHERE username=? OR citizen_id=? OR email=? OR phone=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, citizenID);
            ps.setString(3, email);
            ps.setString(4, phone);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return extract(rs);
            }
            return null;
        }
    }
    
    // Tìm tài khoản người dùng bằng tên đăng nhập + mật khẩu (kiểm tra tên đăng nhập + mật khẩu khi đăng nhập tài khoản)
    public Customer findByLogin(String username, String passwordHash) throws SQLException{
        String sql = "SELECT * FROM customers WHERE username=? AND password_hash=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extract(rs);
            }
            return null;
        }
    }
    
    // Tìm tài khoản bằng tên ID (kiểm tra tên đăng nhập có tồn tại)
    public Customer findByID(int id) throws SQLException{
        String sql = "SELECT * FROM customers WHERE customer_id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return extract(rs);
            }
            return null;
        }
    }
    
    // Xem danh sách tất cả người dùng
    public List<Customer> getAllCustomers() throws SQLException{
        String sql = "SELECT * FROM customers ORDER BY customer_id DESC";
        List<Customer> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql)){
            while (rs.next()){
                list.add(extract(rs));
            }
        }
        return list;
    }

    // Cập nhật thông tin người dùng
    public void update(Customer k) throws SQLException{
        String sql = "UPDATE customers SET full_name=?, date_of_birth=?, place_of_residence=?, email=?, phone=? WHERE customer_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, k.getFullName());
            ps.setString(2, k.getDateOfBirth());
            ps.setString(3, k.getPlaceOfResidence());
            ps.setString(4, k.getEmail());
            ps.setString(5, k.getPhone());
            ps.setInt(6, k.getCustomerID());
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    // Cập nhật trạng thái người dùng
    public void updateStatus(int id, String status) throws SQLException{
        String sql = "UPDATE customers SET user_status=? WHERE customer_id=?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
    
    // Xoá tài khoản theo ID người dùng
    public void delete(int id) throws SQLException{
        String sql = "DELETE FROM customers WHERE customer_id=?";
        try(Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
    
    private Customer extract(ResultSet rs) throws SQLException{
        Customer k = new Customer();
        k.setCustomerID(rs.getInt("customer_id"));
        k.setUsername(rs.getString("username"));
        k.setPasswordHash(rs.getString("password_hash"));
        k.setCitizenID(rs.getString("citizen_id"));
        k.setFullName(rs.getString("full_name"));
        k.setDateOfBirth(rs.getString("date_of_birth"));
        k.setSex(rs.getString("sex"));
        k.setNationality(rs.getString("nationality"));
        k.setPlaceOfOrigin(rs.getString("place_of_origin"));
        k.setPlaceOfResidence(rs.getString("place_of_residence"));
        k.setEmail(rs.getString("email"));
        k.setPhone(rs.getString("phone"));
        k.setUserStatus(rs.getString("user_status"));
        return k;
    }

}
