package com.job.lk.webapp.service.impl;

import com.job.lk.webapp.dto.CompanyDTO;
import com.job.lk.webapp.entity.Company;
import com.job.lk.webapp.entity.User;
import com.job.lk.webapp.exception.coustom.ResourceNotFound;
import com.job.lk.webapp.exception.coustom.UserNotFoundError;
import com.job.lk.webapp.repository.CompanyRepository;
import com.job.lk.webapp.repository.UserRepository;
import com.job.lk.webapp.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {

        Optional<User> optionalUser = userRepository.findById(companyDTO.getUserId());

        if(optionalUser.isEmpty()){
            throw new UserNotFoundError("User Not Found");
        }

        Company company = convertDtoToEntity(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return convertEntityToDTO(savedCompany);
    }

    @Override
    public CompanyDTO getCompanyById(Long id) {
        return companyRepository.findById(id).map(this::convertEntityToDTO).orElseThrow(()-> new ResourceNotFound("Company Not Found with Id : "+ id));
    }

    @Override
    public List<CompanyDTO> getCompaniesByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundError("User Not Found");
        }
        return companyRepository.findByUserId(userId)
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {

        Optional<Company> optionalCompany = companyRepository.findById(id);

        if(optionalCompany.isPresent()){
            Company existingCompany = optionalCompany.get();
            existingCompany.setName(companyDTO.getName());
            existingCompany.setLocation(companyDTO.getLocation());
            Company updatedCompany = companyRepository.save(existingCompany);
            return convertEntityToDTO(updatedCompany);
        }
        throw new ResourceNotFound("Company Not Found");
    }

    @Override
    public boolean deleteCompany(Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            companyRepository.deleteById(id);
            return true;
        }
        throw new ResourceNotFound("Company Not Found....");
    }

    private CompanyDTO convertEntityToDTO(Company company){
        return CompanyDTO.builder().id(company.getId()).userId(company.getUserId()).name(company.getName()).location(company.getLocation()).build();
    }

    private Company convertDtoToEntity(CompanyDTO companyDto){
        return Company.builder().id(companyDto.getId()).userId(companyDto.getUserId()).name(companyDto.getName()).location(companyDto.getLocation()).build();
    }

}
