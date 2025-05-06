package no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility;

import no.ntnu.gr10.bachelor_producer_rest_api.company.Company;
import no.ntnu.gr10.bachelor_producer_rest_api.company.CompanyService;
import no.ntnu.gr10.bachelor_producer_rest_api.exception.CompanyNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.exception.FishingFacilityNotFoundException;
import no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility.dto.CreateFishingFacility;
import org.springframework.stereotype.Service;


@Service
public class FishingFacilityService {

  private final FishingFacilityRepository fishingFacilityRepository;
  private final CompanyService companyService;

  public FishingFacilityService(FishingFacilityRepository fishingFacilityRepository, CompanyService companyService){
    this.fishingFacilityRepository = fishingFacilityRepository;
    this.companyService = companyService;
  }

  public FishingFacility getByIdAndCompanyId(Long id, Long companyId) throws FishingFacilityNotFoundException {
    return fishingFacilityRepository
            .findFishingFacilitiesByIdAndCompany_Id(id, companyId)
            .orElseThrow(() -> new FishingFacilityNotFoundException(
                    "Could not find Fishing Facility with that ID!"));
  }

  public FishingFacility createFishingFacility(CreateFishingFacility dto) throws CompanyNotFoundException {
    Company company = companyService.getCompanyById(dto.companyId());

    FishingFacility ff = new FishingFacility();
    ff.setCompany(company);
    ff.setType(dto.type());
    ff.setBbox(dto.bbox());
    ff.setGeometry(dto.geometry());
    ff.setVersion(dto.version());
    ff.setVesselName(dto.vesselName());
    ff.setVesselPhone(dto.vesselPhone());
    ff.setToolTypeCode(dto.toolTypeCode());
    ff.setSetupDateTime(dto.setupDateTime());
    ff.setToolId(dto.toolId());
    ff.setIrcs(dto.ircs());
    ff.setMmsi(dto.mmsi());
    ff.setImo(dto.imo());
    ff.setVesselEmail(dto.vesselEmail());
    ff.setToolTypeName(dto.toolTypeName());
    ff.setToolColor(dto.toolColor());
    ff.setSource(dto.source());
    ff.setComment(dto.comment());
    ff.setRemovedDateTime(dto.removedDateTime());
    ff.setLastChangedDateTime(dto.lastChangedDateTime());
    ff.setLastChangedBySource(dto.lastChangedBySource());
    ff.setRegNum(dto.regNum());
    ff.setSbrRegNum(dto.sbrRegNum());
    ff.setSetupProcessedTime(dto.setupProcessedTime());
    ff.setRemovedProcessedTime(dto.removedProcessedTime());
    ff.setToolCount(dto.toolCount());

    return fishingFacilityRepository.save(ff);
  }

  public FishingFacility updateForCompany(Long id, CreateFishingFacility dto) throws FishingFacilityNotFoundException {
    FishingFacility existing = getByIdAndCompanyId(id, dto.companyId());

    existing.setType(dto.type());
    existing.setBbox(dto.bbox());
    existing.setGeometry(dto.geometry());
    existing.setVersion(dto.version());
    existing.setVesselName(dto.vesselName());
    existing.setVesselPhone(dto.vesselPhone());
    existing.setToolTypeCode(dto.toolTypeCode());
    existing.setSetupDateTime(dto.setupDateTime());
    existing.setToolId(dto.toolId());
    existing.setIrcs(dto.ircs());
    existing.setMmsi(dto.mmsi());
    existing.setImo(dto.imo());
    existing.setVesselEmail(dto.vesselEmail());
    existing.setToolTypeName(dto.toolTypeName());
    existing.setToolColor(dto.toolColor());
    existing.setSource(dto.source());
    existing.setComment(dto.comment());
    existing.setRemovedDateTime(dto.removedDateTime());
    existing.setLastChangedDateTime(dto.lastChangedDateTime());
    existing.setLastChangedBySource(dto.lastChangedBySource());
    existing.setRegNum(dto.regNum());
    existing.setSbrRegNum(dto.sbrRegNum());
    existing.setSetupProcessedTime(dto.setupProcessedTime());
    existing.setRemovedProcessedTime(dto.removedProcessedTime());
    existing.setToolCount(dto.toolCount());

    return fishingFacilityRepository.save(existing);
  }

  public void deleteByIdAndCompanyId(Long id) throws FishingFacilityNotFoundException {
    FishingFacility ff = fishingFacilityRepository
            .getFishingFacilitiesById(id)
            .orElseThrow(() -> new FishingFacilityNotFoundException(
                    "Fishing Facility with that ID was not found"));
    fishingFacilityRepository.delete(ff);
  }

}
