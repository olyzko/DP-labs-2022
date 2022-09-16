import java.security.SecureRandom;
import java.util.concurrent.SynchronousQueue;

public class B {
    private static final SecureRandom rand = new SecureRandom();
    private static final Integer details = 25;

    public static void main(String[] args) throws InterruptedException {
        Threads pcc = new Threads();

        Thread ivanovThread = new Thread(() -> {
            try {
                pcc.ivanov(details);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread petrovThread = new Thread(() -> {
            try {
                pcc.petrov(details);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread nechipThread = new Thread(() -> {
            try {
                pcc.nechip(details);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ivanovThread.start();
        petrovThread.start();
        nechipThread.start();

        ivanovThread.join();
        petrovThread.join();
        nechipThread.join();
    }

    static class Threads {
        private final SynchronousQueue<Integer> IvanPetr;
        private final SynchronousQueue<Integer> PetrNechip;

        public Threads() {
            this.IvanPetr = new SynchronousQueue();
            this.PetrNechip = new SynchronousQueue();
        }

        public void ivanov(int numItems) throws InterruptedException {
            int current = 0;
            while (true) {
                System.out.println("Ivanov: " + current);
                this.IvanPetr.put(current++);
                if (current == numItems)
                    break;

                Thread.sleep(rand.nextInt(700) + 10);
            }
        }

        public void petrov(int numItems) throws InterruptedException {
            int current = 0;
            while (true) {
                int removed = this.IvanPetr.take();
                System.out.println("Petrov: " + current);
                this.PetrNechip.put(removed);
                current++;
                if (current == numItems)
                    break;

                Thread.sleep(rand.nextInt(700) + 10);
            }
        }

        public void nechip(int numItems) throws InterruptedException {
            while (true) {
                int removed = PetrNechip.take();
                numItems--;
                System.out.println("Nechiporuk: " + removed);
                if (numItems == 0)
                    break;

                Thread.sleep(rand.nextInt(500) + 10);
            }
        }
    }
}