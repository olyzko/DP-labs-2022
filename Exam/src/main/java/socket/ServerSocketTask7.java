package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Phone;

class Callback {

    public boolean shouldEnd = false;
}

class PhoneIterator implements Runnable {

    private final Socket socket;
    private final Callback callback;
    private final List<Phone> phones;

    public PhoneIterator(Socket socket, Callback callback, List<Phone> phones) {

        this.callback = callback;
        this.socket = socket;
        this.phones = phones;
    }

    @Override
    public void run() {
        try {
            InputStreamReader ois = new InputStreamReader(socket.getInputStream());

            System.out.println("Receiving input");

            BufferedReader reader = new BufferedReader(ois);
            String message = reader.readLine();
            System.out.println("Command " + message);
            String[] splitMessage = message.split("#");
            String commandIndex = splitMessage[0];

            System.out.println("Command " + splitMessage[0]);

            if (commandIndex.equals("0")) {
                System.out.println("Close command");
                callback.shouldEnd = true;
                return;
            }

            List<Phone> result = new ArrayList<>();

            switch (commandIndex) {
                case "1" -> {
                    int n = Integer.parseInt(splitMessage[1]);

                    for (Phone phone : phones) {
                        if (phone.getCityMinutes() > n) {
                            result.add(phone);
                        }
                    }
                }
                case "2" -> {
                    for (Phone phone : phones) {
                        if (phone.getInterMinutes() > 0) {
                            result.add(phone);
                        }
                    }
                }
                case "3" -> {
                    result = phones;
                    Collections.sort(result);
                }
            }

            System.out.println(phones);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            ois.close();
            oos.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class ServerSocketTask7 {

    private static final int PORT = 9876;

    public static void main(String[] args) throws IOException {
        var server = new ServerSocket(PORT);
        var callback = new Callback();

        var phones = Phone.getList();

        while (!callback.shouldEnd) {
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();

            PhoneIterator iterator = new PhoneIterator(socket, callback, phones);

            iterator.run();
        }

        System.out.println("Shutting down Socket server!!");
        server.close();
    }
}
