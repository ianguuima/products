package me.ianguuima.product.criteria;

import lombok.AllArgsConstructor;
import me.ianguuima.product.models.product.Product;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class MaxPriceCriteria implements ProductCriteria {

    private final double maxPrice;

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        if (maxPrice == 0) return products;
        return products.stream()
                .filter(product -> product.getPrice() <= maxPrice).collect(Collectors.toList());
    }

}
