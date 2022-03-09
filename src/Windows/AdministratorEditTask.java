package Windows;

import Utillity.DBConnection;
import Utillity.MyModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdministratorEditTask extends JFrame {
    JTable table = new JTable();
    JScrollPane myScroll = new JScrollPane(table);
    JPanel panel = new JPanel();
    JButton backBtn = new JButton("Back");
    JButton editBtn = new JButton("Edit");
    JLabel usernameL = new JLabel("Username:");
    JLabel timeL = new JLabel("Time:");
    JLabel taskListL = new JLabel("Task List:");
    JLabel taskL = new JLabel("Task:");
    JLabel commentL = new JLabel("Comment:");
    JLabel dateL = new JLabel("Date:");
    static JTextField dateTF = new JTextField();
    static JTextField usernameTF = new JTextField();
    static JTextField timeTF = new JTextField();
    static JTextField taskTF = new JTextField();
    static JTextField commentTF = new JTextField();

    Connection conn= null;
    PreparedStatement state = null;
    ResultSet result;

    AdministratorEditTask(){
        this.setTitle("Administrator Edit Window");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        panelSetUp();
        buttonSetUp();
        refreshTable();
    }
    public void buttonSetUp(){
        backBtn.addActionListener(e -> {
            AdministratorLogin window = new AdministratorLogin();
            this.dispose();
        });
    }
    public void refreshTable(){
        conn = DBConnection.getConnection();
        try {
            state = conn.prepareStatement("select * from tasks");
            result = state.executeQuery();
            table.setModel(new MyModel(result));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void panelSetUp(){
        panel.setLayout(new GridLayout(7,2));
        panel.add(usernameL);
        panel.add(usernameTF);
        panel.add(taskL);
        panel.add(taskTF);
        panel.add(dateL);
        panel.add(dateTF);
        panel.add(timeL);
        panel.add(timeTF);
        panel.add(commentL);
        panel.add(commentTF);
        panel.add(taskListL);
        panel.add(myScroll);
        panel.add(backBtn);
        panel.add(editBtn);
        this.add(panel);
    }
}