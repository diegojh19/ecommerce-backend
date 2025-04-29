package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.ProductService;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.infrastructure.mapper.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
@PostMapping
    public ResponseEntity<Product> save(@RequestParam("id") Integer id,
                                        @RequestParam("code") String code,
                                        @RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price")BigDecimal price,
                                        @RequestParam("urlImage") String urlImage,
                                        @RequestParam("userId") Integer userId,
                                        @RequestParam("categoryId") Integer categoryId,
                                        @RequestParam(value = "image", required = false)MultipartFile multipartFile
                                        ) throws IOException {

     Product product = new Product();
     product.setId(id);
     product.setCode(code);
     product.setName(name);
     product.setDescription(description);
     product.setPrice(price);
     product.setUrlImage(urlImage);
     product.setUserId(userId);
     product.setCategoryId(categoryId);

        return new ResponseEntity<>(productService.save(product, multipartFile), HttpStatus.CREATED);
    }

    // Método para actualizar un producto pasar // por form-data
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@RequestParam("id") Integer id,
                                          @RequestParam("code") String code,
                                          @RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("price") BigDecimal price,
                                          @RequestParam("urlImage") String urlImage,
                                          @RequestParam("userId") Integer userId,
                                          @RequestParam("categoryId") Integer categoryId,
                                          @RequestParam(value = "image", required = false) MultipartFile multipartFile
    ) throws IOException {
        // Buscar el producto existente
        Product existingProduct = productService.findById(id);

        if (existingProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Producto no encontrado
        }

        // Actualizar los valores del producto
        existingProduct.setId(id);
        existingProduct.setCode(code);
        existingProduct.setName(name);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setUrlImage(urlImage);
        existingProduct.setUserId(userId);
        existingProduct.setCategoryId(categoryId);

        // Guardar el producto actualizado
        return new ResponseEntity<>(productService.save(existingProduct, multipartFile), HttpStatus.OK);
    }




    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    // Endpoint para obtener los productos por category_entity_id
    @GetMapping("/categories")
    public Iterable<Product> getProductsByCategory(@RequestParam Integer categoryEntityId) {
        return productService.getProductsByCategory(categoryEntityId);
    }
}
