package com.javaFeatues.productAPI_Oauth.repository;

import com.javaFeatues.productAPI_Oauth.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
