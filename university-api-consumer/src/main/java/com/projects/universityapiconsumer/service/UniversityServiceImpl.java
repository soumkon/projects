package com.projects.universityapiconsumer.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.projects.universityapiconsumer.model.CountryReport;
import com.projects.universityapiconsumer.model.University;
import com.projects.universityapiconsumer.model.UniversityByCountry;

@Service
public class UniversityServiceImpl implements UniversityService{

    @Autowired
    private WebClient webClient;

    @Value("${external.api.host:http://localhost:5000}")
    private String apiUrl;

    private static final ParameterizedTypeReference<List<University>> UNIVERSITY_LIST =
            new ParameterizedTypeReference<List<University>>() { };

    @Override
    public Page<University> getAll(Pageable pageable) {
        List<University> universities = getUniversityList(Optional.empty());
        return getPageFromList(pageable, true, universities);
    }

    @Override
    public List<UniversityByCountry> searchByCountry(String country) {
        List<UniversityByCountry> universitiesByCountry = new ArrayList<UniversityByCountry>();
        List<University> universities = getUniversityList(Optional.of(country));
        universities.forEach(u -> {
            UniversityByCountry university = new UniversityByCountry();
            university.setUrl(u.getUrl());
            university.setName(u.getName());
            universitiesByCountry.add(university);
        });
        return universitiesByCountry;
    }

    @Override
    public List<CountryReport> getStatisticsByCountry() {
        List<CountryReport> reports = new ArrayList<CountryReport>();
        List<University> universities = getUniversityList(Optional.empty());
        Map<String, List<University>> response = universities.stream()
                .collect(Collectors.groupingBy(University::getCountry));

        response.keySet().forEach(r -> {
            CountryReport report = new CountryReport();
            report.setCountry(r);
            report.setNumOfUniversities(String.valueOf(response.get(r).size()));
            reports.add(report);
        });
        return reports;

    }

    private List<University> getUniversityList(Optional<String> country) {
        String restDestination = getRestDestinationUrl(country);
        List<University> response = webClient.get()
              .uri(URI.create(restDestination))
              .retrieve()
              .bodyToMono(UNIVERSITY_LIST)
              .block();
        return response.stream().distinct().collect(Collectors.toList());
    }

    private String getRestDestinationUrl(Optional<String> country) {
        StringBuilder restDestination = new StringBuilder(apiUrl + "/search");
        if (country.isPresent() && country.get() != null) {
            restDestination.append("?country=" + country.get());
        }
        return restDestination.toString();
    }

    private <T> Page<T> getPageFromList(Pageable pageable, Boolean applyPagination, List<T> list) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        int size = applyPagination ? list.size() : pageable.getPageSize();
        list = applyPagination ? list.subList(start, end) : list;
        return new PageImpl<T>(list, pageable, size);
    }

}
