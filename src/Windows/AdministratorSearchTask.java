package Windows;

import Utillity.DBConnection;
import Utillity.MyModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdministratorSearchTask extends JFrame {
    JTable table = new JTable();
    JScrollPane myScroll = new JScrollPane(table);
    JPanel panel = new JPanel();
    JButton backBtn = new JButton("Back");
    JButton searchBtn = new JButton("Search");
    JLabel taskL = new JLabel("Task list:");
    JLabel dateL = new JLabel("Date:");
    static JTextField dateTF = new JTextField();

    Connection conn= null;
    PreparedStatement state = null;
    ResultSet result;

    AdministratorSearchTask(){
        this.setTitle("Administrator Search Window");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        buttonSetUp();
        refreshTable();
        panelSetUp();
    }
    public void buttonSetUp(){
        backBtn.addActionListener(e -> {
            AdministratorLogin window = new AdministratorLogin();
            this.dispose();
        });
        searchBtn.addActionListener(e -> {
            conn = DBConnection.getConnection();
            try {
                state = conn.prepareStatement("SELECT ID,USERNAME,TASK,TIME,COMMENT,DATE FROM TASKS WHERE DATE = ?");
                state.setString(1,dateTF.getText());
                result = state.executeQuery();
                table.setModel(new MyModel(result));
            } catch (Exception b){
                b.printStackTrace();
            }
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
        panel.setLayout(new GridLayout(3,2));
        panel.add(dateL);
        panel.add(dateTF);
        panel.add(taskL);
        panel.add(myScroll);
        panel.add(backBtn);
        panel.add(searchBtn);
        this.add(panel);
    }
}