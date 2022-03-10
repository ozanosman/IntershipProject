package Windows;

import Utillity.DBConnection;
import Utillity.MyModel;

import javax.swing.*;
import java.sql.*;
import java.time.LocalTime;

public class WorkerWindow extends JFrame
{
    private Connection conn = null;
    private PreparedStatement state = null;
    private ResultSet result;

    private JPanel workerPanel;

    private JLabel usernameLabel;
    private JLabel typeLabel;

    private JComboBox tasksComboBox;

    private JButton backButton;

    private JButton startWorkButton;
    private JButton breakButton;
    private JButton resetButton;
    private JButton endWorkButton;

    private JLabel hoursLabel;
    private JLabel minutesLabel;
    private JLabel secondsLabel;

    private JTextField commentTextField;

    private JTable tasksTable;
    private JScrollPane tableScrollPane;

    private boolean timerState = true;

    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    public WorkerWindow(String username, String type)
    {
        usernameLabel.setText(username);
        typeLabel.setText(type);

        refreshTasksTable();

        this.setContentPane(workerPanel);
        this.setTitle("Worker Window");
        this.setSize(1000, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        startWorkButton.addActionListener(e ->
        {
            timerState = true;

            Thread t = new Thread()
            {
                public void run()
                {
                    for (;;)
                    {
                        if (timerState)
                        {
                            try
                            {
                                sleep(1000);

                                if (seconds == 59)
                                {
                                    seconds = 0;
                                    minutes++;
                                }

                                if (minutes > 59)
                                {
                                    seconds = 0;
                                    minutes = 0;
                                    hours++;
                                }

                                seconds++;

                                secondsLabel.setText(seconds + "");
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
        });

        breakButton.addActionListener(e ->
        {
            timerState = false;

            startWorkButton.setText("Continue Work");
        });

        resetButton.addActionListener(e ->
        {
            timerState = false;

            seconds = 0;
            minutes = 0;
            hours = 0;

            startWorkButton.setText("Start Work");

            secondsLabel.setText("0");
            minutesLabel.setText("0:");
            hoursLabel.setText("0:");
        });

        endWorkButton.addActionListener(e ->
        {
            conn = DBConnection.getConnection();

            String sql = "insert into tasks (username, task, time, comment, date) values(?, ?, ?, ?, curdate())";

            try
            {
                state = conn.prepareStatement(sql);

                state.setString(1, usernameLabel.getText());
                state.setString(2, tasksComboBox.getSelectedItem().toString());
                state.setTime(3, Time.valueOf(LocalTime.of(hours, minutes, seconds)));
                state.setString(4, commentTextField.getText());

                state.execute();

                refreshTasksTable();
                commentTextField.setText("");
            }
            catch (SQLException c)
            {
                c.printStackTrace();
            }
        });

        backButton.addActionListener(e ->
        {
            if (type.equals("Worker"))
            {
                WorkerLogin window = new WorkerLogin();
            }
            else
            {
                AdministratorLogin window = new AdministratorLogin();
            }
            this.dispose();
        });
    }

    public void refreshTasksTable()
    {
        conn = DBConnection.getConnection();

        try
        {
            state = conn.prepareStatement("select * from tasks");
            result = state.executeQuery();
            tasksTable.setModel(new MyModel(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}