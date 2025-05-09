package com.icodeap.ecommerce.backend.domain.port;

import com.icodeap.ecommerce.backend.domain.model.Product;

import java.util.List;

public interface IProductRepository {

    Product save(Product product);
    Iterable<Product> findAll();
    Product findById(Integer id);
    void deleteById(Integer id);
    List<Product> findByCategoryEntityId(Integer categoryEntityId);



}
