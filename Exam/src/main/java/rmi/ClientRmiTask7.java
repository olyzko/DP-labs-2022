package rmi;

import util.Phone;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class ClientRmiTask7 {

    public static void logPhones(List<Phone> phones) {
        if (phones.isEmpty()) {
            System.out.println("None!");
            return;
        }

        for (var phone : phones) {
            System.out.println(phone);
        }
    }

    public static void main(String[] args) {
        int choice;
        int n;

        Scanner in = new Scanner(System.in);

        try {
            RMIServer rmiServer = (RMIServer) Naming.lookup("//localhost:1234/exam");

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

                choice = in.nextInt();

                if (choice == 0)
                    break;

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter 'n' value: ");
                        n = in.nextInt();

                        logPhones(rmiServer.displayMoreCityMinutesThanN(n));
                    }
                    case 2 -> logPhones(rmiServer.displayInterCity());
                    case 3 -> logPhones(rmiServer.displayInAlphabetic());
                }
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}