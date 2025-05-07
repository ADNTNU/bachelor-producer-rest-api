package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import no.ntnu.gr10.bachelor_producer_rest_api.company.Company;
import no.ntnu.gr10.bachelor_producer_rest_api.company.CompanyService;
import no.ntnu.gr10.bachelor_producer_rest_api.exception.CompanyNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.exception.FisheryActivityNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.dto.CreateFisheryActivity;
import no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity.dto.ResponseFisheryActivity;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitPublisher;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitQueueType;
import no.ntnu.gr10.bachelor_producer_rest_api.rabbit.RabbitQueueUtils;
import no.ntnu.gr10.bachelor_producer_rest_api.scope.Scope;
import org.springframework.stereotype.Service;

@Service
public class FisheryActivityService {

  final FisheryActivityRepository fisheryActivityRepository;
  final CompanyService companyService;
  final RabbitPublisher rabbitPublisher;

  public FisheryActivityService(FisheryActivityRepository fisheryActivityRepository, CompanyService companyService, RabbitPublisher rabbitPublisher) {
    this.fisheryActivityRepository = fisheryActivityRepository;
    this.companyService = companyService;
    this.rabbitPublisher = rabbitPublisher;
  }

  public FisheryActivity getByIdAndCompanyId(Long id, Long companyId) throws FisheryActivityNotFoundException {
    return fisheryActivityRepository.findFisheryActivityByIdAndCompany_Id(id, companyId)
            .orElseThrow(() -> new FisheryActivityNotFoundException("Could not find Fishery Activity with that ID!"));
  }

  public FisheryActivity createFisheryActivity(CreateFisheryActivity createFisheryActivity) throws CompanyNotFoundException {

    Company company = companyService.getCompanyById(createFisheryActivity.companyId());

    FisheryActivity fisheryActivity = new FisheryActivity();
    fisheryActivity.setCompany(company);
    fisheryActivity.setSetupDateTime(createFisheryActivity.setupDateTime());
    fisheryActivity.setToolTypeCode(createFisheryActivity.toolTypeCode());
    fisheryActivity.setToolTypeName(createFisheryActivity.toolTypeName());
    fisheryActivity.setToolId(createFisheryActivity.toolId());
    fisheryActivity.setLastChangedDateTime(createFisheryActivity.lastChangedDateTime());
    fisheryActivity.setStartingPointLat(createFisheryActivity.startingPointLat());
    fisheryActivity.setStartingPointLon(createFisheryActivity.startingPointLon());
    fisheryActivity.setLength(createFisheryActivity.length());
    fisheryActivity.setGeometry(createFisheryActivity.geometry());

    return fisheryActivityRepository.save(fisheryActivity);
  }

  public FisheryActivity updateForCompany(Long id, CreateFisheryActivity cmd) throws FisheryActivityNotFoundException {
    FisheryActivity existing = getByIdAndCompanyId(id, cmd.companyId());

    existing.setSetupDateTime(cmd.setupDateTime());
    existing.setToolTypeCode(cmd.toolTypeCode());
    existing.setToolTypeName(cmd.toolTypeName());
    existing.setToolId(cmd.toolId());
    existing.setRemovedDateTime(cmd.removedDateTime());
    existing.setLastChangedDateTime(cmd.lastChangedDateTime());
    existing.setStartingPointLat(cmd.startingPointLat());
    existing.setStartingPointLon(cmd.startingPointLon());
    existing.setLength(cmd.length());
    existing.setGeometry(cmd.geometry());
    return fisheryActivityRepository.save(existing);
  }

  public void deleteByIdAndCompanyId(Long id) throws FisheryActivityNotFoundException {
    FisheryActivity fa = fisheryActivityRepository.getFisheryActivitiesById(id)
            .orElseThrow(() -> new FisheryActivityNotFoundException("Fishery with that id was not found"));
    fisheryActivityRepository.delete(fa);
  }

}
