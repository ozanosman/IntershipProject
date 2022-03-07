package Windows;

import Utillity.DBConnection;
import Utillity.Modal;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


/**
 * Administrator window class
 */
public class AdministratorLogin extends JFrame {
    Connection conn=null;
    Statement state=null;

    JPanel Panel = new JPanel();
    JLabel usernameLabel=new JLabel("Username");
    JLabel passwordLabel=new JLabel("Password");
    JTextField usernameTF=new JTextField();
    JTextField passwordTF=new JTextField();
    JButton loginBt=new JButton("Log in");
    JButton backBt=new JButton("Back");

    public AdministratorLogin(){
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        Panel.setLayout(new GridLayout(3,2));
        Panel.add(usernameLabel);
        Panel.add(usernameTF);
        Panel.add(passwordLabel);
        Panel.add(passwordTF);
        Panel.add(loginBt);
        Panel.add(backBt);
        this.add(Panel);
        this.setLocationRelativeTo(null);
        ButtonSetUp();
    }

    public void ButtonSetUp(){
        backBt.addActionListener(e -> {
            MainWindow window = new MainWindow();
            this.dispose();
        });

        loginBt.addActionListener(e -> {
            String sql="Select username,password,type from Accounts";
            conn= DBConnection.getConnection();
            try {
                boolean loggedIn = false;
                conn = DBConnection.getConnection();
                state = conn.createStatement();
                ResultSet rs = state.executeQuery(sql);
                while(rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("PASSWORD");
                    String type = rs.getString("type");
                    if (usernameTF.getText().equals(username)&&passwordTF.getText().equals(password)&&type.equals("Admin")) {
                        loggedIn = true;
                    }
                }
                if(loggedIn){
                    AdministratorMainWindow window = new AdministratorMainWindow(usernameTF.getText());
                    this.dispose();
                }else {
                    Modal.render(this,"Warning!","Invalid username or password");
                }

            } catch (SQLException b) {
                // TODO Auto-generated catch block
                b.printStackTrace();
            }
        });
    }
}