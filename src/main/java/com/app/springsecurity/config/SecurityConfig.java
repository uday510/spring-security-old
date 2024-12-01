package com.app.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

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


    // bean to store users in memory (stores in map)
    @Bean
    public UserDetailsService userDetailsService() {

        /**
         * Passwords are stored in plain text
         * {noop} --> no operation
         */
        UserDetails user = User.withUsername("user").password("{noop}User@123454321").authorities("read").build();

        /**
         * Passwords are stored in encrypted form using bcrypt
         * {bcrypt} --> bcrypt encryption
         */
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$DyzXabyI6S/tJ4Vu7FmOtORj4ztlDCeUdWMJSGbcvS0/KmkRMPqQ6")
                .authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     *
     * Passwords are stored in encrypted form using bcrypt
     * {bcrypt} --> bcrypt encryption
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Check if the password is compromised
     * @return CompromisedPasswordChecker
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
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
