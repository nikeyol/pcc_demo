package io.pivotal.solutions.pcc.client.dao;

import io.pivotal.solutions.pcc.model.Order;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, String> {
    Order findById(String id);
}
