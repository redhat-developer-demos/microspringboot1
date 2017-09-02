package com.redhat.developers.osio.microspringboot1;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class TheRESTController {

    RestTemplate restTemplate;

    @Value("${microspringboot1.customer_url}")
    String customerServiceUrl;

    @Autowired
    public TheRESTController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    @HystrixCommand(fallbackMethod = "topLevelFallback")
    public String getCustomersWithOrders() {
        System.out.println("getCustomers()");

        final URI customerUri = UriComponentsBuilder
            .fromHttpUrl(customerServiceUrl)
            .path("/api/customer")
            .build()
            .toUri();


        System.out.println("Customer Uri " + customerUri.toString());

        String customers = restTemplate.getForObject(customerUri, String.class);

        System.out.println(String.format("Got Customers: %s", customers));

        final URI customerOrdersUri = UriComponentsBuilder
            .fromHttpUrl(customerServiceUrl)
            .path("/api/customer/orders")
            .build()
            .toUri();

        System.out.println("Customer Order Uri " + customerOrdersUri.toString());

        String customerOrders = restTemplate.postForObject(customerOrdersUri, customers, String.class);

        System.out.println(String.format("Got Customers Orders: %s", customerOrders));

        return customerOrders;
    } // getCustomers

    private String topLevelFallback() {
        return "Danger, Will Robinson";
    }

}
