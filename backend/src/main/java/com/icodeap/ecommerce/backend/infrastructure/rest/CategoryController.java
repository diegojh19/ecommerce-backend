package com.icodeap.ecommerce.backend.infrastructure.rest;

import com.icodeap.ecommerce.backend.application.CategoryService;
import com.icodeap.ecommerce.backend.domain.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@CrossOrigin(origins = "http://localhost:4200")


public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@RequestBody Category category, @PathVariable Integer id){
        // Asignamos el id recibido desde la URL al objeto category
        category.setId(id);
        // Llamamos al servicio para actualizar la categoria
        Category categories = categoryService.update(category);

        // Si no se encuentra la categoria, devolvemos un 404
        if (categories == null) {
            return ResponseEntity.notFound().build();
        }

        // Si la actualización fue exitosa, devolvemos la categoria actualizada
        //return ResponseEntity.ok(categor);

        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

