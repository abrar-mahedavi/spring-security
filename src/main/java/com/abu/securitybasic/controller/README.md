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
3. We can secure the web application API's, path as per our custom requirements using Spring Security framework like below.
   ```text
   @Bean
   @Order(SecurityProperties.BASIC_AUTH_ORDER)
   SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
           http.authorizeHttpRequests()
                .antMatchers("/api/v1/my-account", "/api/v1/my-balance", "/api/v1/my-loans", "/api/v1/my-cards")
                .authenticated()
                .antMatchers("/notices", "/contact")
                .permitAll()
                .and().httpBasic()
                .and().formLogin();
        return http.build();
   }
   ```
4. To logout of the session use the `http://localhost:8080/logout`
5. If you want to deny all the request \
   `Note :- Not for production.`
   ```text
   @Bean
   @Order(SecurityProperties.BASIC_AUTH_ORDER)
   SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
           http.authorizeHttpRequests()
                .antRequests().denyAll()
                .and().httpBasic()
                .and().formLogin();
        return http.build();
   }
   ```
6. We can permit all the requests coming towards our web application API's, Paths using Spring Security framework like shown below
   `Note :- Not for test/dev environmnet.`
   ```text
   @Bean 
   @Order(SecurityProperties.BASIC_AUTH_ORDER)
   SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
           http.authorizeHttpRequests()
                .antRequests().permitAll()
                .and().httpBasic()
                .and().formLogin();
        return http.build();
   }
   ```

## Section 3
1. Instead of defining a single user inside application.properties as a next step we can define multiple users along with their authorities with help of InMemoryDetailsManger & User Details
   1. Approach 1 where we use withDefaultPasswordEncoder() method while creating the user details
        ```text
        @Bean
        public InMemoryUserDetailsManager userDetailsService() {
            /**
            * Approach 1 where we use withDefaultPasswordEncoder() method
            * while creating the user details
            */
            UserDetails admin = User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("12345")
                               .authorities("admin")
                               .build();
            UserDetails user = User.withDefaultPasswordEncoder()
                                .username("user")
                                .password("12345")
                                .authorities("read")
                               .build();
        return new InMemoryUserDetailsManager(admin, user);
        }
        ```
      1. We need User object which requires a password encoder and role.
   2. Where we don't define password encoder while creating the user details.Instead, a separate PasswordEncoder bean will be created 
      ```text 
         @Bean
         public InMemoryUserDetailsManager userDetailsService() {
            /**
            * Approach 2 where we don't define password encoder
            * while creating the user details. Instead a separate
            * PasswordEncoder bean will be created.
            */
            InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
            UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
            UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
            userDetailsService.createUser(admin);
            userDetailsService.createUser(user);
            return userDetailsService; 
         }
      
         @Bean
         public PasswordEncoder passwordEncoder() {
            return NoOpPasswordEncoder.getInstance();
         }
         ```
2. User Management important classes and interfaces.
   ![Internal Flow](img_3.png)
3. org.springframework.security.core.userdetails.UserDetails.java \
   Has all the functions
4. What is the significance of Authentication object and UserDetails
   ![Internal Flow](img_4 .png)
5. org.springframework.security.core.Authentication
6. javax.security.auth.Subject.Principal.
7. UserDetailsService -> loadUserByUsername()
8. UserDetailsManager extends UserDetailsService
9. Add the ldap dependency.
10. UserDetailsManager implementations. 
    1. InMemoryUserDetailsManager 
    2. JdbcUserDetailsManager -> Connection with database
    3. LdapUserDetailsManager ->
11. Setup the database.
    1. Note for free mysql database.
       1. https://www.freemysqlhosting.net/
       2. https://sqlectron.github.io/
    2. https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/jdbc.html
    3. https://stackoverflow.com/questions/24174884/spring-security-jdbc-authentication-default-schema-error-when-using-postgresql
       1. create database spring-security
       2. Execute the below script 
       ```roomsql
       DROP TABLE IF EXISTS users;
       DROP TABLE IF EXISTS authorities;
       
       CREATE TABLE users (
       id    SERIAL,
       username VARCHAR(50)  NOT NULL,
       password VARCHAR(100) NOT NULL,
       enabled  INT      NOT NULL DEFAULT 1,
       PRIMARY KEY (username)
       );
       
       CREATE TABLE authorities (
       id    SERIAL,
       username  VARCHAR(50) NOT NULL,
       authority VARCHAR(50) NOT NULL,
       FOREIGN KEY (username) 
       REFERENCES users (username));
       ```
       3. Insert data in the above tables
       ```roomsql
          INSERT  INTO users (username, password, enabled) values ('user','pass',1);
          INSERT  INTO authorities (username, authority)values ('user', 'ROLE_USER');
          INSERT  INTO users (username, password, enabled) values ('happy','12345',1);
          INSERT  INTO authorities (username, authority)values ('user', 'write');
       ```
