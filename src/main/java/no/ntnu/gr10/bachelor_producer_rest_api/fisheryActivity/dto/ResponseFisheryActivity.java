package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.FisheryActivity;

import java.time.LocalDateTime;

@Schema(name = "ResponseFisheryActivity", description = "Response DTO for fishery activity details")
public record ResponseFisheryActivity (
        @Schema(description = "Unique identifier of the activity", example = "1")
        Long id,

        @Schema(description = "Unique identifier for the company", example = "1")
        Long companyId,

        @Schema(description = "Timestamp when the activity was set up", example = "2025-04-20T01:47:10")
        LocalDateTime setupDateTime,

        @Schema(description = "Code of the tool type", example = "NETS")
        String toolTypeCode,

        @Schema(description = "Human-readable name of the tool type", example = "Nets")
        String toolTypeName,

        @Schema(description = "UUID of the specific tool used", example = "31e47253-6d8a-4060-942b-aec5ad0d4d12")
        String toolId,

        @Schema(description = "Timestamp when the tool was removed (nullable)", example = "2025-04-20T02:30:00")
        LocalDateTime removedDateTime,

        @Schema(description = "Last time this record was changed", example = "2025-04-20T02:14:26")
        LocalDateTime lastChangedDateTime,

        @Schema(description = "Latitude of the starting point", example = "58.07933")
        Double startingPointLat,

        @Schema(description = "Longitude of the starting point", example = "3.60231")
        Double startingPointLon,

        @Schema(description = "Length of the activity in meters", example = "5321.06")
        Double length,

        @Schema(description = "Well-Known Text (WKT) geometry of the path", example = "LINESTRING(3.60231 58.07933,3.61934 58.07934,...)")
        String geometry
){
  /**
   * Creates a Response DTO from the JPA entity.
   * @param entity the FisheryActivity entity
   * @return mapped ResponseFisheryActivity
   */
  public static ResponseFisheryActivity fromEntity(FisheryActivity entity) {
    return new ResponseFisheryActivity(
            entity.getId(),
            entity.getCompany().getId(),
            entity.getSetupDateTime(),
            entity.getToolTypeCode(),
            entity.getToolTypeName(),
            entity.getToolId(),
            entity.getRemovedDateTime(),
            entity.getLastChangedDateTime(),
            entity.getStartingPointLat(),
            entity.getStartingPointLon(),
            entity.getLength(),
            entity.getGeometry()
    );
  }
}
