package me.ianguuima.product.criteria;

import me.ianguuima.product.models.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.ianguuima.product.fixtures.ProductFixture.getRandomProduct;

class MaxPriceCriteriaTest {

    @Test
    public void meetCriteria_shouldReturnAllProductsWithLessPriceThanDefined_whenProvideAListOfProducts() {
        var definedMaxPrice = 10;
        var maxPriceCriteria = new MaxPriceCriteria(definedMaxPrice);
        var products = List.of(getRandomProduct(10), getRandomProduct(20));

        var productsWithCriteriaApplied = maxPriceCriteria.meetCriteria(products);

        Assertions.assertThat(productsWithCriteriaApplied).hasSize(1);
        Assertions.assertThat(productsWithCriteriaApplied).extracting(Product::getPrice).first().isEqualTo(10.0);
    }
}