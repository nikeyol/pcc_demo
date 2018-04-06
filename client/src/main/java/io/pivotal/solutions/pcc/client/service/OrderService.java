package io.pivotal.solutions.pcc.client.service;

import io.pivotal.solutions.pcc.client.dao.OrderRepository;
import io.pivotal.solutions.pcc.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;


    @Cacheable(cacheNames = "orders")
    public Order findById(String id){
        //Simulate the slowness of DB
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return orderRepository.findById(id);
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

}
