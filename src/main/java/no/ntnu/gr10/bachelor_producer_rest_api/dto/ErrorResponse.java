package no.ntnu.gr10.bachelor_producer_rest_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Response with an error message")
public record ErrorResponse(
        @Schema(description = "Error message", example = "Jwt token is invalid")
        String message
) {}
