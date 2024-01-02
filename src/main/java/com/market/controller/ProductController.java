package com.market.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.market.models.Product;
import com.market.repository.ProductRepository;
import com.market.utils.GenericCode;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
	

    @GetMapping("/product")
    public List<Product> getAllProducts(@RequestParam(name = "productCodePrefix", defaultValue = "PRODUCT-") String productCodePrefix) {
        // Retorna todos os produtos do MongoDB
        return productRepository.findAll();
    }
	
	 @PostMapping("/product")
	    public ResponseEntity<String> createProduct(@RequestBody Product product) {
	        String productCode = GenericCode.generateCode("PRODUCT-");

	        product.setCode(productCode);
	        product.setRegisterDate(new Date());

	        // products.add(product);
	        productRepository.save(product);

	        return new ResponseEntity<>("Produto criado com sucesso! ", HttpStatus.CREATED);
	    }
	 
	 //atualiza pelo productCode
	 @PutMapping("/product/{productCode}")
	 public ResponseEntity<String> updateProduct(@PathVariable String productCode, @RequestBody Product updatedProduct) {
	     // Encontra o produto pelo código no MongoDB
	     Product existingProduct = productRepository.findByCode(productCode);

	     if (existingProduct != null) {
	         try {
	             existingProduct.setName(updatedProduct.getName());
	             existingProduct.setPrice(updatedProduct.getPrice());
	             existingProduct.setQuantity(updatedProduct.getQuantity());
	             existingProduct.setUrlImage(updatedProduct.getUrlImage());
	             existingProduct.setType(updatedProduct.getType());

	             // Atualiza no MongoDB
	             productRepository.save(existingProduct);

	             return new ResponseEntity<>("Produto atualizado com sucesso", HttpStatus.OK);
	         } catch (Exception e) {
	             return new ResponseEntity<>("Erro ao atualizar o produto: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	         }
	     } else {
	         return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
	     }
	 }

	 @GetMapping("/product/filter/{productCode}")
	 public ResponseEntity<List<Product>> getProductsByFilter(@PathVariable String productCode) {
	     // Verifica se é um código válido
	     if (productCode != null && !productCode.isEmpty()) {
	         Product foundProduct = productRepository.findByCode(productCode);

	         if (foundProduct != null) {
	             List<Product> filteredProducts = Collections.singletonList(foundProduct);
	             return new ResponseEntity<>(filteredProducts, HttpStatus.OK);
	         } else {
	             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	         }
	     } else {
	         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	 }

 
}
