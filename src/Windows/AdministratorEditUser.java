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

public class AdministratorEditUser extends JFrame {
    int id=-1;

    JButton backBtn = new JButton("Back");
    JButton editUser= new JButton("Edit");
    JTable table1 = new JTable();
    JScrollPane myScroll = new JScrollPane(table1);
    JPanel panel = new JPanel();
    JLabel usernameL = new JLabel("Username:");
    JLabel passwordL = new JLabel("Password:");
    JLabel listL = new JLabel("Users:");
    static JTextField usernameTF = new JTextField();
    static JTextField passwordTF = new JTextField();

    Connection conn= null;
    PreparedStatement state = null;
    ResultSet result;

    AdministratorEditUser(){
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        ButtonSetUp();
        panelSetUp();
        refreshTable();
        this.add(panel);
        table1.addMouseListener(new MouseAction());
    }

    public void ButtonSetUp() {
        backBtn.addActionListener(e -> {
            AdministratorLogin window = new AdministratorLogin();
            this.dispose();
        });
        editUser.addActionListener(new EditActionOrders());
    }

    public void refreshTable(){
        conn = DBConnection.getConnection();
        try {
            state = conn.prepareStatement("select * from ACCOUNTS");
            result = state.executeQuery();
            table1.setModel(new MyModel(result));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void panelSetUp(){
        panel.setLayout(new GridLayout(4,2));
        panel.add(listL);
        panel.add(myScroll);
        panel.add(usernameL);
        panel.add(usernameTF);
        panel.add(passwordL);
        panel.add(passwordTF);
        panel.add(backBtn);
        panel.add(editUser);
    }
    class MouseAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            int row=table1.getSelectedRow();
            id=Integer.parseInt(table1.getValueAt(row, 0).toString());
            if(e.getClickCount()>1) {
                usernameTF.setText(table1.getValueAt(row, 1).toString());
                passwordTF.setText(table1.getValueAt(row, 2).toString());
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

    class EditActionOrders implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            conn = DBConnection.getConnection();
            String sql = "update ACCOUNTS set username='"+usernameTF.getText()+"',password='"+passwordTF.getText()+"' where id=?";

            try {
                state = conn.prepareStatement(sql);
                state.setInt(1,id);
                state.execute();

                refreshTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}