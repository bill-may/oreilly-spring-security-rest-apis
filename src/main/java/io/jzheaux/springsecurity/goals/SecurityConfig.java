package io.jzheaux.springsecurity.goals;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new UserRepositoryUserDetailsService(userRepository);
  }
}
