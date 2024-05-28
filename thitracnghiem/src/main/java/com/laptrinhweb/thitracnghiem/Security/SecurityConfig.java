package com.laptrinhweb.thitracnghiem.Security;

import java.util.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/login", "/javascript/**", "/assets/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("EMPLOYEE")
                .requestMatchers("/student/**").hasAuthority("STUDENT")
                .requestMatchers("/teacher/**").hasAuthority("TEACHER")
                .anyRequest().authenticated()).formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            // Lấy danh sách các vai trò của người dùng
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            HttpSession session = request.getSession();
                            DefaultSavedRequest savedRequest = (DefaultSavedRequest) session
                                    .getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                            String redirectUrl = null;
                            for (GrantedAuthority authority : authorities) {
                                System.out.println(authority.getAuthority());
                                // Kiểm tra vai trò và chuyển hướng tương ứng
                                if (authority.getAuthority().equals("EMPLOYEE")) {
                                    if (savedRequest != null) {
                                        redirectUrl = savedRequest.getRedirectUrl();
                                        if (!redirectUrl.contains("error")) {
                                            response.sendRedirect(redirectUrl);
                                            return;
                                        }
                                    }

                                    response.sendRedirect("/admin/student");
                                    return;
                                } else if (authority.getAuthority().equals("STUDENT")) {
                                    if (savedRequest != null) {
                                        redirectUrl = savedRequest.getRedirectUrl();
                                        response.sendRedirect(redirectUrl);
                                    } else
                                        response.sendRedirect("/student/home");
                                    return;
                                } else if (authority.getAuthority().equals("TEACHER")) {
                                    if (savedRequest != null) {
                                        redirectUrl = savedRequest.getRedirectUrl();
                                        response.sendRedirect(redirectUrl);
                                    } else
                                        response.sendRedirect("/teacher");
                                    return;
                                }
                            }
                            // Chuyển hướng mặc định nếu không phát hiện vai trò nào
                            response.sendRedirect("/login");
                        }))
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
