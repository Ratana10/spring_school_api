package com.piseth.schoolapi.utils;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public class PaginationUtil {
    public static Pageable getPageNumberAndPageSize(Map<String, String> params) {
        int page = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey( PageUtil.PAGE_NUMBER)) {
            page = Integer.parseInt(params.get( PageUtil.PAGE_NUMBER));
        }

        int size = PageUtil.DEFAULT_PAGE_SIZE;
        if (params.containsKey( PageUtil.PAGE_SIZE)) {
            size = Integer.parseInt(params.get( PageUtil.PAGE_SIZE));
        }

        return PageUtil.getPageable(page, size);
    }
}
