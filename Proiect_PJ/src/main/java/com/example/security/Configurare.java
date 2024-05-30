package com.example.security;

import com.example.services.Service_Utilizator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity //activeaza securitatea
public class Configurare {

    private final Service_Utilizator serviceUtilizator;

    @Autowired
    public Configurare(Service_Utilizator serviceUtilizator) {
        this.serviceUtilizator = serviceUtilizator;
    }

    //se ocupa de autentificare
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //reguli de autorizare
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/login", "/signup").permitAll() //pt toti
                        .requestMatchers("/homepage/add-car", "/homepage/edit-car/**", "/homepage/editor/**").hasRole("EDITOR") //pt editor
                        .requestMatchers("/homepage/editor").hasRole("EDITOR") //pt editor
                        .requestMatchers("/homepage/user").hasRole("USER") //pt user
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler()) //daca are succes logarea userul e dus la o pag in fct de rolul lui
                        .failureUrl("/login?error=true") //daca nu are succes logarea se duce la pagina asta
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                )
                .csrf(csrf -> csrf //configureaza protectia csrf
                        .ignoringRequestMatchers("/login", "/signup")
                );
        return http.build();
    }

    //PENTRU LOGARE CU SUCCES SI REDIRECTIONARE PE ROL
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    public static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            // te trimite pe o pag in functie de rol
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                response.sendRedirect("/homepage/user");
            } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EDITOR"))) {
                response.sendRedirect("/homepage/editor");
            } else {
                response.sendRedirect("/login?error=true");
            }
        }
    }

    //BCrypt ne poate compara parolele fara a mai fi nevoie sa le decriptam
    //doar compara stringurile intre ele

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception { //foloseste BCrypt pentru a verifica parolele la logare
        auth.userDetailsService(serviceUtilizator).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //creaza un obiect de tip BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }


}


