# Spring Boot Gym Application Project
This is simple project in Java Spring Boot, which allows to simulate real Gym Appplication. We have two types of mebers: User and Admin, first of them could manage his account, browse our clubs, add opinion and buy a pass. Admin have additionally CRUD repository to manage list of Users, but he can't add opinions and buy a pass.

## Main Technoligies:
* Java 17
* Spring boot
* Spring Cloud Netflix - Eureka
* Kafka
* PostgreSQL
* Lombok
* Gradle
* Spring security
* Thymeleaf
* html/css
* javascript

## How to deploy this project:
* First you must click Code option in the top right corner above list of the files. Then you must copy the URL to this repository.
* Next step you need to open Java IDE, we suggest Intellij IDEA, and choose option "Get from Version Control", paste the URL to repository and choose folder where you want to deploy project.
* If you open the project, you need to click "Load Gradle Project"
* Now you need to download kafka If you don't have it on your computer.
         https://youtu.be/EUzH9khPYgs?si=rl1sxHkl971KmDG5         - we use this tutorial
* Next step you nee do start Zookeeper and Kafka Server
*

## About project:
### To check funcionality of the project please use one of the accounts below:
         Basic user:
            Login: darek
            Password: Darek123!
         Admin user:
            Login: janWianek
            Password: Janek123!
* On the start page we have login panel where we need to log in into admin or basic user account, if we dont have account we can go to register page and create new account.

* On the registration page, we provide necessary information to create an account, including a unique login, email and a password that must meet security requirements. In case any of the previously mentioned conditions are not met or if any other fields are left incomplete, an appropriate message will be displayed, informing about the missing or incorrect information.

* After logging in, we are redirected to the main page that welcomes us. On this page, we hav top panel that allow us to go to clubs page, passes page, we can go into our account page and we can logout. If we are on the administrator's account, we also have the option to go to the page displaying users.

* In the user panel, we can view information about our account, make changes to it, including changing our password, and delete the entire account. We also can display informations about our pass and club (if we have one assigned).

* In the club section, we can view our clubs, description and . We can also add reviews, which involves selecting a rating from 1 to 5 and providing a textual opinion. The administrator does not have access to adding reviews.
