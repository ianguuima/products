package me.ianguuima.product.services;

import me.ianguuima.product.criteria.ProductCriteria;
import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.models.product.requests.UpdateProductRequest;
import me.ianguuima.product.repositories.ProductRepository;
import me.ianguuima.product.validators.ProductCriteriaChecker;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> update(Long id, UpdateProductRequest updateProductRequest) {
        return findById(id)
                .map(product -> productRepository.save(updateProductRequest.toProduct(id)));
    }

    public Boolean delete(Long id) {
        return findById(id).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }

    public Collection<Product> search(List<ProductCriteria> criteria) {
        return new ProductCriteriaChecker(criteria).validate(findAll());
    }
}
