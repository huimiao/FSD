# Lab 05

## Assignment:
Spring Core, Spring MVC, REST, Security, Validation, Exception, Logging, JWT

##Develop:
User Registration & Login with CAPTCHA (Completely Automated Public Turing test to tell Computers and Human Apart)and one super admin

##Description:
1. Create User Registration page with captcha.
2. Registered user details must be save to H2 in-memory database
3. Create login page with captcha
4. Captcha should have a reload button to re-send request to regenerate the captcha
5. Write server side code for generating captcha image and sending it back with response.
6. Code to verify whether the captcha string entered by user and stored in the session matches or not.

##Activity
- [x] Create a Maven project
- [x] Use POM.xml to manage dependencies
- [x] Deployment Descriptor is created and configured to open default page of the spring mvc application
- [x] Dispatcher Servlet is created and configured as per requirements
- [x] Controllers are created in respective package (com.something.controller)
- [x] POJO classes are created where required in respective package (com.something.pojo)
- [x] Entity classes are created where required in respective package (com.something.entity) for different groups of users
- [x] Service classes are created where required in respective package (com.something.service)
- [x] DAO classes are created where required in respective package (com.something.dao)
- [x] View is created for user registration
- [x] View is created for user login
- [x] View is created for account update
- [x] If the user is not logged in, account update page is redirecting the user to login page; with line saying - login to update your account
- [x] If the user is logged in, he/she can logout to come out of the secure session
- [x] Admin (Page with dummy message about admin) page is visible and accessible only to the logged-in user who is super-admin
- [x] Spring Security is used to implement user roles (guest user, logged-in user, super user)
- [x] CAPTCHA image is being created by class in utl package (com.something.utl) and sent with response to view
- [x] Have used Spring logging to dump the logs
- [x] Have used Spring Validations for validating the user registration information in respective package (com.something.validation)
- [x] Have used Spring Exceptions to manage exceptions in respective package (com.something.exception)
- [ ] Any extra other than documented
1. JWT used for authentication