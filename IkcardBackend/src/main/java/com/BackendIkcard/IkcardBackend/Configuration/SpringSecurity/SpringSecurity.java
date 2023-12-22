package com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity;

import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.AuthEntryPointJwt;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Jwt.AuthTokenFilter;
import com.BackendIkcard.IkcardBackend.Configuration.SpringSecurity.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class SpringSecurity {


    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // @Autowired
    // private AuthEntryPointJwt unauthorizedHandler;


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                // .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/admin/**").permitAll()
                .antMatchers("/cartes/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/ambassadeur/**").permitAll()
                .antMatchers("/contact/**").permitAll()
                .antMatchers("/entreprise/**").permitAll()
                .antMatchers("/contact/**").permitAll()
                .antMatchers("/import/**").permitAll()
                .antMatchers("/anonce/**").permitAll()
                .antMatchers("/connexion/**").permitAll()
                .antMatchers("/api/qrcode/**").permitAll()
                .antMatchers("/logout").permitAll()

                //.antMatchers("/swagger").permitAll()
                //.antMatchers("/swagger-ui.html").permitAll()


                .anyRequest().authenticated();

        http.formLogin();
        // http.oauth2Login();
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
