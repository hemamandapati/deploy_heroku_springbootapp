package com.accenture.edj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import jakarta.annotation.security.PermitAll;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


// @Bean
// public PasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();
// }

// @Bean
// public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//     manager.createUser(User.withUsername("user")
//             .password(passwordEncoder.encode("password"))  
//             .roles("USER")                             
//             .build());
//     manager.createUser(User.withUsername("admin")
//             .password(passwordEncoder.encode("admin"))    
//             .roles("ADMIN")                           
//             .build());
//     return manager;
// }
// 
//  @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeRequests()
//                 .requestMatchers("/getDetails").hasRole("ADMIN")
//                 .requestMatchers("/getDetailsById/**").hasRole("USER")
//              .requestMatchers("/**").permitAll()
//                 .anyRequest().authenticated();
//         return http.build();
//     }
 
// @Bean
// public PasswordEncoder passwordEncoder() {
//     return PasswordEncoderFactories.createDelegatingPasswordEncoder();
// }
 
 @Bean
 public UserDetailsService userDetailsService() {
  UserDetails normalUser = User.withDefaultPasswordEncoder()
    .username("user")
    .password("user")
    .roles("USER")
    .build();
 
  UserDetails adminUser = User.withDefaultPasswordEncoder()
    .username("admin")
    .password("admin")
    .roles("ADMIN")
    .build();
  
  return new InMemoryUserDetailsManager(normalUser, adminUser);
 }
 
 
 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
  return httpSecurity
    .authorizeHttpRequests(registry -> {
     
     registry.requestMatchers("/getDetailsById/**").hasRole("USER");
     registry.requestMatchers("/addEmp").hasRole("ADMIN");
     registry.requestMatchers("/getDetails").hasRole("USER");
     registry.anyRequest().authenticated();
    })
//    .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
    .httpBasic()
    .and()
    .formLogin().disable()
    .build();
  
 }
   
 
 //added to check changes
}