package com.safetrust.simpleapi.base;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
public abstract class BaseRestController {
    protected static final String API_BASE = "/api";
    protected static final String ID = "/{id}";

    @Autowired
    protected ModelMapper modelMapper;

    /**
     * Pagable compoundinng minimising this code when asseing the page parametr
     * @param page
     * @param size
     * @param sort
     * @param direction
     * @return
     */
    protected Pageable pageRequest(int page, int size, List<String> sort, String direction) {
        return PageRequest.of(
            page > 0 ? page - 1 : 0,
            size <= 0 ? 10 : size,
            Sort.Direction.fromString(direction),
            sort.toArray(new String[0])
        );
    }
}
