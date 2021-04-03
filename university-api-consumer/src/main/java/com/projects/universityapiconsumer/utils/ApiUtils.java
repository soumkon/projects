package com.projects.universityapiconsumer.utils;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ApiUtils {

    @Value("${external.api.host:http://localhost:5000}")
    private String apiUrl;

    public String getRestDestinationUrl(Optional<String> country) {
        StringBuilder restDestination = new StringBuilder(apiUrl + "/search");
        if (country.isPresent() && country.get() != null) {
            restDestination.append("?country=" + country.get());
        }
        return restDestination.toString();
    }

    public <T> Page<T> getPageFromList(Pageable pageable, Boolean applyPagination, List<T> list) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        int size = applyPagination ? list.size() : pageable.getPageSize();
        list = applyPagination ? list.subList(start, end) : list;
        return new PageImpl<T>(list, pageable, size);
    }

}
