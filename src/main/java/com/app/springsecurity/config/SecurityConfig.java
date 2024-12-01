package com.app.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/account", "/balance", "/loans", "/cards").authenticated()
                        .requestMatchers("/notices", "/contact", "/error").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // store users in memory, with passwords stored in plain text
        // {noop} --> no operation
        UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();

        // store users in memory, with passwords stored in bcrypt encoding
        // {bcrypt} --> bcrypt encoding
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$P1JG7Yp7JjLtpJBJ6aI0GujrYG/tqM2bTCJKTWerJLCKkBeD2FB.u")
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

/**
 * <ul>
 *   <li>Deny all requests</li>
 *   <pre>{@code
 *   http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
 *   }</pre>
 *
 *   <li>Permit all requests</li>
 *   <pre>{@code
 *   http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
 *   }</pre>
 *
 *   <li>Authenticate all requests</li>
 *   <pre>{@code
 *   http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
 *   }</pre>
 *
 *   <li>Disable form login</li>
 *   <pre>{@code
 *   http.formLogin(AbstractHttpConfigurer::disable);
 *   }</pre>
 *
 *   <li>Disable http basic</li>
 *   <pre>{@code
 *   http.httpBasic(AbstractHttpConfigurer::disable);
 *   }</pre>
 * </ul>
 */
