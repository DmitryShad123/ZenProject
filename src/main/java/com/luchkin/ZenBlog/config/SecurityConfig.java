
package com.luchkin.ZenBlog.config;

import com.luchkin.ZenBlog.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/adminconfig").hasRole("ADMIN")
                .antMatchers("/news/add").hasRole("ADMIN")
                .antMatchers("/article/add").hasAnyRole("MODER", "ADMIN")
                .antMatchers("/article/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/news/**").permitAll()
                .antMatchers("/account").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .loginProcessingUrl("/perform-login")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/home");
    }
}

