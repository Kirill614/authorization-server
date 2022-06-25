# Authorization Server
This project is an authorization server designed according to the Oauth 2.0 framework specification. The main purpose of this server is to authorize users to access protected resources by issuing access tokens.
## Supported features
* [RFC 6749 : Oauth 2.0 Framework](https://datatracker.ietf.org/doc/html/rfc6749)
* Oauth 2.0 grant flows
  * [Authorization code grant](https://datatracker.ietf.org/doc/html/rfc6749#section-4.1)
  * [Client credentials flow](https://datatracker.ietf.org/doc/html/rfc6749#section-4.4)
* [Issuing an access token](https://datatracker.ietf.org/doc/html/rfc6749#section-5)
* [Refreshing acceess token](https://datatracker.ietf.org/doc/html/rfc6749#section-6)
* [Oauth 2.0 Client registration](https://datatracker.ietf.org/doc/html/rfc6749#section-2)
* User and admin registration
* RESTful API for managing clients, registered users, getting information about users
## Authorization server run instructions
This Spring Boot application requires Java 8 JDK or higher, also it uses maven as its build system.To run the authorization server just run Spring Boot starter class com.example.demo.AuthorizationServerApplication via your IDE.


