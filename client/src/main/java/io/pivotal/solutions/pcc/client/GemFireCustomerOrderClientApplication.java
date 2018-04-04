package io.pivotal.solutions.pcc.client;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class GemFireCustomerOrderClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireCustomerOrderClientApplication.class, args);
	}

    @Autowired
    @Bean(name = "customers")
    public ClientRegionFactoryBean<Integer, String> testLocalRegion(ClientCache cache) {
        ClientRegionFactoryBean<Integer, String> testRegion = new ClientRegionFactoryBean<>();
        testRegion.setName("customers");
        testRegion.setCache(cache);
        testRegion.setShortcut(ClientRegionShortcut.PROXY);
        return testRegion;
    }
}
