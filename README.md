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
* Valid AWS account credential

In this sample application, we are trying to add new hotel into collections of hotels. We can add new hotel, remove a hotel, list a hotel, list all hotels and also we can create/delete hotel table from the AWS.

```
Rest API:
   Table: 
       Creation:            POST <address>/create -- Create a table with name Hotel
       Deletion:            Delete <address>     -- delete a table with name Hotel
  
    Hotel:
      Add new hotel:         POST <address>      -- JSON request  { "name": "<hotel name>"}
      List Hotel:            GET <address>/{id}  -- Needs to provide hotel id which returned while adding a new hotel
      List All Hotels:       GET <address>       -- List all hotels with hotel id and name
  ```
