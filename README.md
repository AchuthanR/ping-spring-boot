#Dynamic notifications system using Spring boot

Details about the microservices:
* gateway - Microservice that acts as an API gateway and routes requests to other microservices using their names
* service-discovery - Eureka server to discover and monitor other microservices
* auth-service - Authenticates users and issues jwt tokens
* admin-service - Microservice that provides APIs for admins to send notifications to users
* user-service - Microservice that provides APIs for users to view and manage their notifications