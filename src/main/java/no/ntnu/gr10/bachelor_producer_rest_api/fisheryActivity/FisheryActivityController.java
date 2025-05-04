package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.gr10.bachelor_producer_rest_api.exception.FisheryActivityNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.dto.ResponseFisheryActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import no.ntnu.gr10.bachelor_producer_rest_api.dto.ErrorResponse;

import java.util.List;

@RestController
@RequestMapping("/fisheryActivities")
@Tag(name = "Fishery Activity", description = "APIs for managing fishery activities")
public class FisheryActivityController {

  private final FisheryActivityService fisheryActivityService;
  private static final Logger log = LoggerFactory.getLogger(FisheryActivityController.class);

  public FisheryActivityController(FisheryActivityService fisheryActivityService){
    this.fisheryActivityService = fisheryActivityService;
  }

  @Operation(summary = "Get all fishery activities",
          description = "Retrieve all fishery activities for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth")
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "List of activities",
                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = FisheryActivity.class)))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping()
  public ResponseEntity<?> getAllFisheryActivities(Authentication authentication){

    try {
      Integer companyId = (Integer) authentication.getPrincipal();

      List<ResponseFisheryActivity> responseFisheryActivities = fisheryActivityService
              .getAllFisheryActivitiesWithCompanyId(companyId)
              .stream()
              .map(ResponseFisheryActivity::fromEntity)
              .toList();

      return ResponseEntity.ok(responseFisheryActivities);
    }catch (Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("An error occurred while fetching Fishery Activities"));
    }
  }


  @Operation(summary = "Get fishery activity by ID",
          description = "Retrieve a single fishery activity by its ID for the authenticated company.",
          security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Found the activity",
                  content = @Content(schema = @Schema(implementation = FisheryActivity.class))),
          @ApiResponse(responseCode = "404", description = "Activity not found",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal server error",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(
          @PathVariable
          @Schema(description = "ID of the fishery activity", example = "1") Long id,
          Authentication authentication) {
    try {
      Integer companyId = (Integer) authentication.getPrincipal();
      FisheryActivity fa = fisheryActivityService.getByIdAndCompanyId(id, companyId);

      return ResponseEntity.ok(ResponseFisheryActivity.fromEntity(fa));
    } catch (FisheryActivityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new ErrorResponse("FisheryActivity not found"));
    } catch (Exception e) {
      log.error("Error fetching FisheryActivity {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while fetching the FisheryActivity"));
    }
  }

}
