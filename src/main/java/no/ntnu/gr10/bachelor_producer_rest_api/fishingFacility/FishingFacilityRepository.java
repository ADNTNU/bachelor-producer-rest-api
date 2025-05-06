package no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FishingFacilityRepository extends JpaRepository<FishingFacility, Long> {
  Optional<FishingFacility> getFishingFacilitiesById(Long id);
  Optional<FishingFacility> findFishingFacilitiesByIdAndCompany_Id(Long id, Long companyId);
}
