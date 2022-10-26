# Learnings.

## Section 1

1. After adding the spring security dependency you api is secured by default.
2. The username is user and the password you can see on the terminal from which you ran the project.
3. Once you use the username and password you will be able to access the api.
4. No need to again try username and password for the next request. (i.e once session of the browser it is valid.)
5. https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html
6. Add properties to override the username and password in application.properties.
7. Spring security internal flow
   ![Internal Flow](img.png) 
8. Debugging
   1. DefaultLoginPageGeneratingFilter ->  generateLoginPageHtml()
   2. Read about AbstractSecurityInterceptor -> finallyInvocation()
   3. UsernamePasswordAuthenticationFilter -> attemptAuthentication()
   4. ProviderManager -> authenticate()
   5. AbstractUserDetailsAuthenticationProvider(DaoAuthenticationProvider) ->authenticate()
   6. InMemoryUserDetailsManager -> loadUserByUsername()
9. Spring security sequence diagram.
   ![Internal Flow](img_1.png)
   1) User trying to access a secure page for the first time.
   2) Behind the scenes few filters like AbstractSecurityInterceptor, DefaultLoginPageGeneratingFilter identify that the user is not logged in & redirect the user to login page.
   3) User entered his credentials and the request is intercepted by filters
   4) Filters like UsernamePasswordAuthenticationFilter, extracts the username, password from the request and form an object of UsernamePasswordAuthenticationToken which is an implementation of Authentication interface. With the object created it invokes authenticate() method of ProviderManager.
   5) ProviderManager which is an implementation of AuthenticationManager identify the list of Authentication providers available that are supporting given authentication object style. In the default behaviour, authenticate() method of DaoAuthenticationProvider will be invoked by ProviderManager
   6) DaoAuthenticationProvider invokes the method loadUserByUsername() of InMemoryUserDetailsManager to load the user details from memory. Once the user details loaded, it takes help from the default password encoder implementation to compare the password and validate if the user is authentic or not.
   7) At last, it returns the Authentication object with the details of authentication success or not to ProviderManager
   8) ProviderManager checks if authentication is successful or not. If not, it will try with other available AuthenticationProviders. Otherwise, it simply returns the authentication details to the filters
   9) The Authentication object is stored in the SecurityContext object by the filter for future use and the response will be returned to the end user.
10. Spring security flow chart.
    ![Internal Flow](img_2.png)
11. JSESSIONID used to maintain multiple request of the user.

## Section 2

1. By default Spring security framework protects all the paths present inside the web application.This behaviour is due to code present inside the method defaultSecurityFilterChain(HttpSecurity http) of class SpringBootWebSecurityConfiguration 
   ```text
   @Bean
   @Order(SecurityProperties.BASIC_AUTH_ORDER)
   SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
      http.authorizeRequests().anyRequest().authenticated();
      http.formLogin();
      http.httpBasic();
      return http.build();
   }
   ```
2. The class WebSecurityConfigurerAdapter -> configure(HttpSecurity http) is deprecated so do not use. 

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