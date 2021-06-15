package me.ianguuima.product.criteria;

import lombok.AllArgsConstructor;
import me.ianguuima.product.models.product.Product;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class NameAndDescriptionCriteria implements ProductCriteria {

    private final String nameAndDescription;

    @Override
    public List<Product> meetCriteria(List<Product> products) {
        if (nameAndDescription.equalsIgnoreCase("")) return products;
        return products.stream()
                .filter(product ->
                        product.getName().contains(nameAndDescription)
                                ||
                        product.getDescription().contains(nameAndDescription)).collect(Collectors.toList()
                );
    }

}
