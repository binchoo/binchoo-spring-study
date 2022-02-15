package org.binchoo.study.spring.aws.s3upload.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.config
 * fileName : SecurityConfig
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth.inMemoryAuthentication()
                    .withUser("binchoo").password("{noop}1234").roles("USER");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().csrfTokenRepository(tokenRepository())
        .and().authorizeRequests()
            .antMatchers("/index", "/s3*").authenticated()
        .and().formLogin()
            .loginPage("/login").permitAll()
            .loginProcessingUrl("/doLogin").permitAll()
            .defaultSuccessUrl("/index")
            .failureUrl("/")
        .and().logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/");
    }

    @Bean
    public CsrfTokenRepository tokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }
}
