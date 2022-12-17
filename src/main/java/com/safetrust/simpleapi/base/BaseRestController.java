package com.safetrust.simpleapi.base;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public abstract class BaseRestController {
    protected static final String API_BASE = "/api";
    protected static final String ID = "/{id}";

    @Autowired
    protected ModelMapper modelMapper;
}
