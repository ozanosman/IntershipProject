package Windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkerWindow extends JFrame
{
    private JPanel workerPanel;

    private JComboBox tasksComboBox;

    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private JButton endButton;

    private JLabel hoursLabel;
    private JLabel minutesLabel;
    private JLabel secondsLabel;
    private JLabel millisecondsLabel;

    private boolean state = true;

    private int milliseconds = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    public WorkerWindow(String username)
    {
        this.add(tasksComboBox);
        this.setContentPane(workerPanel);
        this.setTitle("Worker Panel");
        this.setSize(600, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                state = true;

                Thread t = new Thread()
                {
                    public void run()
                    {
                        for (;;)
                        {
                            if (state == true)
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

        pauseButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                state = false;
            }
        });

        resetButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                state = false;

                milliseconds = 0;
                seconds = 0;
                minutes = 0;
                hours = 0;

                millisecondsLabel.setText("0");
                secondsLabel.setText("0:");
                minutesLabel.setText("0:");
                hoursLabel.setText("0:");
            }
        });
    }

}