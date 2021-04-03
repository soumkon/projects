package com.projects.universityapiconsumer.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "url", "name"})
public class UniversityByCountry {

    @JsonAlias("web_pages")
    private List<String> url;
    private String name;

    public UniversityByCountry(List<String> url, String name) {
        super();
        this.url = url;
        this.name = name;
    }
    public List<String> getUrl() {
        return url;
    }
    public void setUrl(List<String> url) {
        this.url = url;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "UniversityByCountry [url=" + url + ", name=" + name + "]";
    }
}
