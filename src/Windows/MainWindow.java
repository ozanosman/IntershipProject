package Windows;

import javax.swing.*;
import java.awt.*;

/**
 * Main window from where you will select administrator/worker mode
 */
public class MainWindow extends JFrame {
    JPanel Panel=new JPanel();
    JButton adminBt=new JButton("Администратор");
    JButton workerBt =new JButton("Работник");
    public MainWindow(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(400,400);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        Panel.setLayout(new GridLayout(1,2));
        Panel.add(adminBt);
        Panel.add(workerBt);
        this.add(Panel);
        ButtonSetUp();
    }

    public void ButtonSetUp(){
        workerBt.addActionListener(e -> {
            WorkerLogin window = new WorkerLogin();
            this.dispose();
        });

        adminBt.addActionListener(e -> {
            AdministratorLogin window = new AdministratorLogin();
            this.dispose();
        });
    }
}
