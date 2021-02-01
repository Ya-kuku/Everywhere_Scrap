package com.example.backend.config;

// import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry.addMapping("/**")
    //     .allowedOrigins("http://localhost:8081")
    //     .allowedMethods(
    //         HttpMethod.GET.name(),
    //         HttpMethod.HEAD.name(),
    //         HttpMethod.POST.name(),
    //         HttpMethod.PUT.name(),
    //         HttpMethod.DELETE.name());
    // }
}