package no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "CreateFishingFacility", description = "Payload for creating a new fishing facility")
public record CreateFishingFacility(
        @Schema(description = "ID of the owning company", example = "5")
        long companyId,

        @Schema(description = "GeoJSON type", example = "Feature")
        String type,

        @Schema(description = "Bounding box as JSON array", example = "[26.3533333,71.4233333,26.3533333,71.4233333]", nullable = true)
        String bbox,

        @Schema(description = "Geometry object as JSON", example = "{\"type\":\"Point\",\"coordinates\":[26.3533333,71.4233333]}", nullable = false)
        String geometry,

        @Schema(description = "Version of the record", example = "0")
        Integer version,

        @Schema(description = "Name of the vessel (nullable)", example = "Vessel One", nullable = true)
        String vesselName,

        @Schema(description = "Phone number of the vessel (nullable)", example = "+4712345678", nullable = true)
        String vesselPhone,

        @Schema(description = "Tool type code", example = "UNK")
        String toolTypeCode,

        @Schema(description = "Setup date/time", example = "2020-01-28T20:29:00Z")
        LocalDateTime setupDateTime,

        @Schema(description = "UUID of the tool", example = "1942a413-8416-4c33-9432-6cec19f21736")
        String toolId,

        @Schema(description = "International Radio Call Sign (nullable)", example = "ABC123", nullable = true)
        String ircs,

        @Schema(description = "Maritime Mobile Service Identity (nullable)", example = "123456789", nullable = true)
        String mmsi,

        @Schema(description = "IMO number (nullable)", example = "9876543", nullable = true)
        String imo,

        @Schema(description = "Email of vessel contact (nullable)", example = "vessel@example.com", nullable = true)
        String vesselEmail,

        @Schema(description = "Tool type name", example = "Unknown")
        String toolTypeName,

        @Schema(description = "Color code of the tool (nullable)", example = "#000000", nullable = true)
        String toolColor,

        @Schema(description = "Data source (nullable)", example = "AIS", nullable = true)
        String source,

        @Schema(description = "Comment or notes (nullable)", example = "Some comment", nullable = true)
        String comment,

        @Schema(description = "Removal date/time (nullable)", example = "2020-01-28T21:00:00Z", nullable = true)
        LocalDateTime removedDateTime,

        @Schema(description = "Last change date/time (nullable)", example = "2020-01-28T20:31:24Z", nullable = true)
        LocalDateTime lastChangedDateTime,

        @Schema(description = "Source of last change (nullable)", example = "system", nullable = true)
        String lastChangedBySource,

        @Schema(description = "Registration number (nullable)", example = "REG123", nullable = true)
        String regNum,

        @Schema(description = "SBR registration number (nullable)", example = "SBR456", nullable = true)
        String sbrRegNum,

        @Schema(description = "Setup processed time (nullable)", example = "2020-01-28T20:30:00Z", nullable = true)
        LocalDateTime setupProcessedTime,

        @Schema(description = "Removed processed time (nullable)", example = "2020-01-28T21:10:00Z", nullable = true)
        LocalDateTime removedProcessedTime,

        @Schema(description = "Count of tools deployed (nullable)", example = "3", nullable = true)
        Integer toolCount
) {}

