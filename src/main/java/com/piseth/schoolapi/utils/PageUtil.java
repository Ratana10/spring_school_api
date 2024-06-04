package com.piseth.schoolapi.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PageUtil {
    int DEFAULT_PAGE_NUMBER = 1;
    int DEFAULT_PAGE_SIZE = 10;

    String PAGE_NUMBER = "page";
    String PAGE_SIZE = "size";


    static Pageable getPageable(int pageNumber, int pageSize) {
        if (pageNumber < DEFAULT_PAGE_NUMBER) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return PageRequest.of(pageNumber - 1, pageSize);
    }


}
