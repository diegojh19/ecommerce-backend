package com.icodeap.ecommerce.backend.domain.port;

import com.icodeap.ecommerce.backend.domain.model.Order;

public interface IOrderRepository {

    Order save(Order order);
    Iterable<Order> findAll();
    Order findById(Integer id);
    Iterable<Order> findByUserId(Integer userId);
    void UpdateStateById(Integer id, String state);
}
