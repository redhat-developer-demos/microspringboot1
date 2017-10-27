This demonstration illustrates the microservice principles
of:
* service discovery
* service load-balancing
* service resiliency

mvn spring-boot:run

http://localhost:8080 (outside of Kubernetes)

Invokes microspringboot2 (customers)

Invokes microspringboot3 (orders)

ToDo:

Figure out why @HystrixCommand does not work without @RequestMapping

Log Aggregation

Config Map Configuration
