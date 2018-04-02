package com.example.script.config;

import com.example.script.model.User;
import com.example.script.service.UserServiceImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class MultiHttpSecurityCustomConfig {

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider
        = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(encoder());
    return authProvider;
  }

  @Autowired
  private UserServiceImpl userService;

  @Configuration
  @Order(1)
  public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/api/**").authorizeRequests().anyRequest().hasRole("ADMIN").and().httpBasic();
    }
  }

  @Configuration
  public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    @Override
    protected void configure(HttpSecurity http) throws Exception {

      LOGGER.info("FormLoginWebSecurityConfigurerAdapter");
      http
          .authorizeRequests()
          .antMatchers("/home").permitAll()
          .antMatchers("/h2","/rest/**").hasRole("ADMIN")
          .anyRequest().authenticated()
          .and()
          .formLogin()
          .loginPage("/login")
          .permitAll()
          .successHandler(loginSuccessHandler())
          .failureHandler(loginFailureHandler())
          .and()
          .logout()
          .permitAll().logoutSuccessUrl("/login")
          .and()
          .csrf()
          // Allow unsecured requests to H2 console
          .ignoringAntMatchers("/h2", "/h2/**").and().headers().frameOptions().disable();
    }


    public AuthenticationSuccessHandler loginSuccessHandler() {
      LOGGER.info("loginSuccessHandler");

      return (request, response, authentication) -> response.sendRedirect("/index");
    }

    public AuthenticationFailureHandler loginFailureHandler() {
      LOGGER.debug("loginFailureHandler");

      return (request, response, exception) -> response.sendRedirect("/login");
    }

  }

}
