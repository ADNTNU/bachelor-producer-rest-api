package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity;

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
import no.ntnu.gr10.bachelor_producer_rest_api.exception.FisheryActivityNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.dto.CreateFisheryActivity;
import no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.dto.ResponseFisheryActivity;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitPublisher;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitQueueType;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitQueueUtils;
import no.ntnu.gr10.bachelor_producer_rest_api.scope.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import no.ntnu.gr10.bachelor_producer_rest_api.dto.ErrorResponse;


@RestController
@RequestMapping("/fisheryActivities")
@Tag(name = "Fishery Activity", description = "APIs for managing fishery activities")
public class FisheryActivityController {

  private final FisheryActivityService fisheryActivityService;
  private static final Logger log = LoggerFactory.getLogger(FisheryActivityController.class);
  private final RabbitPublisher rabbitPublisher;

  public FisheryActivityController(FisheryActivityService fisheryActivityService, RabbitPublisher rabbitPublisher) {
    this.fisheryActivityService = fisheryActivityService;
    this.rabbitPublisher = rabbitPublisher;
  }

  @Operation(summary = "Create a new fishery activity",
          description = "Create a fishery activity under the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Activity created",
                  content = @Content(schema = @Schema(implementation = FisheryActivity.class))),
          @ApiResponse(responseCode = "400", description = "Invalid request or company ID",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/create")
  public ResponseEntity<?> addFisheryActivity(
          @RequestBody
          @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "CreateFisheryActivity payload") CreateFisheryActivity createFisheryActivity){

    try {

      FisheryActivity fisheryActivity = fisheryActivityService.createFisheryActivity(createFisheryActivity);


      ObjectMapper mapper = new ObjectMapper();
      // register the JSR-310 module
      mapper.registerModule(new JavaTimeModule());

      //    Publish to RabbitMQ
      String queueName = RabbitQueueUtils.getDynamicQueueName(String.valueOf(createFisheryActivity.companyId()), Scope.FISHERY_ACTIVITY.toString(), RabbitQueueType.CREATE);
      rabbitPublisher.publish(queueName, mapper.writeValueAsString(ResponseFisheryActivity.fromEntity(fisheryActivity)));

      return ResponseEntity.ok(ResponseFisheryActivity.fromEntity(fisheryActivity));

    } catch (CompanyNotFoundException e){
      log.error("Could not find company with that id.");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("Could not find a company with that id"));
    } catch (Exception e){
      log.error("Error creating Fishery Activity");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while creating the company"));
    }
  }

  @Operation(summary = "Update an existing fishery activity",
          description = "Update a fishery activity by ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Activity updated",
                  content = @Content(schema = @Schema(implementation = FisheryActivity.class))),
          @ApiResponse(responseCode = "404", description = "Activity not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PutMapping("/{id}")
  public ResponseEntity<?> updateFisheryActivity(
          @PathVariable Long id,
          @RequestBody CreateFisheryActivity createFisheryActivity) {
    try {

      FisheryActivity fisheryActivity = fisheryActivityService.updateForCompany(id, createFisheryActivity);
      return ResponseEntity.ok(ResponseFisheryActivity.fromEntity(fisheryActivity));
    } catch (FisheryActivityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("FisheryActivity not found"));
    } catch (Exception e) {
      log.error("Error updating FisheryActivity {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while updating the FisheryActivity"));
    }
  }

  @Operation(summary = "Delete a fishery activity",
          description = "Delete a fishery activity by ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Activity deleted"),
          @ApiResponse(responseCode = "404", description = "Activity not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteFisheryActivity(
          @PathVariable Long id) {
    try {
      fisheryActivityService.deleteByIdAndCompanyId(id);
      return ResponseEntity.noContent().build();
    } catch (FisheryActivityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("FisheryActivity not found"));
    } catch (Exception e) {
      log.error("Error deleting FisheryActivity {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while deleting the FisheryActivity"));
    }
  }

}
