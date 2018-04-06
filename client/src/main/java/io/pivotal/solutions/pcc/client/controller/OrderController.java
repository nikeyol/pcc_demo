package io.pivotal.solutions.pcc.client.controller;

import io.pivotal.solutions.pcc.client.service.OrderService;
import io.pivotal.solutions.pcc.model.Order;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;


@RestController
@RequestMapping("/orders")
public class OrderController {

	@Resource(name = "orders")
	private Region<Integer, Order> orderRegion;

	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/clearData", method = RequestMethod.POST)
	public void clearData() {
		Set<Integer> orderKeySet = (DataPolicy.EMPTY.equals(orderRegion.getAttributes().getDataPolicy()))
				? orderRegion.keySetOnServer() : orderRegion.keySet();

		orderRegion.removeAll(orderKeySet);
	}

	@RequestMapping(value = "/clearDbData", method = RequestMethod.POST)
	public void clearDbData() {
		orderService.deleteAll();
	}

	@RequestMapping(value = "/loadOrderToDB", method = RequestMethod.POST)
	public void loadDataToDB(@RequestBody Order order) {
		orderService.save(order);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/readOrder", method = RequestMethod.GET)
	public Order getOrder(@RequestParam String id) {
		return orderService.findById(id);
	}

}
