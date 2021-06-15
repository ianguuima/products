package me.ianguuima.product.validators;

import me.ianguuima.product.criteria.ProductCriteria;
import me.ianguuima.product.models.product.Product;

import java.util.List;

public class ProductCriteriaChecker {

    private final List<ProductCriteria> criteria;

    public ProductCriteriaChecker(List<ProductCriteria> criteria) {
        this.criteria = criteria;
    }

    public List<Product> validate(List<Product> productList) {
        var products = productList;

        for (ProductCriteria criterion : criteria) {
            products = criterion.meetCriteria(products);
        }

        return products;
    }


}
