package com.betrybe.agrix.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Javadoc.
 */
@Configuration

@EnableWebSecurity

public class SecurityFilterConfigutarion {
  private final SecurityFilter securityFilter;

  @Autowired

  public SecurityFilterConfigutarion(SecurityFilter securityFilter) {
    this.securityFilter = securityFilter;
  }

  /**
   * Javadoc.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/persons").permitAll()
            .anyRequest()
            .authenticated()
        )
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
