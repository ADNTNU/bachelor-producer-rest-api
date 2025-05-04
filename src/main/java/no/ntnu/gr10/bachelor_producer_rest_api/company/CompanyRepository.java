package no.ntnu.gr10.bachelor_producer_rest_api.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
