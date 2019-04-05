package pl.ttwug.security.ttwug.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.ttwug.security.ttwug.filter.JWTLoginPasswordAuthFilter;
import pl.ttwug.security.ttwug.filter.JWTRolesFilter;
import pl.ttwug.security.ttwug.service.UserDetailsServiceImpl;

import static pl.ttwug.security.ttwug.config.RolesConstants.ADMINISTRATOR_ROLE;
import static pl.ttwug.security.ttwug.config.RolesConstants.USER_ROLE;
import static pl.ttwug.security.ttwug.config.UrlConstants.*;


@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(REGISTER_URL).permitAll()
                .antMatchers(AUTHENTICATED_USER_URL).hasRole(USER_ROLE)
                .antMatchers(ADMINISTRATOR_USER_URL).hasRole(ADMINISTRATOR_ROLE)
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTLoginPasswordAuthFilter(authenticationManager()))
                .addFilter(new JWTRolesFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}