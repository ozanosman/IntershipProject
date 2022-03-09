package Windows;

import Utillity.DBConnection;
import Utillity.MyModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministratorEditTask extends JFrame {

    JTable table = new JTable();
    JScrollPane myScroll = new JScrollPane(table);
    JPanel panel = new JPanel();
    JButton backBtn = new JButton("Back");
    JButton editBtn = new JButton("Edit");
    JButton delBtn = new JButton("Delete");
    JButton addBtn = new JButton("Add");
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
    int id = -1;

    Connection conn= null;
    PreparedStatement state = null;
    ResultSet result;

    AdministratorEditTask(){
        this.setTitle("Administrator Edit Window");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        panelSetUp();
        buttonSetUp();
        refreshTable();
        table.addMouseListener(new MouseAction());
    }
    public void buttonSetUp(){
        backBtn.addActionListener(e -> {
            AdministratorLogin window = new AdministratorLogin();
            this.dispose();
        });
        delBtn.addActionListener(new DeleteActionTasks());
        addBtn.addActionListener(new AddActionTasks());
        editBtn.addActionListener(new EditActionOrders());
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
        panel.setLayout(new GridLayout(8,2));
        panel.add(usernameL);
        panel.add(usernameTF);
        panel.add(taskL);
        panel.add(taskTF);
        panel.add(timeL);
        panel.add(timeTF);
        panel.add(commentL);
        panel.add(commentTF);
        panel.add(dateL);
        panel.add(dateTF);
        panel.add(taskListL);
        panel.add(myScroll);
        panel.add(addBtn);
        panel.add(delBtn);
        panel.add(editBtn);
        panel.add(backBtn);
        this.add(panel);
    }
    class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row=table.getSelectedRow();
            id=Integer.parseInt(table.getValueAt(row, 0).toString());
            if(e.getClickCount()>1) {
                usernameTF.setText(table.getValueAt(row, 1).toString());
                taskTF.setText(table.getValueAt(row, 2).toString());
                timeTF.setText(table.getValueAt(row, 3).toString());
                commentTF.setText(table.getValueAt(row, 4).toString());
                dateTF.setText(table.getValueAt(row, 5).toString());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    class DeleteActionTasks implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sqlDeleteString= "delete from tasks where comment=?";

            try {
                state = conn.prepareStatement(sqlDeleteString);
                state.setString(1,commentTF.getText());
                state.execute();

                refreshTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    class AddActionTasks implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "insert into tasks(username,task,time,comment,date) values (?,?,?,?,?)";

            try {
                PreparedStatement preparedStmt = conn.prepareStatement(sql);
                preparedStmt.setString(1, usernameTF.getText());
                preparedStmt.setString(2, taskTF.getText());
                preparedStmt.setString(3, timeTF.getText());
                preparedStmt.setString(4, commentTF.getText());
                preparedStmt.setString(5, dateTF.getText());
                preparedStmt.execute();

                refreshTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    class EditActionOrders implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "update tasks set username='"+usernameTF.getText()+"',task='"+taskTF.getText()+"',time='"+timeTF.getText()+"',comment='"+commentTF.getText()+"',date='"+dateTF.getText()+"' where id=?";

            try {
                state = conn.prepareStatement(sql);
                state.setInt(1, id);
                state.execute();

                refreshTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}