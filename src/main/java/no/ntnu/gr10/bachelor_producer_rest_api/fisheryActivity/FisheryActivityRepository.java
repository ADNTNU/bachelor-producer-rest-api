package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FisheryActivityRepository extends JpaRepository<FisheryActivity, Long> {
  Optional<FisheryActivity> findFisheryActivityByIdAndCompany_Id(Long id, Long companyId);
  Optional<FisheryActivity> getFisheryActivitiesById(Long id);
}
