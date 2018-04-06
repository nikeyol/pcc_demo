package io.pivotal.solutions.pcc.client;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.support.GemfireCacheManager;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
@EntityScan(basePackages = "io.pivotal.solutions.pcc.model")
public class GemFireCustomerOrderClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemFireCustomerOrderClientApplication.class, args);
	}

    @Autowired
    @Bean(name = "customers")
    public ClientRegionFactoryBean<Integer, String> customerRegion(ClientCache cache) {
        ClientRegionFactoryBean<Integer, String> testRegion = new ClientRegionFactoryBean<>();
        testRegion.setName("customers");
        testRegion.setCache(cache);
        testRegion.setShortcut(ClientRegionShortcut.PROXY);
        return testRegion;
    }

    @Autowired
    @Bean(name = "orders")
    public ClientRegionFactoryBean<Integer, String> orderRegion(ClientCache cache) {
        ClientRegionFactoryBean<Integer, String> testRegion = new ClientRegionFactoryBean<>();
        testRegion.setName("orders");
        testRegion.setCache(cache);
        testRegion.setShortcut(ClientRegionShortcut.PROXY);
        return testRegion;
    }

    @Autowired
    @Bean
    public CacheManager cacheManager(ClientCache cache) {
        GemfireCacheManager cacheManager = new GemfireCacheManager();
	    cacheManager.setCache(cache);
	    return cacheManager;
    }
}
