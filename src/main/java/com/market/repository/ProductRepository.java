package com.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.market.models.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

	// Método para encontrar um produto pelo código
    Product findByCode(String code);

    // Método para encontrar produtos pelo nome, ignorando maiúsculas/minúsculas
    List<Product> findByNameContainingIgnoreCase(String name);

    // Método para encontrar produtos pelo tipo
    List<Product> findByType(String type);

    // Método para encontrar produtos pelo preço menor que o especificado
    List<Product> findByPriceLessThan(float price);

    @Query("{'$or': [{'name': {$regex: ?0, $options: 'i'}}, {'code': ?1}]}")
    List<Product> findByNameOrCode(String name, String code);
    
}
