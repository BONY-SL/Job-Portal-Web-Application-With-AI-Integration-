package com.job.lk.webapp.service;

import com.job.lk.webapp.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();

    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompanyById(Long id);

    List<CompanyDTO> getCompaniesByUserId(Long userId);

    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);

    boolean deleteCompany(Long id);
}
