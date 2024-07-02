package repository.impl;

import model.Product;
import repository.IProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository implements IProductRepository {
    private static Map<Integer, Product> productList = new HashMap<>();
    static {
        productList.put(1,new Product(1,"Smartphone",23100000,"Samsung","Trendy"));
        productList.put(2,new Product(2,"Laptop",13005000,"Apple","Fashionable"));
        productList.put(3,new Product(3,"Mouse",53003000,"Xiaomi","Trendy"));
        productList.put(4,new Product(4,"Laptop",63004000,"Apple","User-friendly"));
        productList.put(5,new Product(5,"Mouse",293500000,"Samsung","Fashionable"));
        productList.put(6,new Product(6,"Smartphone",23600000,"Xiaomi","User-friendly"));
    }


    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productList.values());
    }

    @Override
    public void save(Product product) {
        productList.put(product.getId(),product);
    }

    @Override
    public Product findById(int id) {
       return  productList.get(id);
    }

    @Override
    public void update(int id, Product product) {
        productList.put(id,product);
    }

    @Override
    public void remove(int id) {
        productList.remove(id);
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> productsFound  = new ArrayList<>();
        for (Product product : productList.values()){
            if (product.getName().contains(name)){
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    @Override
    public List<Product> searchByPrice(double min, double max) {
        List<Product> productsFound = new ArrayList<>();
        for (Product product : productList.values()){
            if(product.getPrice() > min && product.getPrice() < max){
                productsFound.add(product);
            }
        }
         return productsFound;
    }
}
