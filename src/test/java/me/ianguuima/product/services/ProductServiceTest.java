package me.ianguuima.product.services;

import me.ianguuima.product.criteria.MinPriceCriteria;
import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.models.product.requests.UpdateProductRequest;
import me.ianguuima.product.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static me.ianguuima.product.fixtures.ProductFixture.getRandomProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void save_shouldReturnSavedProduct_whenProvideValidCreateProductRequest() {
        var product = getRandomProduct("Nokia", "Good product, trust me");
        when(productRepository.save(any())).thenReturn(product);

        var createProductRequest = new CreateProductRequest(
                "Nokia",
                "Good product, trust me",
                10.0
        );

        var savedProduct = productService.save(createProductRequest);
        assertEquals(product, savedProduct);
    }

    @Test
    public void findById_shouldReturnProduct_whenProvideItsId() {
        var product = new Product(1L, "Nokia", "Good product, trust me", 10.0);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        var foundProduct = productService.findById(1L);
        assertThat(foundProduct).isPresent().get().isEqualTo(product);
    }

    @Test
    public void findById_shouldReturnEmpty_whenProductIdIsNotValid() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        var foundProduct = productService.findById(1L);
        assertThat(foundProduct).isNotPresent();
    }

    @Test
    public void findAll_shouldReturnAllProducts() {
        var allProductsInDatabase = List.of(
                getRandomProduct("Nokia", "A good cellphone"),
                getRandomProduct("Nokia S5", "A new strong generation"),
                getRandomProduct("Alternative Cellphone", "A cheaper alternative to Nokia")
        );

        when(productRepository.findAll()).thenReturn(allProductsInDatabase);

        var foundProducts = productService.findAll();

        assertThat(foundProducts).hasSize(3);
        assertThat(foundProducts).containsExactlyElementsOf(allProductsInDatabase);
    }

    @Test
    public void update_shouldUpdateFoundProduct_whenProvideProductIdAndUpdateProductRequest() {
        var productId = 1L;
        var updateProductRequest = new UpdateProductRequest("Nokia New Gen", "This is a new gen", 15.0);

        var oldProduct = new Product(1L, "Nokia Old Gen", "This is a old gen", 10.0);
        var incomingUpdatedProduct = new Product(1L, "Nokia New Gen", "This is a new gen", 15.0);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(oldProduct));
        when(productRepository.save(incomingUpdatedProduct)).thenReturn(incomingUpdatedProduct);

        var updatedProduct = productService.update(productId, updateProductRequest);

        assertThat(updatedProduct).isPresent().get().isEqualTo(incomingUpdatedProduct);
    }

    @Test
    public void update_shouldReturnOptionalEmpty_whenProvideANotValidProductId() {
        var productId = 1L;
        var updateProductRequest = new UpdateProductRequest("Nokia New Gen", "This is a new gen", 15.0);

        var incomingUpdatedProduct = new Product(1L, "Nokia New Gen", "This is a new gen", 15.0);

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(productRepository.save(incomingUpdatedProduct)).thenReturn(incomingUpdatedProduct);

        var updatedProduct = productService.update(productId, updateProductRequest);

        assertThat(updatedProduct).isNotPresent();
    }

    @Test
    public void delete_shouldDeleteProduct_whenProvideValidProductId() {
        var randomProduct = getRandomProduct(1L);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(randomProduct));

        assertTrue(productService.delete(1L));
    }

    @Test
    public void delete_shouldNotDeleteProduct_whenProvideANotValidProductId() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertFalse(productService.delete(1L));
    }

    @Test
    public void search_shouldReturnAllProductsCorrespondingToCriteria_whenProvideAListOfCriteria() {
        var allProductsInDatabase = List.of(
                getRandomProduct(5),
                getRandomProduct(3),
                getRandomProduct(2)
        );

        when(productRepository.findAll()).thenReturn(allProductsInDatabase);

        var minPriceCriteria = new MinPriceCriteria(4);
        var filteredProducts = productService.search(singletonList(minPriceCriteria));;

        assertThat(filteredProducts).hasSize(1);
        assertThat(filteredProducts).extracting(Product::getPrice).first().isEqualTo(5.0);
    }


}