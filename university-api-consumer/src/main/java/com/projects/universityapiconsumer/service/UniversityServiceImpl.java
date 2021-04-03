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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.projects.universityapiconsumer.model.CountryReport;
import com.projects.universityapiconsumer.model.University;
import com.projects.universityapiconsumer.model.UniversityByCountry;
import com.projects.universityapiconsumer.utils.ApiUtils;

@Service
public class UniversityServiceImpl implements UniversityService{

    @Autowired
    private WebClient webClient;

    @Autowired
    private ApiUtils utils;

    @Value("${external.api.host:http://localhost:5000}")
    private String apiUrl;

    private static final ParameterizedTypeReference<List<University>> UNIVERSITY_LIST =
            new ParameterizedTypeReference<List<University>>() { };

    @Override
    public Page<University> getAll(Pageable pageable) {
        List<University> universities = getUniversityList(Optional.empty());
        return utils.getPageFromList(pageable, true, universities);
    }

    @Override
    public List<UniversityByCountry> searchByCountry(String country) {
        List<UniversityByCountry> universitiesByCountry = new ArrayList<UniversityByCountry>();
        List<University> universities = getUniversityList(Optional.of(country));
        universities.forEach(u -> {
            UniversityByCountry university = new UniversityByCountry(u.getUrl(), u.getName());
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
            CountryReport report = new CountryReport(r, String.valueOf(response.get(r).size()));
            reports.add(report);
        });
        return reports;

    }

    public List<University> getUniversityList(Optional<String> country) {
        String restDestination = utils.getRestDestinationUrl(country);
        List<University> response = webClient.get()
              .uri(URI.create(restDestination))
              .retrieve()
              .bodyToMono(UNIVERSITY_LIST)
              .block();
        return response.stream().distinct().collect(Collectors.toList());
    }

}
