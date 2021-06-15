package me.ianguuima.product.criteria;

import me.ianguuima.product.models.product.Product;

import java.util.List;

public interface ProductCriteria {

    List<Product> meetCriteria(List<Product> products);

}
