# SpringBlog

This is a SpringBoot REST API application that provides user and blog post management functionality, as well as out-of-memory storage using MS SQL Server.

## Project Structure (Still a work in progress)
```
src/
├── main/
│   ├── java/
│   │   └── com/strawberry/springblog/
│   │       ├── SpringblogApplication.java
│   │       ├── controller/
│   │       │   ├── UserController.java
│   │       │   └── PostController.java
│   │       ├── service/
│   │       │   ├── UserService.java
│   │       │   ├── PostService.java
│   │       │   └── impl/
│   │       │       ├── UserServiceImpl.java
│   │       │       └── PostServiceImpl.java
│   │       ├── repository/
│   │       │   ├── UserRepository.java
│   │       │   └── PostRepository.java
│   │       └── model/
│   │           ├── User.java
│   │           └── Post.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/strawberry/springblog/
            └── SpringblogApplicationTests.java




localhost/ (IN MS SQL SERVER MANAGEMENT STUDIO)
└── Databases/
    ├── blog-db/
        └── Tables/
            ├── dbo.Posts
            └── dbo.Users/
```
