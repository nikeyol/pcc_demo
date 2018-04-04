package io.pivotal.solutions.pcc.client.controller;

import io.pivotal.solutions.pcc.server.Customer;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.Region;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
public class CustomerController {

	@Resource(name = "customers")
	private Region<Integer, Customer> customerRegion;

	@RequestMapping(value = "/clearData", method = RequestMethod.POST)
	public void clearData() {
		Set<Integer> customerKeySet = (DataPolicy.EMPTY.equals(customerRegion.getAttributes().getDataPolicy()))
				? customerRegion.keySetOnServer() : customerRegion.keySet();

		customerRegion.removeAll(customerKeySet);
	}

	//Put vs PutAll -> PutAll batch size custom loop implementation on client side
	@RequestMapping(value = "/loadDataByRegionPutAll", method = RequestMethod.POST)
	public void loadDataByRegionPutAll() {
		clearData();

		Customer customer1 = new Customer();
		customer1.setGivenname("Shaozhen");
		customerRegion.put(1, customer1);

		Customer customer2 = new Customer();
		customer1.setGivenname("Jeff");
		customerRegion.put(2, customer2);

	}

	//Load Data to cache on miss.
	@RequestMapping(value = "/loadDataByServerCachLoader", method = RequestMethod.POST)
	public void loadDataByServerCachLoader() {
		clearData();

		customerRegion.get(1);
		customerRegion.get(2);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCustomers", method = RequestMethod.GET)
	public List<Customer> getCustomers() {
		Set<Integer> customerKeySet = (DataPolicy.EMPTY.equals(customerRegion.getAttributes().getDataPolicy()))
				? customerRegion.keySetOnServer() : customerRegion.keySet();
		List<Customer> customers = new ArrayList<>();
		for (Integer i: customerKeySet){
			customers.add(customerRegion.get(i));
		}
		return customers;
	}

	@RequestMapping(value = "/getSecurity", method = RequestMethod.GET)
	public List<String> getSecurity() {
		List<String> ret = new ArrayList<>();
		ret.add(System.getProperty("gemfire.sys.security-client-auth-init"));
		ret.add(System.getProperty("gemfire.sys.security-password"));
		ret.add(System.getProperty("gemfire.sys.security-username"));
		return ret;
	}
}
