package B;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static Thread firstThread;
    private static Thread secondThread;
    private static final JSlider slider = new JSlider();
    public static AtomicInteger semaphore = new AtomicInteger(0);

    private static final JButton startFirstThreadButton = new JButton("Start 1 thread");
    private static final JButton startSecondThreadButton = new JButton("Start 2 thread");
    private static final JButton stopFirstThreadButton = new JButton("Stop 1 thread");
    private static final JButton stopSecondThreadButton = new JButton("Stop 2 thread");
    private static final JLabel runningThreadLabel = new JLabel("Another thread is already running");
    private static final JLabel noThreadLabel = new JLabel("There is no running thread");

    private static void startFirstThread(){
        if (semaphore.get() == 1){
            runningThreadLabel.setVisible(true);
            return;
        }

        firstThread = new Thread(new MyRunnable(10, slider));
        firstThread.setDaemon(true);
        firstThread.start();
        firstThread.setPriority(1);

        runningThreadLabel.setVisible(false);
        stopSecondThreadButton.setEnabled(false);
    }

    private static void startSecondThread(){
        if (semaphore.get() == 1){
            runningThreadLabel.setVisible(true);
            return;
        }

        secondThread = new Thread(new MyRunnable(90, slider));
        secondThread.setDaemon(true);
        secondThread.start();
        secondThread.setPriority(10);

        runningThreadLabel.setVisible(false);
        stopFirstThreadButton.setEnabled(false);
    }

    private static void stopFirstThread(){
        if (semaphore.get() == 0) {
            noThreadLabel.setVisible(true);
            return;
        }

        firstThread.interrupt();
        stopSecondThreadButton.setEnabled(true);
        runningThreadLabel.setVisible(false);
    }

    private static void stopSecondThread(){
        if (semaphore.get() == 0) {
            noThreadLabel.setVisible(true);
            return;
        }

        secondThread.interrupt();
        stopFirstThreadButton.setEnabled(true);
        runningThreadLabel.setVisible(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 1 Task A");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        slider.setBounds(50,50,500,100);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);

        frame.add(slider);
        frame.add(startFirstThreadButton);
        frame.add(startSecondThreadButton);
        frame.add(stopFirstThreadButton);
        frame.add(stopSecondThreadButton);
        frame.add(runningThreadLabel);
        frame.add(noThreadLabel);

        startFirstThreadButton.setBounds(50,150,200,50);
        startFirstThreadButton.addActionListener(e -> startFirstThread());

        startSecondThreadButton.setBounds(350,150,200,50);
        startSecondThreadButton.addActionListener(e -> startSecondThread());

        stopFirstThreadButton.setBounds(50,210,200,50);
        stopFirstThreadButton.addActionListener(e -> stopFirstThread());

        stopSecondThreadButton.setBounds(350,210,200,50);
        stopSecondThreadButton.addActionListener(e -> stopSecondThread());

        runningThreadLabel.setBounds(200, 260, 300, 50);
        runningThreadLabel.setVisible(false);

        noThreadLabel.setBounds(215, 260, 300, 50);
        noThreadLabel.setVisible(false);


        frame.setSize(600,400);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}