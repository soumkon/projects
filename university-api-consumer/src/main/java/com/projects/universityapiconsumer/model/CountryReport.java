package com.projects.universityapiconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "country", "numOfUniversities"})
public class CountryReport {

    String country;
    @JsonProperty("number_of_universities")
    String numOfUniversities;

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getNumOfUniversities() {
        return numOfUniversities;
    }
    public void setNumOfUniversities(String numOfUniversities) {
        this.numOfUniversities = numOfUniversities;
    }
    @Override
    public String toString() {
        return "Report [country=" + country + ", numOfUniversities=" + numOfUniversities + "]";
    }
}
