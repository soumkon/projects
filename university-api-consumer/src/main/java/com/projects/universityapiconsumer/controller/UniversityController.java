package com.projects.universityapiconsumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.projects.universityapiconsumer.model.CountryReport;
import com.projects.universityapiconsumer.model.University;
import com.projects.universityapiconsumer.model.UniversityByCountry;
import com.projects.universityapiconsumer.service.UniversityService;

@Component
public class UniversityController implements UniversityApi {

    @Autowired
    private UniversityService universityService;

    @Override
    public Page<University> getAll(Pageable pageable) {
        return universityService.getAll(pageable);
    }

    @Override
    public List<UniversityByCountry> searchByCountry(@PathVariable String country) {
        return universityService.searchByCountry(country);
    }

    @Override
    public List<CountryReport> getStatisticsByCountry() {
        return universityService.getStatisticsByCountry();
    }


}
