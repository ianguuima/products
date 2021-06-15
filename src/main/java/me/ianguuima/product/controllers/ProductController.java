package me.ianguuima.product.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.ianguuima.product.models.product.Product;
import me.ianguuima.product.models.product.requests.CreateProductRequest;
import me.ianguuima.product.models.product.requests.UpdateProductRequest;
import me.ianguuima.product.services.ProductService;
import me.ianguuima.product.models.product.ProductQueryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/products")
@Api(tags = "Product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo produto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Produto criado com sucesso")
    })
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        var product = productService.save(createProductRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um produto existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto atualizado com sucesso"),
            @ApiResponse(code = 404, message = "Id do produto não encontrado")
    })
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest updateProductRequest) {
        var product = productService.update(id, updateProductRequest);
        return ResponseEntity.of(product);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Pega um produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto buscado com sucesso"),
            @ApiResponse(code = 404, message = "Id do produto não encontrado")
    })
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        var product = productService.findById(id);
        return ResponseEntity.of(product);
    }

    @GetMapping
    @PostMapping
    @ApiOperation(value = "Retorna todos os produtos disponíveis na database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de produtos retornada com sucesso"),
    })
    public ResponseEntity<Collection<Product>> getAllProducts() {
        var products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Busca pela lista de todos os produtos especificando uma condição")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de produtos que satisfazem a query"),
    })
    public ResponseEntity<Collection<Product>> searchProducts(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "0") Long minPrice,
            @RequestParam(required = false, defaultValue = "0") Long maxPrice
    ) {
        var productCriteriaList = new ProductQueryBuilder()
                .nameAndDescription(q)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .getCriteria();

        var searchResult = productService.search(productCriteriaList);
        return ResponseEntity.ok(searchResult);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um produto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produto deletado com sucesso"),
            @ApiResponse(code = 404, message = "Id do produto não encontrado")
    })
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        var deletionResult = productService.delete(id);
        if (deletionResult) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}
