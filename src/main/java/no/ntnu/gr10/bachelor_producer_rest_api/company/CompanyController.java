package no.ntnu.gr10.bachelor_producer_rest_api.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.gr10.bachelor_producer_rest_api.company.dto.CompanySimpleDto;
import no.ntnu.gr10.bachelor_producer_rest_api.dto.ErrorResponse;
import no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.FisheryActivityController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing companies.
 * <p>
 * Exposes endpoints to fetch company data in simplified DTO form.
 * </p>
 */
@Tag(name = "Company", description = "Operations related to companies")
@RestController
@RequestMapping("/company")
public class CompanyController {

  private final CompanyService companyService;
  private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }



  /**
   * Retrieves all companies in a simplified form.
   *
   * @return HTTP 200 with a list of {@link CompanySimpleDto} if successful;
   *         HTTP 500 with an {@link ErrorResponse} if an error occurs.
   */
  @Operation(
          summary = "Fetch all companies",
          description = "Returns a list of all companies as simple DTOs."
  )
  @ApiResponses(value = {
          @ApiResponse(
                  responseCode = "200",
                  description = "List of companies retrieved successfully",
                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = CompanySimpleDto.class)))),
          @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(
                  schema = @Schema(implementation = ErrorResponse.class))
          )
  })
  @GetMapping("/all")
  public ResponseEntity<?> getAllCompanies() {
    try {
      List<CompanySimpleDto> companies = companyService.getAllCompanies()
              .stream().map(CompanySimpleDto::fromEntity).toList();

      return ResponseEntity.ok(companies);
    } catch (Exception e) {
      log.error("Fault fetching all companies from the database, internal server error");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while fetching companies"));
    }
  }

}
