package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Phone;

interface RMIServer extends Remote {

    List<Phone> displayMoreCityMinutesThanN(int n) throws RemoteException;
    List<Phone> displayInterCity() throws RemoteException;
    List<Phone> displayInAlphabetic() throws RemoteException;
}

public class ServerRmiTask7 {

    static class Service extends UnicastRemoteObject implements RMIServer {

        List<Phone> phones;

        Service() throws RemoteException {
            super();
            phones = Phone.getList();
        }

        @Override
        public List<Phone> displayMoreCityMinutesThanN(int n) {
            List<Phone> result = new ArrayList<>();

            for (Phone phone : phones) {
                if (phone.getCityMinutes() > n) {
                    result.add(phone);
                }
            }

            return result;
        }

        @Override
        public List<Phone> displayInterCity() {
            List<Phone> result = new ArrayList<>();

            for (Phone phone : phones) {
                if (phone.getInterMinutes() > 0) {
                    result.add(phone);
                }
            }

            return result;
        }

        @Override
        public List<Phone> displayInAlphabetic() {
            List<Phone> result = phones;

            Collections.sort(result);

            return result;
        }
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(1234);
        RMIServer service = new Service();

        registry.rebind("exam", service);

        System.out.println("Server started!");
    }
}
