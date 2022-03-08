package Windows;

import javax.swing.*;
import java.awt.*;

public class AdministratorMainWindow extends JFrame {
    String username;
    JPanel Panel = new JPanel();
    JButton workBt=new JButton("Work");
    JButton backBt =new JButton("Back");
    JButton editTaskBt=new JButton("Edit tasks");
    JButton editUserBt =new JButton("Edit users");
    JButton searchBt=new JButton("Search task");
    AdministratorMainWindow(String name){
        this.setTitle("Administrator Main Window");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        PanelSetUp();
        ButtonSetUp();
        this.username = name;
        this.add(Panel);
    }

    public void PanelSetUp(){
        Panel.setLayout(new GridLayout(3,2));
        Panel.add(workBt);
        Panel.add(editUserBt);
        Panel.add(editTaskBt);
        Panel.add(searchBt);
        Panel.add(backBt);
    }

    public void ButtonSetUp(){
        backBt.addActionListener(e -> {
            AdministratorLogin window = new AdministratorLogin();
            this.dispose();
        });
        workBt.addActionListener(e->{
            WorkerWindow window = new WorkerWindow(username, "Admin");
            this.dispose();
        });
        editUserBt.addActionListener(e->{
            AdministratorEditUser window = new AdministratorEditUser();
            this.dispose();
        });
    }
}