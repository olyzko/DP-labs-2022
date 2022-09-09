package A;

import javax.swing.*;

public class Main {
    private static Thread thread1;
    private static Thread thread2;
    private static JButton startButton = new JButton("Start");

    private static void startClicked() {
        thread1.start();
        thread2.start();
        startButton.setEnabled(false);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lab 1 Task A");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(null);

        JSlider slider = new JSlider();
        frame.add(slider);
        slider.setBounds(50, 65, 300, 50);
        slider.setMajorTickSpacing(10);
        slider.setPaintLabels(true);

        frame.add(startButton);
        startButton.setBounds(100, 250, 200, 50);
        startButton.addActionListener(e -> startClicked());

        SpinnerModel firstModel = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner firstSpinner = new JSpinner(firstModel);
        frame.add(firstSpinner);
        firstSpinner.setValue(1);
        firstSpinner.addChangeListener(e -> {
            int changedValue = (int) firstSpinner.getValue();
            thread1.setPriority(changedValue);
        });
        firstSpinner.setBounds(75, 165, 100, 40);

        SpinnerModel secondModel = new SpinnerNumberModel(1, 1, 10, 1);
        JSpinner secondSpinner = new JSpinner(secondModel);
        frame.add(secondSpinner);
        secondSpinner.addChangeListener(e -> {
            int changedValue = (int) secondSpinner.getValue();
            thread2.setPriority(changedValue);
        });
        secondSpinner.setBounds(200, 165, 100, 40);


        frame.setVisible(true);

        thread1 = new Thread(new MyRunnable(10, slider));
        thread2 = new Thread(new MyRunnable(90, slider));
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread1.setPriority(1);
        thread2.setPriority(1);
    }
}
