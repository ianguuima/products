package me.ianguuima.criteria;

import lombok.AllArgsConstructor;
import me.ianguuima.product.models.product.Product;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MinPriceCriteria implements ProductCriteria {

    private final double minPrice;

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        if (minPrice == 0) return products;
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice).collect(Collectors.toList());
    }

}
