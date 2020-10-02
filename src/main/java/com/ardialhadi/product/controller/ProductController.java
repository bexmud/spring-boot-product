package com.ardialhadi.product.controller;

import com.ardialhadi.product.entity.Product;
import com.ardialhadi.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.createOrUpdate(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);

        if(!product.isPresent()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product.get());
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Product> update(@PathVariable long id, @Valid @RequestBody Product product) {
        Optional<Product> p = productService.findById(id);

        if(!p.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        p.get().setName(product.getName());

        return ResponseEntity.ok(productService.createOrUpdate(p.get()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        Optional<Product> p = productService.findById(id);
        if(!p.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
