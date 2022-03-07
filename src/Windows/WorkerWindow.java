package Windows;

import Utillity.DBConnection;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class WorkerWindow extends JFrame
{
    Connection conn = null;
    PreparedStatement state = null;

    private JPanel workerPanel;

    private JLabel usernameLabel;

    private JComboBox tasksComboBox;

    private JButton startWorkButton;
    private JButton breakButton;
    private JButton resetButton;
    private JButton endWorkButton;

    private JLabel hoursLabel;
    private JLabel minutesLabel;
    private JLabel secondsLabel;
    private JLabel millisecondsLabel;

    private JTextField commentTextField;

    private boolean timerState = true;

    private int milliseconds = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    public WorkerWindow(String username)
    {
        usernameLabel.setText(username);

        this.setContentPane(workerPanel);
        this.setTitle("Worker Panel");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startWorkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                timerState = true;

                Thread t = new Thread()
                {
                    public void run()
                    {
                        for (;;)
                        {
                            if (timerState == true)
                            {
                                try
                                {
                                    sleep(1);

                                    if (milliseconds > 1000)
                                    {
                                        milliseconds = 0;
                                        seconds++;
                                    }

                                    if (seconds > 60)
                                    {
                                        milliseconds = 0;
                                        seconds = 0;
                                        minutes++;
                                    }

                                    if (minutes > 60)
                                    {
                                        milliseconds = 0;
                                        seconds = 0;
                                        minutes = 0;
                                        hours++;
                                    }

                                    milliseconds++;

                                    millisecondsLabel.setText(milliseconds + "");
                                    secondsLabel.setText(seconds + ":");
                                    minutesLabel.setText(minutes + ":");
                                    hoursLabel.setText(hours + ":");
                                }
                                catch (Exception e)
                                {

                                }
                            }
                            else
                            {
                                break;
                            }
                        }
                    }
                };

                t.start();
            }
        });

        breakButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                timerState = false;

                startWorkButton.setText("Continue Work");
            }
        });

        resetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                timerState = false;

                milliseconds = 0;
                seconds = 0;
                minutes = 0;
                hours = 0;

                startWorkButton.setText("Start Work");

                millisecondsLabel.setText("0");
                secondsLabel.setText("0:");
                minutesLabel.setText("0:");
                hoursLabel.setText("0:");
            }
        });

        endWorkButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                conn = DBConnection.getConnection();

                String sql = "insert into tasks values(null, ?, ?, ?)";

                try
                {
                    state = conn.prepareStatement(sql);

                    state.setString(1, usernameLabel.getText());
                    state.setString(2, tasksComboBox.getSelectedItem().toString());
                    state.setString(3, commentTextField.getText());

                    state.execute();
                }
                catch (SQLException c)
                {
                    c.printStackTrace();
                }
            }
        });
    }
}