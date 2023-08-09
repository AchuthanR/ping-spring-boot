# Dynamic notifications system using Spring Boot

Details about the microservices:
* gateway - Microservice that acts as an API gateway and routes requests to other microservices using their names
* service-discovery - Eureka server to discover and monitor other microservices
* auth-service - Authenticates users and issues jwt tokens
* admin-service - Microservice that provides APIs for admins to send notifications to users
* user-service - Microservice that provides APIs for users to view and manage their notifications

Steps for local setup:
1. git clone this repo.
2. Load all the microservices into the IDE (Use "Existing Maven Projects" in STS).
3. Update Maven dependencies in all the microservices.
4. Download lombok from [here](https://projectlombok.org/download) and install it in the IDE installation path. This is used to generate getters and setters on the fly to avoid boilerplate.
5. Make sure postgres database is installed and a database with the name of "ping" exists.
6. Run service discovery and gateway first. And then run the other microservices.
7. Use "http://localhost:8080/<SERVICE_NAME>/**" to access the APIs. Service names are mentioned above.
8. Most of the APIs are protected. So, use the "/auth-service/users/register" endpoint to register an user and use the "/auth-service/users/login" endpoint to get the jwt token to be used with all the protected requests.

API example:
Endpoint: /admin-service/notification/group
Payload: 
{
    "notification": {
        "title": "Account password about to be expired!",
        "content": "According to our password policy, passwords should be changed every 60 days. Your account password is about to be expired. We request you to change it immediately for safety purposes.",
        "timestamp": "2023-08-08T11:34:23",
        "status": {
            "id": 1
        },
        "category": {
            "id": 1 
        }
    },
    "userIds": [2, 3]
}
Response:
[
    {
        "id": 1,
        "title": "Account password about to be expired!",
        "content": "According to our password policy, passwords should be changed every 60 days. Your account password is about to be expired. We request you to change it immediately for safety purposes.",
        "timestamp": "2023-08-08T11:34:23",
        "status": {
            "id": 1,
            "status": "New"
        },
        "recipient": {
            "id": 2,
            "username": "achu",
            "role": "USER",
            "createdAt": "2023-08-08T00:10:06.05808",
            "modifiedAt": "2023-08-08T00:10:06.05808",
            "active": true
        },
        "category": {
            "id": 1,
            "category": "Alerts"
        },
        "readTimestamp": null
    },
    {
        "id": 2,
        "title": "Account password about to be expired!",
        "content": "According to our password policy, passwords should be changed every 60 days. Your account password is about to be expired. We request you to change it immediately for safety purposes.",
        "timestamp": "2023-08-08T11:34:23",
        "status": {
            "id": 1,
            "status": "New"
        },
        "recipient": {
            "id": 3,
            "username": "adarsh123",
            "role": "USER",
            "createdAt": "2023-08-08T14:18:30.731654",
            "modifiedAt": "2023-08-08T14:18:30.731654",
            "active": true
        },
        "category": {
            "id": 1,
            "category": "Alerts"
        },
        "readTimestamp": null
    }
]