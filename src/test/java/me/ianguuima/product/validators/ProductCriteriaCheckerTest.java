package me.ianguuima.product.validators;

import me.ianguuima.product.criteria.MinPriceCriteria;
import me.ianguuima.product.criteria.ProductCriteria;
import me.ianguuima.product.models.product.Product;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static me.ianguuima.product.fixtures.ProductFixture.getRandomProduct;
import static org.assertj.core.api.Assertions.assertThat;

class ProductCriteriaCheckerTest {

    @Test
    public void validate_shouldApplyCriteriaAndReturnAListOfProductsThatMeetsTheCriteria_whenProvideAListOfCriteria() {
        List<ProductCriteria> criteria = singletonList(new MinPriceCriteria(4));
        List<Product> products = List.of(
                getRandomProduct(5),
                getRandomProduct(3),
                getRandomProduct(2)
        );

        var filteredProducts = new ProductCriteriaChecker(criteria).validate(products);

        assertThat(filteredProducts).hasSize(1);
        assertThat(filteredProducts).extracting(Product::getPrice).first().isEqualTo(5.0);
    }


}