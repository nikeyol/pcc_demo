package io.pivotal.solutions.pcc.client.dao;

import io.pivotal.solutions.pcc.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {
}
