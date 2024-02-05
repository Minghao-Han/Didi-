# Didi

### Description
{**This project is a online taxi back-end based on micro-service architecture.**}

### Software Architecture

This is a micro-service architecture style system. The entire system consists of three microservices: Bill Management, Driver Management, and Passenger Management. Each microservice has its own database. Every microservice is composed of three layers: the controller layer, the service layer, and the data access layer.

- Passenger Management: This part handles passenger-related tasks, such as requesting or canceling a taxi.
- Driver Management: This part handles driver-related tasks, such as accepting orders, changing status, and reviewing past bills.
- Bill Management: This part is responsible for bill-related operations, such as receiving orders from passengers and assigning them to drivers.

### Tech Stack

1. Eureka is utilized for service management.  
2. Mybatis is employed for data access.  
3. Openfeign is applied to streamline remote requests.  
4. Spring Cloud Gateway is used to route requests to specific services.  
5. Spring MVC is utilized as the framework.
6. Jwt to verify users.

### Illustration of Some Function

1. Calling a Taxi  
When a passenger tries to call a taxi, it will send a request containing destination and starting-point information. The gateway will dispatch that request to an instance of the service named PASSENGER-MANAGEMENT. The informations including destination, starting-point and passenger id will then be send an instance of bill service through remote calls. The bill service, BILL-MANAGEMENT, hold a queue of orders waiting to be accpeted by drivers. Whenever a request for taxi comes in, the bill service will create a Bill instance and put it into that queue in a blocking way (the thread will be blocked if that queue is full). Since that, this operation is executed in a new thread to avoid long http connection. The drivers can receive orders by sending requests and the bill service will then pop out an Bill instance as return.
2. User Varification
Both driver management and passenger management will generate and assign a Jwt (json web token) to the user that has successfully logged in. This jwt will be put into request header and verified while trying to pass the gateway.
3. Annotation @UserId  
Almost every opereation except login and register needs the id of user. To security concern, passing user id in plaintext through internet might not be a good idea. Thus, I put the user id into jwt and extract the id when passing gateway. The perticular micro service can then make use of it by simply add a @UserId annotation. The augement resolver of @UserId will get the value of id and use it to set the annotated varible.

### Problems & Solutions
Problem No.1 is resolved but No.2 and No.3 are not.

1. `Problem`: Orders are reserved in memory. This can result in faster response speed comparing to extract from databse every time. But it'll also lead to high memory occupation or even overflow.  
`Solution`: Use an ArrayBlockingQueue type queue named waitingBills and set a limitation to it. The bill service will firstly save the new order with WAITING status in database and put it into waitingBills in a new thread. In this way, the request can be responsed quickly and passenger can also check their order eventhough the new order haven't enter waitingList.
2. `Problem`: This system haven't connected to any map system. It means drivers are receiving orders regardless of location and distance. Also, fares depending on distance can't be appropriately calculated.
`Solution`: Attach to a map system like baidu map using its api.
3. `Problem`: The system doesn't attach to or has any payment system. As a consequese, passengers can only pay their fares offline.

#### Gitee Feature

1.  You can use Readme\_XXX.md to support different languages, such as Readme\_en.md, Readme\_zh.md
2.  Gitee blog [blog.gitee.com](https://blog.gitee.com)
3.  Explore open source project [https://gitee.com/explore](https://gitee.com/explore)
4.  The most valuable open source project [GVP](https://gitee.com/gvp)
5.  The manual of Gitee [https://gitee.com/help](https://gitee.com/help)
6.  The most popular members  [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
