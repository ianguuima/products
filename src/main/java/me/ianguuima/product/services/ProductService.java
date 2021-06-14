package me.ianguuima.product.services;

import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.models.product.requests.UpdateProductRequest;
import me.ianguuima.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Collection<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> update(Long id, UpdateProductRequest updateProductRequest) {
        return findById(id)
                .map(product -> productRepository.save(updateProductRequest.toProduct(id)));
    }
}
