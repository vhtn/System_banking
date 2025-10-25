package Service;

import DataAccessObject.*;
import model.*;
import java.sql.*;
import java.util.List;

public class CustomerService{
    
    private final CustomerDAO c = new CustomerDAO();
    
    // Đăng kí tài khoản người dùng mới
    public Customer registerCustomer(String username, String passwordHash, String citizenID, String fullName, String dateOfBirth, String sex, String nationality, String placeOfOrigin, String placeOfResidence, String email, String phone) throws SQLException{
        if(c.findByRegister(username, citizenID, email, phone) != null){
            throw new IllegalArgumentException("Username, citizenID, email or phone already exists.");
        }
        return c.registerCustomer(username, passwordHash, citizenID, fullName, dateOfBirth, sex, nationality, placeOfOrigin, placeOfResidence, email, phone);
    }
    
    // Đăng nhập tài khoản người dùng
    public Customer login(String username, String password) throws SQLException{
        Customer cus = c.findByLogin(username, password);
        if(cus == null){
            throw new IllegalArgumentException("Username or password is incorrect.");
        }
        if("LOCKED".equalsIgnoreCase(cus.getUserStatus())){
            throw new IllegalArgumentException("Account is disabled.");
        }
        return cus;
    }
    
    // Cập nhật thông tin khách hàng
    public void updateInfor(Customer customer) throws SQLException{
        Customer cus = c.findByID(customer.getCustomerID());
        if(cus == null){
            throw new IllegalArgumentException("Account does not exist.");
        }
        c.update(customer);
    }
    
    // Cập nhật trạng thái tài khoản người dùng
    public void updateStatus(int id, String status) throws SQLException{
        Customer cus = c.findByID(id);
        if(cus == null){
            throw new IllegalArgumentException("Account does not exist.");
        }
        c.updateStatus(id, status);
    }
    
    // Xoá tài khoản người dùng
    public void delete(int id) throws SQLException{
        Customer cus = c.findByID(id);
        if(cus == null){
            throw new IllegalArgumentException("Account does not exist.");
        }
        c.delete(id);
    }
    
    // Xem danh sách tất cả người dùng
    public List<Customer> getAllCustomer() throws SQLException{
        return c.getAllCustomers();
    }
}
