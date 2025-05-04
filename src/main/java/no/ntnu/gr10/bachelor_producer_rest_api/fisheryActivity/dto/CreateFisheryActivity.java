package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "CreateFisheryActivity", description = "Payload for creating a new fishery activity")
public record CreateFisheryActivity(
        @Schema(
                description = "When the activity was set up",
                example = "2025-04-20T01:47:10",
                required = true
        )
        LocalDateTime setupDateTime,
        @Schema(
                description = "Tool type code (e.g. NETS)",
                example = "NETS",
                required = true
        )
        String toolTypeCode,
        @Schema(
                description = "Human‑readable tool type name",
                example = "Nets",
                required = true
        )
        String toolTypeName,

        @Schema(
                description = "UUID of the tool",
                example = "31e47253-6d8a-4060-942b-aec5ad0d4d12",
                required = true
        )
        String toolId,

        @Schema(
                description = "When the tool was removed (nullable)",
                example = "2025-04-20T02:30:00",
                required = false
        )
        LocalDateTime removedDateTime,

        @Schema(
                description = "Last time this record was changed",
                example = "2025-04-20T02:14:26",
                required = true
        )
        LocalDateTime lastChangedDateTime,

        @Schema(
                description = "Latitude of the starting point",
                example = "58.07933",
                required = true
        )
        Double startingPointLat,

        @Schema(
                description = "Longitude of the starting point",
                example = "3.60231",
                required = true
        )
        Double startingPointLon,

        @Schema(
                description = "Length of the activity in meters",
                example = "5321.06",
                required = true
        )
        Double length,

        @Schema(
                description = "WKT linestring geometry of the activity",
                example = "LINESTRING(3.60231 58.07933,3.61934 58.07934,...)",
                required = true
        )
        String geometry
)

{}
