package TaskB;

public class Main {
    public static void main(String[] args) {
        int numberOfVisitors = 10;

        Thread[] threads = new Thread[numberOfVisitors];
        Barber barber = new Barber();

        for (int i = 0; i < numberOfVisitors; i++) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threads[i] = new Thread(new Visitor(barber), "Visitor " + i);
            threads[i].start();
        }

        for (int i =0; i<numberOfVisitors; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}