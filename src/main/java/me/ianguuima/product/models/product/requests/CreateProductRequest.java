package me.ianguuima.product.models.product.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ianguuima.product.models.product.Product;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Getter
public class CreateProductRequest {

    private final String name;
    private final String description;

    @Min(value = 1, message = "Minimum value for price is 1")
    private final double price;

    public Product toProduct() {
        return new Product(name, description, price);
    }
}
