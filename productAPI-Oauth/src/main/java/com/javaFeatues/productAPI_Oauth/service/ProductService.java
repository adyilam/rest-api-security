package com.javaFeatues.productAPI_Oauth.service;

import com.javaFeatues.productAPI_Oauth.domain.Product;
import com.javaFeatues.productAPI_Oauth.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            product.setCategory(updatedProduct.getCategory());

            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product with given Id is not found!"));
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
