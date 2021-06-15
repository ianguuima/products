package me.ianguuima.product.controllers;

import me.ianguuima.product.criteria.MaxPriceCriteria;
import me.ianguuima.product.criteria.MinPriceCriteria;
import me.ianguuima.product.criteria.NameAndDescriptionCriteria;
import me.ianguuima.product.criteria.ProductCriteria;
import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.models.product.requests.UpdateProductRequest;
import me.ianguuima.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        var product = productService.save(createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        var product = productService.update(id, updateProductRequest);
        return ResponseEntity.of(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        var product = productService.findById(id);
        return ResponseEntity.of(product);
    }

    @GetMapping
    public ResponseEntity<Collection<Product>> getAllProducts() {
        var products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<Product>> searchProducts(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "0") Long minPrice,
            @RequestParam(required = false, defaultValue = "0") Long maxPrice
    ) {
        List<ProductCriteria> criteria = List.of(
                new MaxPriceCriteria(maxPrice),
                new MinPriceCriteria(minPrice),
                new NameAndDescriptionCriteria(q)
        );

        var searchResult = productService.search(criteria);
        return ResponseEntity.ok(searchResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        var deletionResult = productService.delete(id);
        if (deletionResult) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}
