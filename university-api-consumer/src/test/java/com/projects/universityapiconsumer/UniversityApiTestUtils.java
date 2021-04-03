package com.projects.universityapiconsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.projects.universityapiconsumer.model.CountryReport;
import com.projects.universityapiconsumer.model.University;
import com.projects.universityapiconsumer.model.UniversityByCountry;

@Component
public class UniversityApiTestUtils {

    public List<University> createUniversityList() {
        List<University> content = new ArrayList<University>();
        University u1 = new University();
        u1.setUrl(Arrays.asList("http://www.marywood.edu"));
        u1.setName("Marywood University");
        u1.setCountry("United States");

        University u2 = new University();
        u2.setUrl(Arrays.asList("https://www.cstj.qc.ca", "https://ccmt.cstj.qc.ca", "https://ccml.cstj.qc.ca"));
        u2.setName("Cégep de Saint-Jérôme");
        u2.setCountry("Canada");

        University u3 = new University();
        u3.setUrl(Arrays.asList("http://www.lindenwood.edu/"));
        u3.setName("Lindenwood University");
        u3.setCountry("United States");

        University u4 = new University();
        u4.setUrl(Arrays.asList("http://www.davietjal.org/"));
        u4.setName("DAV Institute of Engineering & Technology");
        u4.setCountry("India");

        University u5 = new University();
        u5.setUrl(Arrays.asList("https://sullivan.edu/"));
        u5.setName("Sullivan University");
        u5.setCountry("United States");

        content.add(u1);
        content.add(u2);
        content.add(u3);
        content.add(u4);
        content.add(u5);

        return content;
    }

    public Page<University> createUniversitiesResponseForDefaultPageable() {
        return new PageImpl<University>(createUniversityList(), PageRequest.of(0, 20), 5);
    }

    public Page<University> createUniversitiesResponseForPage1Size2() {
        List<University> universities = new ArrayList<>();
        universities.add(createUniversityList().get(2));
        universities.add(createUniversityList().get(3));
        return new PageImpl<University>(universities, PageRequest.of(1, 2), 5);
    }

    public Page<University> createUniversitiesResponseForPage0Size1() {
        List<University> universities = new ArrayList<>();
        universities.add(createUniversityList().get(0));
        return new PageImpl<University>(universities, PageRequest.of(0, 1), 5);
    }

    public List<UniversityByCountry> createUniversitiesByCountryResponse(String country) {
        List<UniversityByCountry> content = new ArrayList<UniversityByCountry>();
        if (country.equals("Canada")) {
            content.add(new UniversityByCountry(Arrays.asList("https://www.cstj.qc.ca",
                    "https://ccmt.cstj.qc.ca", "https://ccml.cstj.qc.ca"), "Cégep de Saint-Jérôme"));
        } else if(country.equals("United States")) {
            content.add(new UniversityByCountry(Arrays.asList("http://www.marywood.edu"), "Marywood University"));
            content.add(new UniversityByCountry(Arrays.asList("http://www.lindenwood.edu/"), "Lindenwood University"));
            content.add(new UniversityByCountry(Arrays.asList("https://sullivan.edu/"), "Sullivan University"));
        } else if(country.equals("India")) {
            content.add(new UniversityByCountry(Arrays.asList("http://www.davietjal.org/"), "DAV Institute of Engineering & Technology"));
        }
        return content;
    }

    public List<CountryReport> createCountriseReportResponse() {
        List<CountryReport> content = new ArrayList<CountryReport>();
        content.add(new CountryReport("Canada","1"));
        content.add(new CountryReport("United States","3"));
        content.add(new CountryReport("India","1"));
        return content;
    }

}
