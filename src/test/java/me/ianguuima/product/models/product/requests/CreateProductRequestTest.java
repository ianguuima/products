package me.ianguuima.product.models.product.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateProductRequestTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void priceField_minimumValue_shouldBe1() {
        var createRequest = new CreateProductRequest("Random Name", "Random Desc", 0.0);

        Set<ConstraintViolation<CreateProductRequest>> violations = validator.validate(createRequest);

        assertThat(violations)
                .extracting(ConstraintViolation::getMessageTemplate)
                .first()
                .isEqualTo("Minimum value for price is 1");
    }


}