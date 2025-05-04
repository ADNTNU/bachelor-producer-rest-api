package no.ntnu.gr10.bachelor_producer_rest_api.company;

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
}
