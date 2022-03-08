package Windows;

import javax.swing.*;
import java.awt.*;

/**
 * Main window from where you will select administrator/worker mode
 */
public class MainWindow extends JFrame {
    JPanel Panel=new JPanel();
    JButton adminBt=new JButton("Administrator");
    JButton workerBt =new JButton("Worker");
    public MainWindow(){
        this.setTitle("Main Window");
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

    /**
     * Contains the logic of the buttons
     */
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