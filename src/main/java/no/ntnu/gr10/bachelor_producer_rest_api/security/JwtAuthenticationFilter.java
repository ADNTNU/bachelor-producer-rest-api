package no.ntnu.gr10.bachelor_producer_rest_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import no.ntnu.gr10.bachelor_producer_rest_api.dto.ErrorResponse;
import no.ntnu.gr10.bachelor_producer_rest_api.dto.JwtPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT authentication filter.
 * <p>
 * Checks for the presence of a JWT token in the request header, validates it,
 * and sets the authentication in the security context.
 * </p>
 *
 * @author Daniel Neset
 * @version 11.04.2025
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final ObjectMapper objectMapper = new ObjectMapper();
  private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  public JwtAuthenticationFilter(JwtUtil jwtUtil){
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest httpServletRequest,
          HttpServletResponse httpServletResponse,
          FilterChain filterChain
  )throws IOException, ServletException {
    try {
      String jwtToken = getJwtFromRequest(httpServletRequest);

      if (jwtToken != null) {
        JwtPayload jwtPayload = jwtUtil.verifyTokenAndGetPayload(jwtToken);

        registerUserAsAuthenticated(httpServletRequest, jwtPayload);
      }
      filterChain.doFilter(httpServletRequest, httpServletResponse);

    } catch (JwtException | IllegalArgumentException ex) {
      writeJsonError(httpServletResponse, HttpStatus.UNAUTHORIZED, "Invalid JWT token");
    }
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    final String BEARER_PREFIX = "Bearer ";
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX.length()); // Remove "Bearer " prefix
    }
    return null;
  }

  private static void registerUserAsAuthenticated(HttpServletRequest request, JwtPayload jwtPayload) {
    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(jwtPayload.companyId(), null, jwtPayload.authorities());
    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
  }

  private void writeJsonError(HttpServletResponse response, HttpStatus status, String message) {
    try {
      response.setContentType("application/json");
      response.setStatus(status.value());

      ErrorResponse errorResponse = new ErrorResponse(message);
      String json = objectMapper.writeValueAsString(errorResponse);

      response.getWriter().write(json);
    } catch (Exception e) {
      log.error("Error writing JSON error response: {}. Original error: {}", e.getMessage(), message);
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      try {
        response.getWriter().write("Internal server error");
      } catch (IOException ioException) {
        log.error("Error writing internal server error response: {}", ioException.getMessage());
      }
    }
  }

}
