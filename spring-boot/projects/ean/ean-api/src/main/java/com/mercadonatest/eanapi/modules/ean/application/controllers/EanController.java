package com.mercadonatest.eanapi.modules.ean.application.controllers;

import com.mercadonatest.eanapi.modules.ean.domain.models.responses.EanResponse;
import com.mercadonatest.eanapi.modules.ean.domain.services.EanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = EanController.CONTROLLER_PATH)
@Validated
@Tag(name = EanController.SWAGGER_TAG)
@RequiredArgsConstructor
public class EanController {
    public static final String SWAGGER_TAG = "Ean API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/ean";
    @SuppressWarnings("squid:S1075")
    public static final String GET_EAN_SUB_PATH = "/{eanValue}";

    private final EanService eanService;

    @GetMapping(GET_EAN_SUB_PATH)
    public ResponseEntity<EanResponse> get(
            @PathVariable @Min(0) @Max(9999999999999L) final long eanValue
    ) {
        final EanResponse response = eanService.get(eanValue);
        return ResponseEntity.ok(response);
    }
}
