package com.example.script.config;

import com.example.script.model.User;
import com.example.script.service.UserService;
import com.example.script.service.UserServiceImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private UserServiceImpl userService;

  /*@Bean
  public ServletRegistrationBean h2servletRegistration() {
    ServletRegistrationBean registration = new ServletRegistrationBean(new WebdavServlet());
    registration.addUrlMappings("/h2/*");
    return registration;
  }*/

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
  private RESTAuthenticationEntryPoint authenticationEntryPoint;
  @Autowired
  private RESTAuthenticationFailureHandler authenticationFailureHandler;
  @Autowired
  private RESTAuthenticationSuccessHandler authenticationSuccessHandler;



  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }



  @Override
  protected void configure(HttpSecurity http) throws Exception {

    LOGGER.info("configure");

    User adminUser = userService.getById(1L,User.class);

    if (adminUser == null) {

      User user = new User();
      user.setUserName("admin");
      user.setPassword(encoder().encode("1"));
      userService.insert(user);

    }

    List<User> userList = userService.getAll(User.class);

    for (User userItem : userList) {
      LOGGER.info("userItem : " + userItem.getUsername());
    }

    /* http.authorizeRequests().antMatchers("/rest/**").authenticated();
    http.csrf().disable();
    http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    http.formLogin().successHandler(authenticationSuccessHandler);
    http.formLogin().failureHandler(authenticationFailureHandler);*/

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
    /*http.csrf().disable();
    http.headers().frameOptions().disable();*/


   /* http
        .antMatcher("/").authorizeRequests().and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .successHandler(loginSuccessHandler())
        .failureHandler(loginFailureHandler())
        .and()
        .logout()
        .permitAll()
        .logoutSuccessUrl("/login")
        .and()
        .csrf();/


    /*http.csrf().disable();
    http.headers().frameOptions().disable();*/
  }


  public AuthenticationSuccessHandler loginSuccessHandler() {
    LOGGER.info("loginSuccessHandler");

    return (request, response, authentication) -> response.sendRedirect("/index");
  }

  public AuthenticationFailureHandler loginFailureHandler() {
    LOGGER.debug("loginFailureHandler");

    return (request, response, exception) -> {
    };
  }
}
