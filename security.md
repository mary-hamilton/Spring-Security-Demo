# Security Todo List

We need ... 
- [x] to allow users to signup
- [x] to allow users to login with a username and password
- [x] a way to store users
- [x] to tell Spring how we define users and how to get their users
- [x] to not store plain text passwords in the database
- [x] a way to give users a key that...
  - [x] they can use with all requests
  - [x] that tells the server who they are and what their role is
  - [x] that cannot be spoofed
  - [x] that is not stored in the database
  - [x] that allows a user to verify the issuer

Dependencies (old)
- Spring Boot DevTools
- Spring Web
- Spring Data JPA
- MySQL Driver
- Validation

Dependencies (new)
- Spring Configuration Processor - allows us to inject properties into our application
- Spring Security - allows us to configure what authenticated users can and can't do
- OAuth2 Authorization Server