package elements.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import elements.service.UsersService;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.POST, "/anime/**").hasRole("ADMIN")
                .pathMatchers(HttpMethod.GET, "/anime/**").hasRole("USER")
                .pathMatchers("/webjars/**", "/v3/api-docs", "/swagger-ui.html").permitAll()
                .anyExchange().authenticated()
                .and().formLogin()
                .and().httpBasic()
                .and().build();

    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(UsersService user){
        UserDetailsRepositoryReactiveAuthenticationManager auth = new UserDetailsRepositoryReactiveAuthenticationManager(user);
        auth.setPasswordEncoder(new BCryptPasswordEncoder());
        return auth;
    }

}
