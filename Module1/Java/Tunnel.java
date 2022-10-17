import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum TrainDirection {
    left, right
}

public class Tunnel {
    private final int MAX_TRAINS_IN_A_ROW = 3;
    private final Lock priorityLock = new ReentrantLock();
    private final Semaphore frontSemaphore = new Semaphore(MAX_TRAINS_IN_A_ROW, true);
    private final Semaphore backSemaphore = new Semaphore(MAX_TRAINS_IN_A_ROW, true);

    private TrainDirection priorityDirection = TrainDirection.right;
    private final AtomicInteger trainCounter = new AtomicInteger(0);
    private final int tunnelId;

    public Tunnel(int id) {
        this.tunnelId = id;
    }

    public void occupyTunnel(Train train) {
        try {
            acquire(train.getDirection());

            while (!canEnterTunnel(train)) {
                TimeUnit.MICROSECONDS.sleep(1);
            }

            System.out.println("Train " + train.getTrainId() + " entered tunnel " + this.getTunnelId() +
                    " on the " + train.getDirection());
            TimeUnit.SECONDS.sleep(3);

            if (trainCounter.get() == MAX_TRAINS_IN_A_ROW) {
                trainCounter.set(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseTunnel(Train train) {
        System.out.println("Train " + train.getTrainId() + " released tunnel " + this.getTunnelId() +
                " on the " + train.getDirection());
        release(train.getDirection());
    }

    private void acquire(TrainDirection direction) throws InterruptedException {
        if (direction == TrainDirection.left) {
            frontSemaphore.acquire();
        } else {
            backSemaphore.acquire();
        }
    }

    private void release(TrainDirection direction) {
        if (direction == TrainDirection.left) {
            frontSemaphore.release();
        } else {
            backSemaphore.release();
        }
    }

    private boolean isTunnelEmpty(TrainDirection direction) {
        return ((direction == TrainDirection.left ? backSemaphore : frontSemaphore).availablePermits() == MAX_TRAINS_IN_A_ROW);
    }

    private boolean isDirectionSame(Train train) {
        return (train.getDirection().equals(getPriorityDirection()));
    }

    private boolean canEnterTunnel(Train train) throws InterruptedException {
        boolean canEnter;
        try {
            priorityLock.lock();
            canEnter = (isTunnelEmpty(train.getDirection()) ||
                    (isDirectionSame(train) && (getTrainCounter().get() < MAX_TRAINS_IN_A_ROW)));
            if (canEnter) {
                if (trainCounter.getAndIncrement() == MAX_TRAINS_IN_A_ROW) {
                    priorityDirection = (priorityDirection.equals(TrainDirection.left)) ? TrainDirection.right : TrainDirection.left;
                } else {
                    priorityDirection = train.getDirection();
                }
            }
        } finally {
            priorityLock.unlock();
        }
        return canEnter;
    }

    public TrainDirection getPriorityDirection() {
        return priorityDirection;
    }

    public AtomicInteger getTrainCounter() {
        return trainCounter;
    }

    public int getTunnelId() {
        return tunnelId;
    }

    @Override
    public String toString() {
        return "Tunnel{" +
                "priorityDirection=" + priorityDirection +
                ", tunnelId=" + tunnelId +
                '}';
    }
}