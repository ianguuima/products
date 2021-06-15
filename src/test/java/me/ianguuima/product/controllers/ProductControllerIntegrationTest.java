package me.ianguuima.product.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ianguuima.product.criteria.MinPriceCriteria;
import me.ianguuima.product.criteria.NameAndDescriptionCriteria;
import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.models.product.requests.UpdateProductRequest;
import me.ianguuima.product.repositories.ProductRepository;
import me.ianguuima.product.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        productRepository.deleteAll();
    }

    @Test
    public void createProduct_shouldReturnCreatedProductWithId_whenProvideValidCreateProductRequest() throws Exception {
        var createProductRequest = mapper.writeValueAsString(
                new CreateProductRequest("Nokia", "Good phone", 10.0)
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id", any(Integer.class)))
                .andExpect(jsonPath("$.name", is("Nokia")))
                .andExpect(jsonPath("$.description", is("Good phone")))
                .andExpect(jsonPath("$.price", is(10.0)));
    }

    @Test
    public void createProduct_shouldReturnBadRequest_whenProvideNotValidCreateProductRequest() throws Exception {
        var createProductRequest = mapper.writeValueAsString(
                new CreateProductRequest("Nokia", "Good phone", 0.0)
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductRequest))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void updateProduct_shouldReturnUpdatedProduct_whenProvideValidProductIdAndUpdateProductRequest() throws Exception {
        var preExistentProductInDatabase = productService.save(
                new CreateProductRequest("Nokia", "Good phone", 10.0)
        );

        var updateProductRequest = mapper.writeValueAsString(new UpdateProductRequest(
                "New Nokia Phone",
                "A new good phone",
                15.0
        ));

        var expectedUpdatedProduct = mapper.writeValueAsString(new Product(
                preExistentProductInDatabase.getId(),
                "New Nokia Phone",
                "A new good phone",
                15.0
        ));

        mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", preExistentProductInDatabase.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateProductRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedUpdatedProduct));
    }

    @Test
    public void findById_shouldReturnProduct_whenProductExists() throws Exception {
        var createProductRequest = new CreateProductRequest("Nokia", "Good phone", 10.0);

        var savedProduct = productService.save(createProductRequest);
        var parsedProduct = mapper.writeValueAsString(savedProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", savedProduct.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(parsedProduct));
    }

    @Test
    public void findById_shouldReturnNotFound_whenProductDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findAll_shouldReturnAllProducts() throws Exception {
        var productsToCreateInDatabase = List.of(
                new CreateProductRequest("Random Product 1", "Test Desc", 10.0),
                new CreateProductRequest("Random Product 2", "Test Desc", 15.0)
        );

        productsToCreateInDatabase.forEach(product -> productService.save(product));

        var expectedProducts = mapper.writeValueAsString(productService.findAll());

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedProducts));
    }

    @Test
    public void searchProducts_shouldReturnFilteredProducts_whenProvideValidQuery() throws Exception {
        var productsToCreateInDatabase = List.of(
                new CreateProductRequest("Nokia 1", "Test Desc", 5.0),
                new CreateProductRequest("Nokia 2", "Test Desc", 10.0),
                new CreateProductRequest("Nokia 3", "Test Desc", 3.0),
                new CreateProductRequest("Samsung", "Test Desc", 15.0)
        );

        productsToCreateInDatabase.forEach(product -> productService.save(product));

        var expectedProducts = mapper.writeValueAsString(
                productService.search(List.of(new MinPriceCriteria(5), new NameAndDescriptionCriteria("Nok")))
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/products/search?minPrice=5&q=Nok"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedProducts));
    }

    @Test
    public void deleteProduct_shouldDelete_whenProvideValidId() throws Exception {
        var createProductRequest = new CreateProductRequest("Nokia", "Good phone", 10.0);

        var savedProduct = productService.save(createProductRequest);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", savedProduct.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteProduct_shouldNotDelete_whenProvideANotValidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}