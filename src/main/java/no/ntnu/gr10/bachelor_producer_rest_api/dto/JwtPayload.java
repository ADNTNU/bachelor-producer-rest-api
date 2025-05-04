package no.ntnu.gr10.bachelor_producer_rest_api.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record JwtPayload(Integer companyId, List<GrantedAuthority> authorities) {
}
