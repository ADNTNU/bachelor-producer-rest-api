package no.ntnu.gr10.bachelor_producer_rest_api.company;

import no.ntnu.gr10.bachelor_producer_rest_api.exception.CompanyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

  final CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository){
    this.companyRepository = companyRepository;
  }

  List<Company> getAllCompanies(){
    return companyRepository.findAll();
  }

  public Company getCompanyById(Long id) throws CompanyNotFoundException {
    return companyRepository.getCompanyById(id).orElseThrow(() -> new CompanyNotFoundException("Company not found"));
  }
}
