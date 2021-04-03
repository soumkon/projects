package com.projects.universityapiconsumer.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.projects.universityapiconsumer.model.CountryReport;
import com.projects.universityapiconsumer.model.University;
import com.projects.universityapiconsumer.model.UniversityByCountry;

public interface UniversityService {

    Page<University> getAll(Pageable pageable);

    List<UniversityByCountry> searchByCountry(String country);

    List<CountryReport> getStatisticsByCountry();

}
