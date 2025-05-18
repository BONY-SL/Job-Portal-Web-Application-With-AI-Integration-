package com.job.lk.webapp.controller;

import com.job.lk.webapp.dto.CompanyDTO;
import com.job.lk.webapp.service.CompanyService;
import com.job.lk.webapp.util.JsonResponse;
import com.job.lk.webapp.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<JsonResponse> getAllCompanies(){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                companyService.getAllCompanies()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JsonResponse> createCompany(@RequestBody CompanyDTO companyDTO){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "New Company Added Success...",
                companyService.createCompany(companyDTO)
        ),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResponse> getCompanyById(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                companyService.getCompanyById(id)
        ),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<JsonResponse> getCompaniesByUserId(@PathVariable(value = "userId") Long userId){

        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Message.SUCCESS.name(),
                companyService.getCompaniesByUserId(userId)
        ),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonResponse> updateCompany(@PathVariable(value = "id") Long id,
                                                      @RequestBody CompanyDTO companyDTO){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                "Company Details Update Success",
                companyService.updateCompany(id,companyDTO)
        ),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResponse> deleteCompany(@PathVariable(value = "id") Long id){
        return new ResponseEntity<>(new JsonResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                "Company Deleted Success",
                companyService.deleteCompany(id)
        ),HttpStatus.CREATED);
    }
}
