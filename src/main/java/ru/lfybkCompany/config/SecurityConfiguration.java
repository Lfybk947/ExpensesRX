package ru.lfybkCompany.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.lfybkCompany.database.entity.Role;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/login", "/users", "/users/registration", "/v3/api-docs/", "/swagger-ui/")
                            .permitAll()
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(login-> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/expenses")
                        .permitAll())
                .logout(logout-> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .deleteCookies("JSESSIONID"))
//                .sessionManagement(session->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ;
        return http.build();
    }

}
