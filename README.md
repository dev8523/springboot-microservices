# springboot-microservices
This is a Movie Catalog API application.

# Communication and Service Discovery
- This project basically will show how one microservice is interacting/communicating with another microservice using RestTemplate.
- We are doing Service Discovery using Eureka Client.
- Performing load balancing on the client side.

# Components Description
- discovery-server : Eureka Server
- movie-info-service : Eureka Client
- movie-catalog-service : Eureka Client
- rating-data-service : Eureka Client

# hosted uri's and port info
- Eureka server: http://localhost:8761/
- Get movie catalog details by userId: http://localhost:8081/catalog/foo
- Get Movie Info by movieId: http://localhost:8082/movies/123
- Get Movie ratings by movieId: http://localhost:8083/ratingsdata/movies/123
- Get User ratings by userId: http://localhost:8083/ratingsdata/users/123
