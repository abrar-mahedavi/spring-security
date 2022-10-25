# Learnings.

1. After adding the spring security dependency you api is secured by default.
2. The username is user and the password you can see on the terminal from which you ran the project.
3. Once you use the username and password you will be able to access the api.
4. No need to again try username and password for the next request. (i.e once session of the browser it is valid.)
5. https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html
6. Add properties to override the username and password in application.properties.
7. Spring security internal flow
   ![Internal Flow](img.png)
8. 

# Reading and explorations.
1. Do read about JSESSIONID how and when it gets created and destroyed, what is the significance when to use it.
2. Read about servlets and filters.
3. Read about spring security filters (approx 25 filters). 

# Links
1. https://www.baeldung.com/spring-security-registered-filters#:~:text=Important%20Spring%20Security%20Filters&text=AnonymousAuthenticationFilter%3A%20when%20there's%20no%20authentication,ExceptionTranslationFilter%3A%20catch%20Spring%20Security%20exceptions
2. https://stackoverflow.com/questions/10140515/spring-security-where-can-i-find-the-list-of-all-security-filters-registered-w
3. https://docs.spring.io/spring-security/site/docs/3.1.4.RELEASE/reference/security-filter-chain.html
4. https://www.javadevjournal.com/spring-security/spring-security-filters/
5. https://ckinan.com/blog/spring-security-filter-chain/
6. https://visweshwar.medium.com/debugging-a-custom-spring-security-filterchain-3a2ea3f5ee38
7. 