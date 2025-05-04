package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity;

import no.ntnu.gr10.bachelor_producer_rest_api.exception.FisheryActivityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FisheryActivityService {

  final FisheryActivityRepository fisheryActivityRepository;

  public FisheryActivityService(FisheryActivityRepository fisheryActivityRepository){
    this.fisheryActivityRepository = fisheryActivityRepository;
  }

  public List<FisheryActivity> getAllFisheryActivitiesWithCompanyId(Integer companyId){
    return fisheryActivityRepository.findAllByCompany_Id(companyId.longValue());
  }

  public FisheryActivity getByIdAndCompanyId(Long id, Integer companyId){
    return fisheryActivityRepository.findFisheryActivityByIdAndCompany_Id(id, companyId.longValue())
            .orElseThrow(() -> new FisheryActivityNotFoundException("Could not find Fishery Activity with that ID!"));
  }


}
