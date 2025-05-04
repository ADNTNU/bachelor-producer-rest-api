package no.ntnu.gr10.bachelor_producer_rest_api.company;


import no.ntnu.gr10.bachelor_producer_rest_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

  private final CompanyService companyService;


  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  //TODO fetch company with id, and use a response dto, dont need to show administrators

  /**
   * Fetches all companies associated with the authenticated user.
   */
  @GetMapping("/all")
  public ResponseEntity<?> getAllCompanies() {
    try {

      return ResponseEntity.ok(
              companyService.getAllCompanies()
      );

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse("An error occurred while fetching companies"));
    }
  }

}
