package com.laptrinhweb.thitracnghiem.Security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // @Autowired
    // private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/login", "/javascript/**", "/assets/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("EMPLOYEE")
                .requestMatchers("/student/**").hasAuthority("STUDENT")
                .requestMatchers("/teacher/**").hasAuthority("TEACHER")
                .anyRequest().authenticated()).formLogin((form) -> form
                        // .loginPage(null)
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            // Lấy danh sách các vai trò của người dùng
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            for (GrantedAuthority authority : authorities) {
                                System.out.println(authority.getAuthority());
                                // Kiểm tra vai trò và chuyển hướng tương ứng
                                if (authority.getAuthority().equals("EMPLOYEE")) {
                                    response.sendRedirect("/admin/student");
                                    return;
                                } else if (authority.getAuthority().equals("STUDENT")) {
                                    response.sendRedirect("/student/home");
                                    return;
                                } else if (authority.getAuthority().equals("TEACHER")) {
                                    response.sendRedirect("/teacher");
                                    return;
                                }
                            }
                            // Chuyển hướng mặc định nếu không phát hiện vai trò nào
                            response.sendRedirect("/login");
                        }))
                .logout((logout) -> logout.permitAll());
        // .authorizeHttpRequests((requests) -> requests
        // .requestMatchers("/", "/login","/javascript/**","/assets/**").permitAll()
        // .anyRequest().authenticated()
        // )
        // .formLogin((form) -> form
        // // .loginPage("/student/home")
        // .permitAll()
        // .defaultSuccessUrl("/student/home", true)
        // )
        // .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // @Bean
    // public UserDetailsService userDetailsService() {
    // UserDetails user =
    // User
    // .withUsername("user")
    // .password(passwordEncoder().encode("password"))
    // .roles("STUDENT")
    // .build();

    // return new InMemoryUserDetailsManager(user);
    // }
}
