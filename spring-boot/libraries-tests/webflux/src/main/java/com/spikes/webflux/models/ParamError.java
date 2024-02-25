package com.spikes.webflux.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ParamError(
        String field,
        String message
) implements Serializable {
}