package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws RemoteException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        GroceryImpl groceryImpl = new GroceryImpl();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("Grocery", groceryImpl);
        System.out.println("Server started!");
    }
}
