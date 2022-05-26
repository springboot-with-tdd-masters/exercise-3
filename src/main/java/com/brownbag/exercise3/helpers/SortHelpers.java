package com.brownbag.exercise3.helpers;

import org.springframework.data.domain.Sort;

public class SortHelpers {

    public static Sort getSort(String sortBy, Boolean isAsc) {

        if(isAsc) {
            return Sort.by(sortBy).ascending();
        } else {
            return Sort.by(sortBy).descending();
        }
    }
}
