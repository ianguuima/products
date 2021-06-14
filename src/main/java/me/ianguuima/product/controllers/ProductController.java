package me.ianguuima.product.controllers;

import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        var product = productService.findById(id);
        return ResponseEntity.of(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        var product = productService.save(createProductRequest);
        return ResponseEntity.ok(product);
    }

}
