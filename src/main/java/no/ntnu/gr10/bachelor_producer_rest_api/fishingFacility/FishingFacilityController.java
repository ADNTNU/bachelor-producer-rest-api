package no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.gr10.bachelor_producer_rest_api.exception.CompanyNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.exception.FishingFacilityNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility.dto.CreateFishingFacility;
import no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility.dto.ResponseFishingFacility;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitPublisher;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitQueueType;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitQueueUtils;
import no.ntnu.gr10.bachelor_producer_rest_api.scope.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import no.ntnu.gr10.bachelor_producer_rest_api.dto.ErrorResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fishingFacilities")
@Tag(name = "Fishing Facility", description = "APIs for managing fishing facilities")
public class FishingFacilityController {

  private final FishingFacilityService fishingFacilityService;
  private final RabbitPublisher rabbitPublisher;
  private static final Logger log = LoggerFactory.getLogger(FishingFacilityController.class);

  public FishingFacilityController(FishingFacilityService fishingFacilityService, RabbitPublisher rabbitPublisher) {
    this.fishingFacilityService = fishingFacilityService;
    this.rabbitPublisher = rabbitPublisher;
  }

  @Operation(summary = "Create a new fishing facility",
          description = "Create a fishing facility under the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Facility created",
                  content = @Content(schema = @Schema(implementation = FishingFacility.class))),
          @ApiResponse(responseCode = "404", description = "Company not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/create")
  public ResponseEntity<?> addFishingFacility(
          @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "CreateFishingFacility payload")
          CreateFishingFacility createDto) {
    try {
      FishingFacility facility = fishingFacilityService.createFishingFacility(createDto);

      ObjectMapper mapper = new ObjectMapper();
      // register the JSR-310 module
      mapper.registerModule(new JavaTimeModule());

      //    Publish to RabbitMQ
      String queueName = RabbitQueueUtils.getDynamicQueueName(String.valueOf(facility.getCompany().getId()), Scope.FISHING_FACILITY.toString(), RabbitQueueType.CREATE);
      rabbitPublisher.publish(queueName, mapper.writeValueAsString(ResponseFishingFacility.fromEntity(facility)));

      return ResponseEntity.status(HttpStatus.CREATED)
              .body(ResponseFishingFacility.fromEntity(facility));
    } catch (CompanyNotFoundException e) {
      log.error("Could not find company with id {}", createDto.companyId());
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("Could not find a company with that id"));
    } catch (Exception e) {
      log.error("Error creating FishingFacility", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while creating the Fishing Facility"));
    }
  }

  @Operation(summary = "Update an existing fishing facility",
          description = "Update a fishing facility by ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Facility updated",
                  content = @Content(schema = @Schema(implementation = FishingFacility.class))),
          @ApiResponse(responseCode = "404", description = "Facility not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> updateFishingFacility(
          @PathVariable Long id,
          @RequestBody CreateFishingFacility updateDto) {
    try {
      FishingFacility updated = fishingFacilityService.updateForCompany(id, updateDto);
      return ResponseEntity.ok(ResponseFishingFacility.fromEntity(updated));
    } catch (FishingFacilityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("Fishing Facility not found"));
    } catch (Exception e) {
      log.error("Error updating FishingFacility {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while updating the Fishing Facility"));
    }
  }

  @Operation(summary = "Delete a fishing facility",
          description = "Delete a fishing facility by ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Facility deleted"),
          @ApiResponse(responseCode = "404", description = "Facility not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteFishingFacility(@PathVariable Long id) {
    try {
      fishingFacilityService.deleteByIdAndCompanyId(id);
      return ResponseEntity.noContent().build();
    } catch (FishingFacilityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("Fishing Facility not found"));
    } catch (Exception e) {
      log.error("Error deleting FishingFacility {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while deleting the Fishing Facility"));
    }
  }

}
