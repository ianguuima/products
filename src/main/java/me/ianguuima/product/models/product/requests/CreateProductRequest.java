package me.ianguuima.product.models.product.requests;

import lombok.AllArgsConstructor;
import me.ianguuima.product.models.product.Product;

import javax.validation.constraints.Min;

@AllArgsConstructor
public class CreateProductRequest {

    private final String name;
    private final String description;

    @Min(value = 1, message = "Min value is 1")
    private final double price;

    public Product toProduct() {
        return new Product(name, description, price);
    }
}
