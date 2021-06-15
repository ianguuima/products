package me.ianguuima.product.fixtures;

import me.ianguuima.product.models.product.Product;

public class ProductFixture {

    public static Product getRandomProduct(int price) {
        return new Product(1L, "test", "test description", price);
    }

    public static Product getRandomProduct(String name, String description) {
        return new Product(1L, name, description, 10.0);
    }

    public static Product getRandomProduct(long id) {
        return new Product(id,"test", "test description", 10.0);
    }

}
