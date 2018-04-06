package io.pivotal.solutions.pcc.client.controller;

import io.pivotal.solutions.pcc.model.Customer;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.Region;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Resource(name = "customers")
	private Region<String, Customer> customerRegion;

	@RequestMapping(value = "/clearCacheData", method = RequestMethod.POST)
	public void clearCacheData() {
		Set<String> customerKeySet = (DataPolicy.EMPTY.equals(customerRegion.getAttributes().getDataPolicy()))
				? customerRegion.keySetOnServer() : customerRegion.keySet();

		customerRegion.removeAll(customerKeySet);
	}

	@RequestMapping(value = "/writeThrough", method = RequestMethod.POST)
	public void loadCacheData(@RequestBody Customer customer) {
		customerRegion.put(customer.getId(), customer);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/readThrough", method = RequestMethod.GET)
	public Customer getCustomer(@RequestParam String id) {
		return customerRegion.get(id);
	}

	@RequestMapping(value = "/getServerCredentials", method = RequestMethod.GET)
	public List<String> getSecurity() {
		List<String> ret = new ArrayList<>();
		ret.add(System.getProperty("gemfire.sys.security-client-auth-init"));
		ret.add(System.getProperty("gemfire.sys.security-password"));
		ret.add(System.getProperty("gemfire.sys.security-username"));
		return ret;
	}
}
