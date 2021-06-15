package me.ianguuima.product.criteria;

import me.ianguuima.product.models.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.ianguuima.product.fixtures.ProductFixture.getRandomProduct;

class MinPriceCriteriaTest {

    @Test
    public void meetCriteria_shouldReturnAllProductsWithMaxPriceThanDefined_whenProvideAListOfProducts() {
        var definedMinPrice = 5;
        var minPriceCriteria = new MinPriceCriteria(definedMinPrice);
        var products = List.of(getRandomProduct(5), getRandomProduct(2), getRandomProduct(3));

        var productsWithCriteriaApplied = minPriceCriteria.meetCriteria(products);

        Assertions.assertThat(productsWithCriteriaApplied).hasSize(1);
        Assertions.assertThat(productsWithCriteriaApplied).extracting(Product::getPrice).first().isEqualTo(5.0);
    }
}