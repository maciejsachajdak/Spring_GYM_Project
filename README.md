# Spring Boot Gym Application Project
This is the simple project in Java Spring Boot, which allows to simulate real Gym Appplication. We have two types of mebers: User and Admin, first of them could manage his account, browse our clubs, add opinion and buy a pass. Admin have additionally CRUD repository to manage list of Users, but he can't add opinions and buy a pass.

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
* HTML/CSS
* javascript

## How to deploy this project:
* First you must click Code option in the top right corner above list of the files. Then you must copy the URL to this repository.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/ea64cdc1-cbb9-4213-9c53-3d69a2e3d6f1)

* Next step you need to open Java IDE, we suggest Intellij IDEA, and choose option "Get from Version Control", paste the URL to repository and choose folder where you want to deploy project.

  ![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/32733e73-55d2-434e-a2fa-32a615b3ab1d)

* Now you need to download kafka If you don't have it on your computer. We used tutorial below:
  
         https://youtu.be/EUzH9khPYgs?si=rl1sxHkl971KmDG5          
* Next step you need do start Zookeeper and Kafka Server
                  * You need to open two terminals: one for Zookeeper and second one for Kafka Server and paste this commands:
  
         For Zookeeper:
                  cd C:/kafka
                  .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

         For Kafka Server:
                  cd C:/kafka
                  .\bin\windows\kafka-server-start.bat .\config\server.properties
           
* Now you need to configure every application.properties file, in the red frame we have marked the part responsible for Kafka configuration, the blue one is responsible for the first database and the green one is responsible for the second one, in our case both databases are in Postgres
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/f5824ffb-7436-477a-a5d7-cd27cda3c48b)

* And now you are ready to start our application, please start application in order:
  
         eureka_service
         login_and_register_service
         user_panel_service
         club_service
         pass_service
         gateway_service

* Last step is open the browser and paste the link: http://localhost:8080/lr/login, our service has security: you can't go for example to user Panel without login, because we have a filter, which check your token, we keep it in cookie files, example below
  
  ![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/d59733a7-86da-46da-8368-20fb6e4f4ed6)

## About project:
### To check funcionality of the project please use one of the accounts below:
         Basic user:
            Login: darek
            Password: Darek123!
         Admin user:
            Login: janWianek
            Password: Janek123!
* Services have initialized some example data to databases:
  
         6 users
         4 clubs
         13 passes
         8 opinions
         2 roles
   
* On the start page we have login panel where we need to log in into admin or basic user account, if we don't have an account we can go to register page and create new account.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/93ce91c9-11ab-4118-bd82-c5d346fb03ab)

* On the registration page, we provide necessary information to create an account, including a unique login, email and a password that must meet security requirements. In case any of the previously mentioned conditions are not met or if any other fields are left incomplete, an appropriate message will be displayed, informing about the missing or incorrect information.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/f4f80f73-a927-4974-8dfd-9e99dbe7f8b8)

* After logging in, we are redirected to the main page that welcomes us. On this page, we hav top panel that allow us to go to clubs page, passes page, we can go into our account page and we can logout. If we are on the administrator's account, we also have the option to go to the page displaying users. The top panel is available on every page (apart from login and register page).
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/efc984b0-d8ec-43ca-85ba-96618519171d)

* In the user panel, we can view information about our account, make changes to it, including changing our password, and delete the entire account. If we decided to delete our account we have to confirm this action by entering our password. We also can display informations about our pass and club (if we have one assigned).
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/14598857-2303-40e6-b183-183e13755560)

* In the club section, we can view our clubs, image of club, description and opinion-based assessment. We can select one of the club and we will be redirected to more detailed page about selected club.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/a01d2487-d42a-4b3e-87bb-6e5c235bfdac)

* In chosen club page we can see more informations about the club, and also we can see opinions about this club. We can add new opinion by clicking the button which send us to another page. Admin can't add any opinion.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/1b2116eb-6fd9-43b9-b79c-b4de474cd57b)

* On opinion page we can add new opinion by filling opinion name, description and choosing rate from 1 to 5, where 5 is the highest.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/8d228c8a-672d-4e4d-9809-26cdd121b576)

* Next we have passes page wher we can see all available passes for purchase. From the scroled list we can choose specific pass which will be displayed above. There we can see name of the pass, description, available clubs, available entries and price. After buying pass it will be assigned to our account. Admin can't buy any pass.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/595ea720-eab2-4015-b247-3a4e2a7c08a2)

* After logging into admin account apart from the usual functions we also have access to page dedicated to menage all, not admin, accounts. From there we can see all registered accounts, we can see all informations and links to clubs and passes assigned to specific user. We can also edit, delete and add new user. Deleting user will require putting admin password. Creating new user will redirect us to another page wher we can insert all the informations, similar to register page, except password which is set to default password.
  
![image](https://github.com/maciejsachajdak/Spring_GYM_Project/assets/119767371/13f75bdc-dd13-45c0-ba1d-93dc979e973b)
