# Lab 06

## Assignment:
Spring Boot, Spring Data, Hibernate, H2, Spring Aspects, Thymeleaf
##Develop:
User Registration & Login with CAPTCHA (Completely Automated Public Turing test to tell Computers and Human Apart)and one super admin
##Description:

1.Create User Registration page with captcha.

2.Registered userdetails must be save to H2 in-memory database

3.Create login page with captcha

4.Captcha should have a reload button to re-send request to regenerate the captcha

5.Write server side code for generating captcha image and sending it back with response.

6.Code to verify whether the captcha string entered by user and stored in the session matches or not.

7.Implement views for the page using thymeleaf.8.Use Spring Security for managing user roles (guest user, logged-in user, super-admin user)


##Activity

- [x] Create a Spring Boot Maven project
- [x] Use POM.xml to manage dependencies
- [x] Spring Boot Entry Point with SpringApplication.run is present and working
- [x] application.properties is created with required configuration
- [x] Controllers are created in repsective package (com.something.controller)
- [x] POJO classes are created where required in respective package (com.something.pojo)
- [x] Entity classes are created where required in respective package (com.something.entity) for different groups of users
- [x] Service classes are created where required in respective package (com.something.service)
- [x] DAO classes are created where required in respective package (com.something.dao)
- [x] Thymeleaf View is created for user registration
- [x] Thymeleaf View is created for user login
- [x] Thymeleaf View is created for account update
- [x] If the user is not logged in, account update page is redirecting the user to login page; with line saying - login to update your account
- [x] If the user is logged in, he/she can logout to come out of the secure session
- [x] Admin (Page with dummy message about admin) page is visible and accessible only to the logged-in user who is super-admin
- [x] Spring Security is used to implement user roles (guest user, logged-in user, super user)
- [x] CAPTCHA image is being created by class in util package (com.something.util) and sent with response to view
- [x] Have used Spring logging to dump the logs
- [x] Have used Spring Validations for validating the user registration information in respective package (com.something.validation)
- [x] Have used Spring Exceptions to manage exceptions in respective package (com.something.exception)
- [x] Any extra other than documented

1. Remember me