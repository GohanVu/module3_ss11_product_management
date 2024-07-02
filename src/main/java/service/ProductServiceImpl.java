package service;

import model.Product;
import repository.IProductRepository;
import repository.impl.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService{
    private IProductRepository productRepository = new ProductRepository();

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productRepository.findAll());
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public void update(int id, Product product) {
        productRepository.update(id, product);
    }

    @Override
    public void remove(int id) {
        productRepository.remove(id);
    }

    @Override
    public List<Product> searchByName(String name) {
        return  productRepository.searchByName(name);
    }

    @Override
    public List<Product> searchByPrice(double min, double max) {
        return productRepository.searchByPrice(min,max);
    }
}
