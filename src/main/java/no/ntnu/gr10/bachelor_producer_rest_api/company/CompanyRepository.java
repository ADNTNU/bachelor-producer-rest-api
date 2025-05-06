package no.ntnu.gr10.bachelor_producer_rest_api.company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

  Optional<Company> getCompanyById(Long id);

}
