package RMI;

import DAO.GroceryDAO;
import Grocery.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroceryImpl extends UnicastRemoteObject implements Grocery{
    private final GroceryDAO grocery;

    public GroceryImpl() throws RemoteException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        grocery = new GroceryDAO();
    }

    @Override
    public void addNewCategory(String name) throws RemoteException {
        synchronized (grocery) {
            grocery.addProductCategory(name);
        }

    }

    @Override
    public void deleteCategory(String categoryName) throws RemoteException {
        synchronized (grocery) {
            grocery.deleteProductCategory(categoryName);
        }
    }

    @Override
    public void addProductToCategory(String categoryName, String productName, int price, int amount) throws RemoteException {
        synchronized (grocery) {
            grocery.addProduct(categoryName, productName, price, amount);
        }
    }

    @Override
    public void deleteProduct(int productId) throws RemoteException {
        synchronized (grocery) {
            grocery.deleteProduct(productId);
        }
    }

    @Override
    public void editProduct(int productId, String categoryName, String productName, int price, int amount) throws RemoteException {
        synchronized (grocery) {
            grocery.updateProduct(productId, categoryName, productName, price, amount);
        }
    }

    @Override
    public int countProductsInCategory(String categoryName) throws RemoteException {
        synchronized (grocery) {
            return grocery.countProductsInCategory(categoryName);
        }
    }

    @Override
    public Product searchProductByName(String name) throws RemoteException {
        synchronized (grocery) {
            return grocery.searchProductByName(name);
        }
    }

    @Override
    public ArrayList<Product> getAllProductsInCategory(String categoryName) throws RemoteException {
        synchronized (grocery) {
            return grocery.getAllProductsInCategory(categoryName);
        }
    }

    @Override
    public ArrayList<Category> getAllCategories() throws RemoteException {
        synchronized (grocery) {
            return grocery.getAllCategories();
        }
    }

    @Override
    public void stop() throws RemoteException {
        System.exit(0);
    }
}
