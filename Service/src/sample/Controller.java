package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {

    private static String url = "jdbc:mysql://localhost:3306/sw_test";
    private static String user = "admin", pass="123";
    PreparedStatement insert;

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtname;

    @FXML
    private TextField txtsurname;

    @FXML
    private TextField txtemail;

    @FXML
    private DatePicker txtdate;

    @FXML
    private Button reserveButton;

    @FXML
    private TextField txttime;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;
    public void reserveButtonClicked() throws SQLException {
        String name = txtname.getText();
        String surname = txtsurname.getText();
        String email = txtemail.getText();
        java.util.Date date = java.util.Date.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String time = txttime.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pass);

            insert = con.prepareStatement("insert into service(name,surname,email,date,time) values(?,?,?,?,?)");
            insert.setString(1,name);
            insert.setString(2,surname);
            insert.setString(3,email);
            insert.setDate(4,sqlDate);
            insert.setString(5,time);

            insert.executeUpdate();
            System.out.println("Pridano");

        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void refreshButtonClicked(ActionEvent event) {
        System.out.println("HALOOLO");
        PropertyValueFactory id = new PropertyValueFactory<User, Integer>("id");
        tableColID.setCellValueFactory(id);
        PropertyValueFactory name = new PropertyValueFactory<User, String>("name");
        tableColName.setCellValueFactory(name);
        PropertyValueFactory surname = new PropertyValueFactory<User,String >("surname");
        tableColSurname.setCellValueFactory(surname);
        PropertyValueFactory email = new PropertyValueFactory<User,String >("email");
        tableColEmail.setCellValueFactory(email);
        PropertyValueFactory date = new PropertyValueFactory<User,Date >("date");
        tableColDate.setCellValueFactory(date);
        PropertyValueFactory time = new PropertyValueFactory<User,Time >("time");
        tableColTime.setCellValueFactory(time);
        listM = MysqlConnect.getDatauser();
        System.out.println("sadadsda");
        tableUsers.setItems(listM);
    }

    public void Delete(){
        con = MysqlConnect.ConnectDb();
        String sql = "delete from service where id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1,txtID.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"deleted");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

    }

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> tableColID;
    @FXML
    private TableColumn<User, String> tableColName;

    @FXML
    private TableColumn<User, String> tableColSurname;

    @FXML
    private TableColumn<User, String> tableColEmail;

    @FXML
    private TableColumn<User, Date> tableColDate;

    @FXML
    private TableColumn<User, Time> tableColTime;

    ObservableList<User> listM;
    int index = -1;
    Connection con= null;
    ResultSet rs = null;
    PreparedStatement ps = null;

    public void initialize(URL url, ResourceBundle rb){
        System.out.println("HALOOLO");
        PropertyValueFactory id = new PropertyValueFactory<User, Integer>("id");
        tableColID.setCellValueFactory(id);
        PropertyValueFactory name = new PropertyValueFactory<User, String>("name");
        tableColName.setCellValueFactory(name);
        PropertyValueFactory surname = new PropertyValueFactory<User,String >("surname");
        tableColSurname.setCellValueFactory(surname);
        PropertyValueFactory email = new PropertyValueFactory<User,String >("email");
        tableColEmail.setCellValueFactory(email);
        PropertyValueFactory date = new PropertyValueFactory<User,Date >("date");
        tableColDate.setCellValueFactory(date);
        PropertyValueFactory time = new PropertyValueFactory<User,Time >("time");
        tableColTime.setCellValueFactory(time);

        listM = MysqlConnect.getDatauser();
        System.out.println("sadadsda");
        tableUsers.setItems(listM);
    }

    public void GetSelected(MouseEvent mouseEvent) {
        index = tableUsers.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtID.setText(tableColID.getCellData(index).toString());
        txtname.setText(tableColName.getCellData(index).toString());
        txtsurname.setText(tableColSurname.getCellData(index).toString());
        txtemail.setText(tableColEmail.getCellData(index).toString());
        txttime.setText(tableColTime.getCellData(index).toString());
    }
    public void Edit(){
        try{
            con = MysqlConnect.ConnectDb();
            String valueID = txtID.getText();
            String value1 = txtname.getText();
            String value2 = txtsurname.getText();
            String value3 = txtemail.getText();
            java.util.Date date = java.util.Date.from(txtdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date value4 = new java.sql.Date(date.getTime());
            String value5 = txttime.getText();

            String sql = "update service set name = '"+value1+"',surname = '"+value2+"',email = '"+value3+"',date = '"+value4+"',time = '"+value5+"' where id = '"+valueID+"'";
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Updated");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }

    }
}
