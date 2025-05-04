package no.ntnu.gr10.bachelor_producer_rest_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for setting up authentication and authorization.
 * This class configures the security filter chain, authentication manager, and password encoder.
 *
 * @author Daniel Neset
 * @version 20.04.2025
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFIlter;

  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFIlter){
    this.jwtAuthenticationFIlter = jwtAuthenticationFIlter;
  }

  @Bean
  protected SecurityFilterChain configure
          (HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/fisheryActivities/**").hasRole(Scope.ADMIN.getAuthority())
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                            ).permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFIlter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }
}