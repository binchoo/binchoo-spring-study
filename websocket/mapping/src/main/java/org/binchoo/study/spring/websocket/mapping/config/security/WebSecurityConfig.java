package org.binchoo.study.spring.websocket.mapping.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth.inMemoryAuthentication()
                    .withUser("user").password("{noop}user").roles("USER");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().csrfTokenRepository(tokenRepository())
            .and().authorizeRequests()
                .antMatchers("/main-logined").authenticated()
                .antMatchers("/*").permitAll()
            .and().formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/loginform")
                .failureUrl("/loginform")
                .defaultSuccessUrl("/main-logined")
                .permitAll()
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/main");
    }

    @Bean
    public CsrfTokenRepository tokenRepository() {
        HttpSessionCsrfTokenRepository tokenRepository = new HttpSessionCsrfTokenRepository();
        tokenRepository.setParameterName("_csrf");
        tokenRepository.setHeaderName("X-CSRF-TOKEN");
        tokenRepository.setSessionAttributeName("CSRF_TOKEN");
        return tokenRepository;
    }
}
