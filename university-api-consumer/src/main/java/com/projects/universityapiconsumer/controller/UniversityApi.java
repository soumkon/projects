package com.projects.universityapiconsumer.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.universityapiconsumer.model.CountryReport;
import com.projects.universityapiconsumer.model.University;
import com.projects.universityapiconsumer.model.UniversityByCountry;

@RestController
@RequestMapping("/universities")
public interface UniversityApi {

    @GetMapping
    Page<University> getAll(Pageable pageable);

    @GetMapping("/{country}")
    List<UniversityByCountry> searchByCountry(@PathVariable String country);

    @GetMapping("/statistics")
    List<CountryReport> getStatisticsByCountry();

}