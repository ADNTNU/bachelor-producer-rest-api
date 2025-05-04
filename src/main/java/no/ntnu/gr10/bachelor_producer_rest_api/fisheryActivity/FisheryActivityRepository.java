package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FisheryActivityRepository extends JpaRepository<FisheryActivity, Long> {
  List<FisheryActivity> findAllByCompany_Id(Long companyId);
  Optional<FisheryActivity> findFisheryActivityByIdAndCompany_Id(Long id, Long companyId);



}
