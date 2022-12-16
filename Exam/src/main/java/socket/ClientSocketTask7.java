package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.Phone;

public class ClientSocketTask7 {

    private static final int PORT = 9876;

    public static void logPhones(List<Phone> phones) {
        if (phones.isEmpty()) {
            System.out.println("None!");
            return;
        }

        for (var phone : phones) {
            System.out.println(phone);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Socket socket;
        PrintWriter oos;
        ObjectInputStream ois;

        InetAddress host = InetAddress.getLocalHost();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println(
                    """
                    Choose option:
                    1 - display Phones in which number of city minutes is higher than 'n'
                    2 - display Phones which used inter city connection
                    3 - display Phones in alphabetic order
                    0 - exit
                    """
            );

            socket = new Socket(host.getHostName(), PORT);
            oos = new PrintWriter(socket.getOutputStream(), true);;

            System.out.println("Sending request to Socket Server");

            int commandIndex = scan.nextInt();

            if (commandIndex == 0) {
                System.out.println("Sending close Request");

                oos.println(commandIndex);
                oos.flush();

                break;
            }

            switch (commandIndex) {
                case 1 -> {
                    System.out.println("Enter 'n'");
                    int n = scan.nextInt();

                    String message = "" + commandIndex + "#" + n;

                    oos.println(message);
                    System.out.println(message);
                    oos.flush();
                }

                case 2, 3 -> {
                    String message = "" + commandIndex;

                    oos.println(message);
                    oos.flush();
                }
            }

            System.out.println("Results: ");
            ois = new ObjectInputStream(socket.getInputStream());

            logPhones((ArrayList<Phone>) ois.readObject());

            ois.close();
            oos.close();

            Thread.sleep(100);
        }

        oos.println(3);

        System.out.println("bb :)");
    }
}