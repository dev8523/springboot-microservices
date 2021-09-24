# springboot-microservices
This is a Movie Catalog API application.

# Communication and Service Discovery
- This project basically will show how one microservice is interacting/communicating with another microservice.
- We are doing Service Discovery using Eureka Client which will perform load balancing on the client side.

# Components Description
discovery-server : Eureka Server
movie-info-service : Eureka Client
movie-catalog-service : Eureka Client
rating-data-service : Eureka Client

# RestTemplate - To consume Rest API's and having communication.
