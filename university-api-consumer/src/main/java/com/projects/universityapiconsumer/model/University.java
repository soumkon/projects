package com.projects.universityapiconsumer.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "url", "name", "country"})
public class University {

    @JsonAlias("web_pages")
    private List<String> url;
    private String name;
    private String country;

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
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    @Override
    public String toString() {
        return "University [url=" + url + ", name=" + name + ", country=" + country + "]";
    }
}
