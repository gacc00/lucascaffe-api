package com.paolo.lucascaffe.controller;


import com.paolo.lucascaffe.model.Product;
import com.paolo.lucascaffe.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Conseguir y Postear nuestro productos")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Recibir todos los productos", description = "Recibes todos los productos")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Recibir por ID", description = "Recibes el producto solo por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Postear un producto", description = "Creas un producto")
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(product));
    }
    @Operation(summary = "Borrar", description = "Eliminas un producto por su ID ")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Buscar", description = "Buscas un producto por su categoria")
    @GetMapping("/search")
    public ResponseEntity<List<Product>> getByCategory(@RequestParam String category){
        return ResponseEntity.ok(productService.getByCategory(category));
    }
 }