12. Use custom tables and not the spring secutity tables.
    1. Create the table
       ```roomsql
        CREATE TABLE customer (
        id SERIAL,
        email varchar(45) NOT null unique,
        pwd varchar(45) NOT NULL,
        role varchar(45) NOT NULL,
        PRIMARY KEY (id)
        );
        INSERT INTO customer (email, pwd, role)
        VALUES ('johndoe@example.com', '54321', 'admin')
       ```
    2. Instead of using the user and authority table we have created our custom table for secrurity.
    3. Add a SpringSecurityUserDetails which implements UserDetailsService
    4. Also need to add an entity (i.e Customer entity)
    5. Also need to add a repository. (i.e CustomerRepository )
    6. This won't work unless and untill you comment the below bean
       ```text
       @Bean
       public UserDetailsService userDetailsService(DataSource dataSource){
       return new JdbcUserDetailsManager(dataSource);
       } 
       ```
13. Add login controller to register new user.
    1. Add the endpoint on config to permitAll().
    2. It won't work as by default the csrf is enable().
    3. It won't work again as we need to update the @Id field of Entity.
       1. @GeneratedValue(strategy = GenerationType.IDENTITY)
    4. 

## Section 4
1. Storing the password in a plain text inside a storage system like DB will have integrity & confidentiality issues. So this is not recommended approach for Production.
2. What is Encoding vs Encryption vs Hashing 
   ![Internal Flow](img_5.png)
3. Encoding :- transform one from to another and can be decoded easily.
4. Encryption :- transform one from to another with help of the secrete and can decrypted with secrete. 
5. Hashing :- A new hash for same string but the hash value would be same even if the hash string is different.
6. ![Internal Flow](img_6.png)
7. Default NoOpPasswordEncoder encoder is used which is not recommended in production.
8. This PasswordEncoder is not secure. Instead use an adaptive one way function like BCryptPasswordEncoder, Pbkdf2PasswordEncoder, or SCryptPasswordEncoder. Even better use DelegatingPasswordEncoder which supports password upgrades.
9. https://bcrypt-generator.com/ to test the BCryptPasswordEncoder.
10. How is password validated. 
    ![Internal Flow](img_7.png) 
11. Details of PasswordEncoder.
    ![Internal Flow](img_8.png)
12. Use the below for production.
    BCryptPasswordEncoder :- Clients can optionally supply a "version" ($2a, $2b, $2y) and a "strength" (a.k.a. log rounds in BCrypt) and a SecureRandom instance. The larger the strength parameter the more work will have to be done (exponentially) to hash the passwords. The default value is 10.
    SCryptPasswordEncoder :- Clients can optionally supply a cpu cost parameter, a memory cost parameter and a parallelization parameter.
    Argon2PasswordEncoder :- Clients can optionally supply the length of the salt to use, the length of the generated hash, a cpu cost parameter, a memory cost parameter and a parallelization parameter.
13. To Implement BCryptPasswordEncoder
    1. Update the passwordEncoder bean to return new BCryptPasswordEncoder();
    2. Update the password when it is stored in database.
    3. There is a pattern in BCrypt $2a $2y $2b this are the version.
14. org.springframework.security.crypto has all the password encoders.

## Section 5
1. Understanding authentication provider and Implement it.
   ![Internal Flow](img_9.png)
2. Details of authentication providers.
   ![Internal Flow](img_10.png)
3. Implementation of custom AuthenticationProvider.
   1. Add a class which implements the AuthenticationProvider.
   2. Remove the class which implements the UserDetails.
   3. It will clear the credentials after the authentication is successfull but set the isAuthenticated to be true.
4. Sequence flow.
   ![Internal Flow](img_11.png)

