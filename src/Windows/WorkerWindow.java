package Windows;

import javax.swing.*;
import java.awt.*;

/**
 * Administrator window class
 */
public class WorkerWindow extends JFrame {
    JPanel Panel = new JPanel();
    JLabel usernameLabel=new JLabel("Име:");
    JLabel passwordLabel=new JLabel("Фамилия:");
    JTextField usernameTF=new JTextField();
    JTextField passwordTF=new JTextField();
    JButton loginBt=new JButton("Log in");
    JButton backBt =new JButton("Назад");

    public WorkerWindow(){
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
        ButtonSetUp();
    }

    public void ButtonSetUp(){
        backBt.addActionListener(e -> {
            MainWindow window = new MainWindow();
            this.dispose();
        });
    }
}
