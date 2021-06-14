package me.ianguuima.product.services;

import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(CreateProductRequest createProductRequest) {
        return productRepository.save(createProductRequest.toProduct());
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }
}
