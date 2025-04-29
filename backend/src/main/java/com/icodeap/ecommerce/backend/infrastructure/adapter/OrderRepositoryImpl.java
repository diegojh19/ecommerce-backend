package com.icodeap.ecommerce.backend.infrastructure.adapter;

import com.icodeap.ecommerce.backend.domain.model.Order;
import com.icodeap.ecommerce.backend.domain.model.OrderState;
import com.icodeap.ecommerce.backend.domain.port.IOrderRepository;
import com.icodeap.ecommerce.backend.infrastructure.Entity.OrderEntity;
import com.icodeap.ecommerce.backend.infrastructure.Entity.UserEntity;
import com.icodeap.ecommerce.backend.infrastructure.mapper.OrderMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl implements IOrderRepository {
    private final IOrderCrudRepository iOrderCrudRepository;
    private final OrderMapper orderMapper;

    public OrderRepositoryImpl(IOrderCrudRepository iOrderCrudRepository, OrderMapper orderMapper) {
        this.iOrderCrudRepository = iOrderCrudRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderMapper.toOrderEntity(order);
        orderEntity.getOrderProducts().forEach(
                orderProductEntity -> orderProductEntity.setOrderEntity(orderEntity)
        );
        return orderMapper.toOrder(iOrderCrudRepository.save(orderEntity));
    }

    @Override
    public Iterable<Order> findAll() {

        return orderMapper.toOrderList(iOrderCrudRepository.findAll());
    }

    @Override
    public Order findById(Integer id) {
        return orderMapper.toOrder(iOrderCrudRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("la orden con id "+id+" no existe")
        ));
    }

    @Override
    public Iterable<Order> findByUserId(Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return orderMapper.toOrderList(iOrderCrudRepository.findByUserEntity(userEntity));
    }

    @Override
    public void UpdateStateById(Integer id, String state) {
        if(state.equals(OrderState.CANCELLED.toString())){
            iOrderCrudRepository.updateStateById(id,OrderState.CANCELLED);
        }else{
            iOrderCrudRepository.updateStateById(id,OrderState.confirmed);
        }
    }
}
