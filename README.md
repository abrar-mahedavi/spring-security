# spring-security
A spring project to try spring security with JWT, OAUTH2 

# Build
```shell
mvn clean install       # A bit slower as copies the jar to .m2 folder
mvn clean package       # A bit faster as skips the copy part
```

# Run
```shell
mvn spring-boot:run     # Run the project 
java -jar target/security-basic-0.0.1-SNAPSHOT.jar
```

# Test
```shell
mvn test 
```