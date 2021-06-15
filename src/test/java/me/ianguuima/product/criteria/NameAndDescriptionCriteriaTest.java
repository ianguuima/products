package me.ianguuima.product.criteria;

import me.ianguuima.product.models.product.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static me.ianguuima.product.fixtures.ProductFixture.getRandomProduct;

class NameAndDescriptionCriteriaTest {

    @Test
    public void meetCriteria_shouldReturnAllProductsWithNameOrDescriptionDefined_whenProvideAListOfProducts() {
        var definedQuery = "Nokia";
        var nameAndDescriptionCriteria = new NameAndDescriptionCriteria(definedQuery);

        var products = List.of(
                getRandomProduct("Nokia", "A good cellphone"),
                getRandomProduct("Samsung", "Better than never"),
                getRandomProduct("Nokia S5", "A new strong generation"),
                getRandomProduct("Alternative Cellphone", "A cheaper alternative to Nokia")
        );

        var productsWithCriteriaApplied = nameAndDescriptionCriteria.meetCriteria(products);
        Assertions.assertThat(productsWithCriteriaApplied).hasSize(3);
    }

}