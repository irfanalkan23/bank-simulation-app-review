package com.cydeo.config;

import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        .requestMatchers("/accounts/**").hasAuthority("Admin")     //"Admin" as in the data.sql
                        .requestMatchers("/transaction/**").hasAnyAuthority("Admin","Cashier")
                        .requestMatchers("/","/login").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")
//                        .defaultSuccessUrl("/index")    //we will change to custom url later
                        //complete success handler. admin: /index , cashier: /make-transfer
                        .successHandler(authSuccessHandler)
                        .failureForwardUrl("/login?failure=true")
                        .permitAll()
                )
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")     //direct to "/login" page upon successful logout
                )
                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .tokenValiditySeconds(300)
                        .key("bankapp")
                        //if you want me to remember, provide me where I'm gonna find this user = securityService.
                        //looking for a service implementing UserDetailsService
                        .userDetailsService(securityService)
                )
                .build();

    }
}
