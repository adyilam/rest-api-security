package com.javaFeatues.productAPI_Oauth.web;

import com.javaFeatues.productAPI_Oauth.domain.Product;
import com.javaFeatues.productAPI_Oauth.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            Product product1 = new Product();
            product1.setName("SmartPhone");
            product1.setDescription("iphone 12 pro");
            product1.setPrice(BigDecimal.valueOf(12000.00));
            product1.setStock(10);
            product1.setCategory("Electronics");

            productRepository.save(product1);

            System.out.println("Product added to H2 database!");
        }
    }
}
