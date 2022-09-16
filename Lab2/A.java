import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class A {
    private final List<List<Boolean>> forest;
    private final AtomicBoolean flag;
    private final Integer forestSize;
    public final Integer threadsNumber;
    private final AtomicInteger nextSection;

    public A(Integer forestSize){
        this.forestSize = forestSize;
        this.threadsNumber = (int) Math.sqrt(forestSize);

        forest = new ArrayList(forestSize);
        for (int i = 0; i < forestSize; i++){
            List<Boolean> f = new ArrayList<>(forestSize);
            for (int j = 0; j < forestSize; j++)
                f.add(false);

            forest.add(f);
        }

        SecureRandom random = new SecureRandom();
        int section = random.nextInt(forestSize);
        int cell = random.nextInt(forestSize);
        System.out.println("Winnie is in section " + section + " cell " + cell);
        forest.get(section).set(cell, true);

        flag = new AtomicBoolean(false);
        nextSection = new AtomicInteger(0);
    }

    public static void main (String[] args){
        A program = new A(100);
        program.find();
    }

    private void find(){
        for(int i = 0; i < this.threadsNumber; i++) {
            Thread thread = new BeeThread();
            thread.start();
        }
    }

    private class BeeThread extends Thread {
        @Override
        public void run() {
            while (!flag.get()) {
                nextSection.set(nextSection.get() + 1);
                checkSection(nextSection.get() - 1);
            }
        }
    }

    private void checkSection(int sectionNumber){
        if(flag.get())
            return;

        System.out.println(Thread.currentThread().getName() + " searching in section " + sectionNumber);
        List<Boolean> section = forest.get(sectionNumber);
        if (section.contains(true)){
            System.out.println(Thread.currentThread().getName() + " found Winnie in section" + sectionNumber);
            flag.set(true);
        }
    }
}