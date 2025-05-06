package no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility.FishingFacility;

import java.time.LocalDateTime;

@Schema(name = "ResponseFishingFacility", description = "Response DTO for fishing facility GeoJSON feature")
public record ResponseFishingFacility(
        @Schema(description = "Primary key of the fishing facility", example = "1")
        Long id,

        @Schema(description = "GeoJSON type", example = "Feature")
        String type,

        @Schema(description = "Bounding box as JSON array", example = "[26.3533333,71.4233333,26.3533333,71.4233333]")
        String bbox,

        @Schema(description = "Geometry object as JSON string", example = "{\"type\":\"Point\",\"coordinates\":[26.3533333,71.4233333]}")
        String geometry,

        @Schema(description = "Version of the record", example = "0")
        Integer version,

        @Schema(description = "Name of the vessel (nullable)")
        String vesselName,

        @Schema(description = "Phone number of the vessel (nullable)")
        String vesselPhone,

        @Schema(description = "Tool type code", example = "UNK")
        String toolTypeCode,

        @Schema(description = "Setup date/time", example = "2020-01-28T20:29:00Z")
        LocalDateTime setupDateTime,

        @Schema(description = "UUID of the tool", example = "1942a413-8416-4c33-9432-6cec19f21736")
        String toolId,

        @Schema(description = "International Radio Call Sign (nullable)")
        String ircs,

        @Schema(description = "Maritime Mobile Service Identity (nullable)")
        String mmsi,

        @Schema(description = "IMO number (nullable)")
        String imo,

        @Schema(description = "Email of vessel contact (nullable)")
        String vesselEmail,

        @Schema(description = "Tool type name", example = "Unknown")
        String toolTypeName,

        @Schema(description = "Color code of the tool (nullable)", example = "#000000")
        String toolColor,

        @Schema(description = "Data source (nullable)")
        String source,

        @Schema(description = "Comment or notes (nullable)")
        String comment,

        @Schema(description = "Removal date/time (nullable)")
        LocalDateTime removedDateTime,

        @Schema(description = "Last change date/time (nullable)", example = "2020-01-28T20:31:24Z")
        LocalDateTime lastChangedDateTime,

        @Schema(description = "Source of last change (nullable)")
        String lastChangedBySource,

        @Schema(description = "Registration number (nullable)")
        String regNum,

        @Schema(description = "SBR registration number (nullable)")
        String sbrRegNum,

        @Schema(description = "Setup processed time (nullable)")
        LocalDateTime setupProcessedTime,

        @Schema(description = "Removed processed time (nullable)")
        LocalDateTime removedProcessedTime,

        @Schema(description = "Count of tools deployed (nullable)")
        Integer toolCount
) {
  /**
   * Maps a FishingFacility JPA entity to its Response DTO.
   *
   * @param entity the FishingFacility entity
   * @return ResponseFishingFacility DTO
   */
  public static ResponseFishingFacility fromEntity(FishingFacility entity) {
    return new ResponseFishingFacility(
            entity.getId(),
            entity.getType(),
            entity.getBbox(),
            entity.getGeometry(),
            entity.getVersion(),
            entity.getVesselName(),
            entity.getVesselPhone(),
            entity.getToolTypeCode(),
            entity.getSetupDateTime(),
            entity.getToolId(),
            entity.getIrcs(),
            entity.getMmsi(),
            entity.getImo(),
            entity.getVesselEmail(),
            entity.getToolTypeName(),
            entity.getToolColor(),
            entity.getSource(),
            entity.getComment(),
            entity.getRemovedDateTime(),
            entity.getLastChangedDateTime(),
            entity.getLastChangedBySource(),
            entity.getRegNum(),
            entity.getSbrRegNum(),
            entity.getSetupProcessedTime(),
            entity.getRemovedProcessedTime(),
            entity.getToolCount()
    );
  }
}

