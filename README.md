# playing_with_aws_dynamodb
How to use dynamodb NoSQL using Java AWS SDK and Rest services

### Why to use Dynamodb?

* Document style
* Highly available (replicas are in 3 available zone)
* Price on (10 write /sec) and read (50 ops/sec)
* Generally use for read intensive application
* Read is cheaper but write is costly
* Can be scalable at any level
* Use SSD hard risk for better performance

### Requirements

* aws-java-sdk-dynamodb
* spring-boot-starter-web
* lombok
