package com.fst.elearning.config;

import com.fst.elearning.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
                .disable()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/uploads/**",
                                 "/webjars/**", "/favicon.ico").permitAll()
                .requestMatchers("/", "/home", "/login", "/register", "/error").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                // Admin-only
                .requestMatchers("/api/**").hasRole("ADMIN")
                .requestMatchers("/ui/utilisateurs/**").hasRole("ADMIN")
                .requestMatchers("/ui/paiements", "/ui/paiements/").hasRole("ADMIN")
                .requestMatchers("/ui/paiements/*/valider",
                                 "/ui/paiements/*/refuser",
                                 "/ui/paiements/*/rembourser").hasRole("ADMIN")
                // Formateur + Admin
                .requestMatchers("/ui/cours/new", "/ui/cours/*/edit",
                                 "/ui/cours/*/delete").hasAnyRole("ADMIN", "FORMATEUR")
                .requestMatchers("/ui/modules/**", "/ui/lecons/**").hasAnyRole("ADMIN", "FORMATEUR")
                // Authenticated users
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
