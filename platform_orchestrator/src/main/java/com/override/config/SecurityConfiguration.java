package com.override.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;
    @Autowired
    private PermitAllURLs permitAllUrls;
    @Autowired
    private TokenFilter tokenFilter;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private LessonFilter lessonFilter;
    @Autowired
    private ReportFilter reportFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(permitAllUrls.getPermitAllUrls()).permitAll()
                .antMatchers("/restore").permitAll()
                .antMatchers("/getCode/**").permitAll()
                .antMatchers("/notification/**").permitAll()
                .antMatchers("/codePhone-password").permitAll()
               // .antMatchers("/admin/**", "/join/request/**").hasRole("ADMIN")
                .antMatchers("/**").hasAnyRole("USER", "ADMIN", "GRADUATE")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(lessonFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(reportFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(tokenFilter, JwtFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}