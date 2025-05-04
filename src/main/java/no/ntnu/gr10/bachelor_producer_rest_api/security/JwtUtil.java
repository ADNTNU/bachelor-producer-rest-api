package no.ntnu.gr10.bachelor_producer_rest_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import no.ntnu.gr10.bachelor_producer_rest_api.dto.JwtPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

  @Value("${jwt.secret_key}")
  private String secretKey;
  private static final String COMPANY_ID_CLAIM = "companyId";
  private static final String SCOPES_CLAIM = "scopes";


  public JwtPayload verifyTokenAndGetPayload(String token)
          throws JwtException, IllegalArgumentException {
    Claims claims = verifyTokenAndGetClaims(token);

    Integer companyId = claims.get(COMPANY_ID_CLAIM, Integer.class);

    Object rawScopes = claims.get(SCOPES_CLAIM);

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    if (rawScopes instanceof String) {
      for (String scope : ((String) rawScopes).split("\\s+")) {
        String role = scope.startsWith("ROLE_") ? scope : "ROLE_" + scope;
        authorities.add(new SimpleGrantedAuthority(role));
      }
    }
    else if (rawScopes instanceof List<?>) {
      for (Object item : (List<?>) rawScopes) {
        String authority = item instanceof Map<?,?> map && map.containsKey("authority")
                ? map.get("authority").toString()
                : item.toString();
        if (!authority.startsWith("ROLE_")) {
          authority = "ROLE_" + authority;
        }
        authorities.add(new SimpleGrantedAuthority(authority));
      }
    }

     return new JwtPayload(companyId, Collections.unmodifiableList(authorities));
  }

  private Claims verifyTokenAndGetClaims(String token) throws JwtException, IllegalArgumentException {
    if (token == null || token.isEmpty()) {
      throw new IllegalArgumentException("Token is null or empty");
    }

    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  private SecretKey getSigningKey(){
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
  }

}
