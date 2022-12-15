package RMI;

import Grocery.Category;
import Grocery.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Grocery extends Remote {
    public void addNewCategory(String name) throws RemoteException;
    public void deleteCategory(String name) throws RemoteException;
    public void addProductToCategory(String name, String productName, int price, int amount) throws RemoteException;
    public void deleteProduct(int productId) throws RemoteException;
    public void editProduct(int productId, String name, String productName, int price, int amount) throws RemoteException;
    public int countProductsInCategory(String name) throws RemoteException;
    public Product searchProductByName(String name) throws RemoteException;
    public ArrayList<Product> getAllProductsInCategory(String name) throws RemoteException;
    public ArrayList<Category> getAllCategories() throws RemoteException;

    public void stop() throws RemoteException;
}
