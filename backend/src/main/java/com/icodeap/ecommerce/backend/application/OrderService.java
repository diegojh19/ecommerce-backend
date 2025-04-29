package com.icodeap.ecommerce.backend.application;

import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.port.IOrderRepository;

public class OrderService {
    private final IOrderRepository iOrderRepository;

    public OrderService(IOrderRepository iOrderRepository) {
        this.iOrderRepository = iOrderRepository;
    }

    public Order save(Order order){
        return iOrderRepository.save(order);
    }
    public Iterable<Order> findAll(){
        return iOrderRepository.findAll();
    }
    public Iterable<Order> findByUserId(Integer userId){
        return iOrderRepository.findByUserId(userId);
    }
    public void updateStateById(Integer id, String state){
        iOrderRepository.UpdateStateById(id,state);
    }

    public Order findById(Integer id){
        return iOrderRepository.findById(id);
    }
}
