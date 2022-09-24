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
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/user/new").permitAll()
                .antMatchers("/user/info/**").permitAll()
                .antMatchers("/notifications/**").permitAll()
                .antMatchers("/my_posts/**").hasAuthority("Farmer")
                .antMatchers("/my_products/**").hasAuthority("Farmer")
                .antMatchers("/my_connections/**").permitAll()
                .antMatchers("/customers/**").hasAuthority("Farmer")
                .antMatchers("/farmers/**").hasAuthority("Buyer")
                .antMatchers("/timeline/**").hasAuthority("Buyer")
                .antMatchers("/").hasAnyAuthority("Farmer","Buyer")
                .and()
                .formLogin()
                .loginPage("/login")
                    .usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                .logout().permitAll();
        http.headers().frameOptions().sameOrigin();

        http.authenticationProvider(authenticationProvider());

        return http.build();

    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().antMatchers("/images/**", "/js/**", "/fonts/**", "/css/**"));
    }
}
