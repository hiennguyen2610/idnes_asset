package com.example.asset_management_idnes.config;

import com.example.asset_management_idnes.security.AuthTokenFilter;
import com.example.asset_management_idnes.security.AuthenticationEntryPointJwt;
import com.example.asset_management_idnes.security.CustomUserDetailsService;
import com.example.asset_management_idnes.statics.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    CustomUserDetailsService userDetailsService;

    AuthenticationEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/v1/authentication/login", "/api/v1/authentication/signup").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/v1/authentication/refresh-token", "/api/v1/authentication/logout").authenticated()
//                .antMatchers(HttpMethod.POST, "/api/v1/users").hasAnyAuthority(Roles.ADMIN.toString())
//                .antMatchers(HttpMethod.GET, "/api/v1/users/**", "/api/v1/public/**").hasAnyAuthority(Roles.ADMIN.toString())
//                .antMatchers(HttpMethod.POST, "/api/v1/users/**", "/api/v1/public/**").hasAnyAuthority(Roles.ADMIN.toString())
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/admin/login")
//                .permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .headers().frameOptions().sameOrigin();
//        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOrigin("http://localhost:4200"); // Điều chỉnh theo địa chỉ frontend của bạn
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/v1/authentication/login", "/api/v1/authentication/signup").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/v1/authentication/refresh-token", "/api/v1/authentication/logout").authenticated()
//                .antMatchers(HttpMethod.POST, "/api/v1/users").hasAnyAuthority(Roles.ADMIN.toString())
//                .antMatchers(HttpMethod.GET, "/api/v1/users/**", "/api/v1/public/**").hasAnyAuthority(Roles.ADMIN.toString())
//                .antMatchers(HttpMethod.POST, "/api/v1/users/**", "/api/v1/public/**").hasAnyAuthority(Roles.ADMIN.toString())
//                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/admin/login")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .headers().frameOptions().sameOrigin();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // Thay thế bằng origin của ứng dụng Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}