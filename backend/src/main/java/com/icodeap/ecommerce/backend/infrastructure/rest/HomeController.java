package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.CategoryService;
import com.icodeap.ecommerce.backend.application.ProductService;
import com.icodeap.ecommerce.backend.application.SliderService;
import com.icodeap.ecommerce.backend.domain.model.Category;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.model.Slider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@CrossOrigin(origins = "http://localhost:4200")

public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    private final SliderService sliderService;


    public HomeController(ProductService productService, CategoryService categoryService, SliderService sliderService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.sliderService = sliderService;
    }
    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

    // Endpoint para obtener todas las categorías
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> findAllCategories() {
        return ResponseEntity.ok((List<Category>) categoryService.findAll());
    }

    @GetMapping("/sliders")
    public ResponseEntity<List<Slider>> findAllSlider() {
        return ResponseEntity.ok((List<Slider>) sliderService.findAll());
    }
    // Endpoint para obtener todas las categorías por id
    @GetMapping("/categoriess")
    public Iterable<Product> getProductsByCategory(@RequestParam Integer categoryEntityId) {
        return productService.getProductsByCategory(categoryEntityId);
    }
}
