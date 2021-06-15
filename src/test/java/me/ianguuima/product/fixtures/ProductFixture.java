package me.ianguuima.product.fixtures;

import me.ianguuima.product.models.product.Product;

public class ProductFixture {

    public static Product getRandomProduct(int price) {
        return new Product("test", "test description", price);
    }

    public static Product getRandomProduct(String name, String description) {
        return new Product(name, description, 10.0);
    }

}
