package org.przemekrutkowski.exammarksapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.przemekrutkowski.exammarksapp.enums.SecurityRole.*;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/js/**")
                .permitAll()
                .antMatchers("/user_administration", "/student_list").hasRole(ROLE_ADMIN.getShortName())
                .antMatchers("/marks_list").hasAnyRole(ROLE_USER.getShortName(), ROLE_ADMIN.getShortName())
                .antMatchers("/*").hasAnyRole(ROLE_GUEST.getShortName(), ROLE_USER.getShortName(), ROLE_ADMIN.getShortName())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
