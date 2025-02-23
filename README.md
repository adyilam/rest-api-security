# rest-api-security
Basic authentication in Java using Spring Boot!

Basic Authentication is a simple authentication mechanism that uses a username and password to access a resource. It is a standard authentication method defined in HTTP/1.0 and supported by RFC 7617.

# How Basic Authentication Works
The client (e.g., browser, Postman, cURL) sends an HTTP request to a protected resource.
The server responds with 401 Unauthorized and a WWW-Authenticate header requesting authentication.
The client sends the username and password encoded in Base64 in the Authorization header.
The server decodes the credentials, verifies them, and grants or denies access.

Steps to configure Basec Authentication using Spring Boot.

1. Add Dependencies
Include Spring Security in your `pom.xml` (if using Maven):

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

For Gradle:
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-security'
}

2. Default Basic Authentication
Once you add Spring Security, it enables Basic Authentication by default. A default user (`user`) and a randomly generated password (printed in logs on startup) will be used.

Check the logs for the password:
Using generated security password: 1234abcd-5678-efgh-ijkl-9876mnopqrst
Now, any request to your API will require authentication.

3. Custom Basic Authentication Configuration
Define a custom username and password, add these properties to `application.properties`:

spring.security.user.name=admin
spring.security.user.password=admin123

4. Custom Security Configuration
Create a security configuration class:

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class EmployeeSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChainEmployee(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/employees").permitAll()  // Public endpoints accessible to everyone
                                .requestMatchers("/h2-console/**").permitAll() // Allow H2 Console
                                .requestMatchers("/employees/**")
                                .hasRole("ADMIN")     // Restricted to HR / Manager role
                                .anyRequest().authenticated()  // All other requests need authentication
                )
                .formLogin(Customizer.withDefaults())  // Enables form-based authentication
                .httpBasic(Customizer.withDefaults());  // Enables Basic Auth

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // Disable CSRF for H2
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Allow H2 Console Frames


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Secure password encoding
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}


5. Testing Basic Authentication
Use Postman or cURL:

# Using cURL
curl -u admin:admin http://localhost:8080/your-endpoint

# Using Postman
1. Go to Authorization.
2. Select Basic Auth.
3. Enter `admin` as username and `admin` as password.
4. Send the request.

 6. Further Customization
- To secure only specific endpoints:  
  auth.requestMatchers("/employees").permitAll()  // Public endpoints accessible to everyone
      .requestMatchers("/h2-console/**").permitAll() // Allow H2 Console
      .requestMatchers("/employees/**")
      .hasRole("ADMIN")     // Restricted to HR / Manager role
      .anyRequest().authenticated()  // All other requests need authentication
  
- use BCrypt for password encoding:
   @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Secure password encoding
    }

Supportive reference Documentation
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Boot Security Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.security)

