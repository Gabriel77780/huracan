package com.sc504.huracan.security;

import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

      SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
          .withProcedureName("get_user_details");

      Map<String, Object> result = jdbcCall.execute(Map.of("P_USERNAME", username));

      String password = (String) result.get("P_PASSWORD");
      String role = (String) result.get("P_ROLE");
      Integer id = result.get("p_id") != null ? ((Integer) result.get("p_id")) : null;

      if (password == null || role == null) {
        throw new UsernameNotFoundException("User not found: " + username);
      }

      List<GrantedAuthority> authorities = List.of(() -> role);

      return new SystemUserDetails(id, username,
          passwordEncoder().encode(password), authorities);
    };
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
   http
       .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
           .requestMatchers("/login").permitAll()
           .requestMatchers("/images/**", "/css/**", "/js/**", "/favicon.ico").permitAll()
           .anyRequest().authenticated()
       )
       .userDetailsService(userDetailsService())
    .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
        .loginPage("/login")
            .loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/home", true)
            .failureUrl("/login?error=true")
        .permitAll())
       .logout(logout -> logout
           .logoutUrl("/logout")
           .logoutSuccessUrl("/login.html?logout=true")
           .invalidateHttpSession(true)
           .deleteCookies("JSESSIONID")
       );
    return http.build();
  }

}
