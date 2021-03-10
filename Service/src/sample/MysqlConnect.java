package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MysqlConnect {
    private static String url = "jdbc:mysql://localhost:3306/sw_test";
    private static String user = "admin", pass="123";

    Connection conn = null;
    public static Connection ConnectDb(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/sw_test","admin","123");
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn;
        } catch (Exception e) {
            System.out.println("Nevyšlo to");
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    public static ObservableList<User> getDatauser(){
        Connection con = ConnectDb();
        ObservableList<User> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = con.prepareStatement("select * from service");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new User(rs.getInt("id"),rs.getString("name"),rs.getString("surname"),rs.getString("email"),rs.getDate("date"),rs.getTime("time")));
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

        return list;
    }
}
