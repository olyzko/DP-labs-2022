package Grocery;

import java.util.ArrayList;

public class Grocery {
    private final ArrayList<Category> productCategories= new ArrayList<Category>();

    //1
    public void addProductCategory(Category productCategory) {
        for (Category category : this.productCategories) {
            if (category.getCategoryId() == productCategory.getCategoryId()) {
                System.out.println("Product category already exists");
                return;
            }
        }
        this.productCategories.add(productCategory);
    }

    //2
    public void deleteProductCategory(int categoryId) {
        for (Category category : this.productCategories) {
            if (category.getCategoryId() == categoryId) {
                this.productCategories.remove(category);
                return;
            }
        }
    }

    //3
    public void addProduct(int categoryId, Product product) {
        for (Category category : this.productCategories) {
            if (category.getCategoryId() == categoryId) {
                for (Product p : category.getProducts()) {
                    if (p.getProductCode() == product.getProductCode()) {
                        System.out.println("Product already exists");
                        return;
                    }
                }
                category.addProduct(product);
                return;
            }
        }
    }

    //4
    public void deleteProduct(int categoryId, int productCode) {
        for (Category category : this.productCategories) {
            if (category.getCategoryId() == categoryId) {
                for (Product product : category.getProducts()) {
                    if (product.getProductCode() == productCode) {
                        category.deleteProduct(productCode);
                        return;
                    }
                }
            }
        }
    }

    //5
    public void updateProduct(int categoryId, int productCode, Product product) {
        for (Category category : this.productCategories) {
            if (category.getCategoryId() == categoryId) {
                for (Product p : category.getProducts()) {
                    if (p.getProductCode() == productCode) {
                        p.setProductName(product.getProductName());
                        p.setPrice(product.getPrice());
                        p.setAmount(product.getAmount());
                        return;
                    }
                }
            }
        }
    }

    //6
    public int countProductsInCategory(int categoryId) {
        for (Category category : this.productCategories) {
            if (category.getCategoryId() == categoryId) {
                return category.getProducts().size();
            }
        }
        return 0;
    }

    //7
    public Product searchProductByName(String productName) {
        for (Category category : this.productCategories) {
            for (Product product : category.getProducts()) {
                if (product.getProductName().equals(productName)) {
                    return product;
                }
            }
        }
        return null;
    }

    //8
    public Product[] getAllProductsInCategory(int categoryId) {
        for (Category category : this.productCategories) {
            if (category.getCategoryId() == categoryId) {
                return category.getProducts().toArray(new Product[0]);
            }
        }
        return null;
    }

    //9
    public Category[] getAllProductCategories() {
        return this.productCategories.toArray(new Category[0]);
    }

}
