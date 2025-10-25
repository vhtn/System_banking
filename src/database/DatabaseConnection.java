package database;
import java.sql.*;
public class DatabaseConnection{
    private static final String URL = "jdbc:mysql://localhost:3306/bankdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "SQL@thanh2308"; 

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args){
        try (Connection conn = getConnection()){
            System.out.println("Ket noi SQL thanh cong");
        } catch (SQLException e){
            System.out.println("Ket noi that bai");
            e.printStackTrace();
        }
    }
}
