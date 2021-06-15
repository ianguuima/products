package me.ianguuima.product.models.product.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateProductRequestTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void priceField_minimumValue_shouldBe1() {
        var updateRequest = new UpdateProductRequest("Random Name", "Random Desc", 0.0);

        Set<ConstraintViolation<UpdateProductRequest>> violations = validator.validate(updateRequest);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessageTemplate)
                .first()
                .isEqualTo("Minimum value for price is 1");
    }

}