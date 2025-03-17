package com.sc504.huracan.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final JdbcTemplate jdbcTemplate;

  public SecurityConfig(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  
  
  
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      String query = "SELECT username, password, role FROM user WHERE username = ?";
      return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
        String password = rs.getString("password");
        String role = rs.getString("role");
        return User
            .withUsername(username)
            .password(passwordEncoder().encode(password))
            .roles(role)
            .build();
      }, username);
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   http
       .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
           .requestMatchers((request) -> request.getServletPath().equals("/login"))
           .permitAll()
           .anyRequest()
           .authenticated()).userDetailsService(userDetailsService())
    .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
        .loginPage("/login")
            .loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/home", true)
            .failureUrl("/login.html?error=true")
        .permitAll())
       ;
    return http.build();
  }

}
