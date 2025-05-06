package no.ntnu.gr10.bachelor_producer_rest_api.dto;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public record JwtPayload(Integer companyId, List<SimpleGrantedAuthority> authorities) {
}
