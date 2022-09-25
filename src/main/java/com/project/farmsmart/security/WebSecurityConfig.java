package com.project.farmsmart.security;

import com.project.farmsmart.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/user/new").permitAll()
                .antMatchers("/user/info/**").hasAnyAuthority("Farmer", "Buyer")
                .antMatchers("/notifications/**").hasAnyAuthority("Farmer", "Buyer")
                .antMatchers("/my_posts/**").hasAuthority("Farmer")
                .antMatchers("/my_products/**").hasAuthority("Farmer")
                .antMatchers("/my_connections/**").hasAnyAuthority("Farmer", "Buyer")
                .antMatchers("/customers/**").hasAuthority("Farmer")
                .antMatchers("/farmers/**").hasAuthority("Buyer")
                .antMatchers("/timeline/**").hasAuthority("Buyer")
                .antMatchers("/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .failureForwardUrl("/login")
                    .permitAll()
                .and()
                .logout().permitAll();
        http.headers().frameOptions().sameOrigin();

        http.authenticationProvider(authenticationProvider());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().antMatchers("/images/**", "/js/**", "/fonts/**", "/css/**"));
    }
}
