package me.ianguuima.product.models.product;

import me.ianguuima.product.criteria.MaxPriceCriteria;
import me.ianguuima.product.criteria.MinPriceCriteria;
import me.ianguuima.product.criteria.NameAndDescriptionCriteria;
import me.ianguuima.product.criteria.ProductCriteria;

import java.util.ArrayList;
import java.util.List;

public class ProductQueryBuilder {

    private final List<ProductCriteria> criteria = new ArrayList<>();

    public ProductQueryBuilder minPrice(double minPrice) {
        criteria.add(new MinPriceCriteria(minPrice));
        return this;
    }

    public ProductQueryBuilder maxPrice(double maxPrice) {
        criteria.add(new MaxPriceCriteria(maxPrice));
        return this;
    }

    public ProductQueryBuilder nameAndDescription(String nameAndDescription) {
        criteria.add(new NameAndDescriptionCriteria(nameAndDescription));
        return this;
    }

    public List<ProductCriteria> getCriteria() {
        return criteria;
    }

}
