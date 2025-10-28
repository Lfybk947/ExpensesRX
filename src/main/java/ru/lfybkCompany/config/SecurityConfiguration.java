package ru.lfybkCompany.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import ru.lfybkCompany.database.entity.Role;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/login", "/users/registration", "/v3/api-docs/", "/swagger-ui/")
                            .permitAll()
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.getAuthority())
                        .requestMatchers(antMatcher("/users/{\\d}/delete")).hasAnyAuthority(
                            Role.ADMIN.getAuthority())
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
                                ;
        return http.build();

    }

}