## Section 6
1. Understanding CSRF(Cross site request forgery) and CORS(Cross origin resource sharing).
2. Clone the angular app.
3. Create the database.
   1. ```roomsql
      create database eazybank;
      use eazybank;
      
      drop table users;
      drop table authorities;
      drop table customer;
      
      CREATE TABLE `customer` (
      `customer_id` int NOT NULL AUTO_INCREMENT,
      `name` varchar(100) NOT NULL,
      `email` varchar(100) NOT NULL,
      `mobile_number` varchar(20) NOT NULL,
      `pwd` varchar(500) NOT NULL,
      `role` varchar(100) NOT NULL,
      `create_dt` date DEFAULT NULL,
      PRIMARY KEY (`customer_id`)
      );
      
      INSERT INTO `customer` (`name`,`email`,`mobile_number`, `pwd`, `role`,`create_dt`)
      VALUES ('Happy','happy@example.com','9876548337', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'admin',CURDATE());
      
      CREATE TABLE `accounts` (
      `customer_id` int NOT NULL,
      `account_number` int NOT NULL,
      `account_type` varchar(100) NOT NULL,
      `branch_address` varchar(200) NOT NULL,
      `create_dt` date DEFAULT NULL,
      PRIMARY KEY (`account_number`),
      KEY `customer_id` (`customer_id`),
      CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
      );
      
      INSERT INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`, `create_dt`)
      VALUES (1, 186576453434, 'Savings', '123 Main Street, New York', CURDATE());
      
      CREATE TABLE `account_transactions` (
      `transaction_id` varchar(200) NOT NULL,
      `account_number` int NOT NULL,
      `customer_id` int NOT NULL,
      `transaction_dt` date NOT NULL,
      `transaction_summary` varchar(200) NOT NULL,
      `transaction_type` varchar(100) NOT NULL,
      `transaction_amt` int NOT NULL,
      `closing_balance` int NOT NULL,
      `create_dt` date DEFAULT NULL,
      PRIMARY KEY (`transaction_id`),
      KEY `customer_id` (`customer_id`),
      KEY `account_number` (`account_number`),
      CONSTRAINT `accounts_ibfk_2` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`account_number`) ON DELETE CASCADE,
      CONSTRAINT `acct_user_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE CASCADE
      );
      
      INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
      `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-7, 'Coffee Shop', 'Withdrawal', 30,34500,CURDATE()-7);
      
      INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
      `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-6, 'Uber', 'Withdrawal', 100,34400,CURDATE()-6);
      
      INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
      `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-5, 'Self Deposit', 'Deposit', 500,34900,CURDATE()-5);
      
      INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
      `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-4, 'Ebay', 'Withdrawal', 600,34300,CURDATE()-4);
      
      INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
      `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-2, 'OnlineTransfer', 'Deposit', 700,35000,CURDATE()-2);
      
      INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
      `closing_balance`, `create_dt`)  VALUES (UUID(), 186576453434, 1, CURDATE()-1, 'Amazon.com', 'Withdrawal', 100,34900,CURDATE()-1);

      ```

# Reading and explorations.
1. Do read about JSESSIONID how and when it gets created and destroyed, what is the significance when to use it.
2. Read about servlets and filters.
3. Read about spring security filters (approx 25 filters). 
4. What are the default authorities.
5. 

# Links
1. https://www.baeldung.com/spring-security-registered-filters#:~:text=Important%20Spring%20Security%20Filters&text=AnonymousAuthenticationFilter%3A%20when%20there's%20no%20authentication,ExceptionTranslationFilter%3A%20catch%20Spring%20Security%20exceptions
2. https://stackoverflow.com/questions/10140515/spring-security-where-can-i-find-the-list-of-all-security-filters-registered-w
3. https://docs.spring.io/spring-security/site/docs/3.1.4.RELEASE/reference/security-filter-chain.html
4. https://www.javadevjournal.com/spring-security/spring-security-filters/
5. https://ckinan.com/blog/spring-security-filter-chain/
6. https://visweshwar.medium.com/debugging-a-custom-spring-security-filterchain-3a2ea3f5ee38
7. https://docs.spring.io/spring-security/site/docs/5.2.11.RELEASE/reference/html/authorization.html
8. 