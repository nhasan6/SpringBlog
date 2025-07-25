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
## Postman API Visualization

<img width="966" height="605" alt="Screenshot 2025-07-25 155708" src="https://github.com/user-attachments/assets/ebabbbe2-981e-43fe-a786-1dc365f7ef0a" />
(Updating User information)

<img width="1029" height="868" alt="Screenshot 2025-07-25 155055" src="https://github.com/user-attachments/assets/5401eca8-0d22-44f0-afde-7f6a1bdbce9d" />
(Creating Posts)



