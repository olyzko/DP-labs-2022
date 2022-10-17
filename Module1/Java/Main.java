import java.util.*;

public class Main {
    public static void main(String[] args) {
        Queue<Train> trains = addTrains();
        List<Tunnel> tunnels = addTunnels();

        Random random = new Random();
        for (Train train : trains) {
            Tunnel tunnel = tunnels.get(random.nextInt(tunnels.size()));
            train.setTunnel(tunnel);
            train.start();
        }
    }

    public static Queue<Train> addTrains() {
        Queue<Train> trains = new ArrayDeque<>();
        trains.add(new Train(1, TrainDirection.left));
        trains.add(new Train(2, TrainDirection.right));
        trains.add(new Train(3, TrainDirection.left));
        trains.add(new Train(4, TrainDirection.left));
        trains.add(new Train(5, TrainDirection.right));
        trains.add(new Train(6, TrainDirection.left));
        trains.add(new Train(7, TrainDirection.right));
        trains.add(new Train(8, TrainDirection.right));
        trains.add(new Train(9, TrainDirection.right));
        trains.add(new Train(10, TrainDirection.left));
        return trains;
    }

    public static List<Tunnel> addTunnels() {
        List<Tunnel> tunnels = new ArrayList<>();
        tunnels.add(new Tunnel(1));
        tunnels.add(new Tunnel(2));
        return tunnels;
    }


}


