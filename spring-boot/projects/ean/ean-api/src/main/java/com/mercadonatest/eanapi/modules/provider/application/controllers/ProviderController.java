package com.mercadonatest.eanapi.modules.provider.application.controllers;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.provider.domain.models.requests.ProviderRequest;
import com.mercadonatest.eanapi.modules.provider.domain.models.responses.ProviderResponse;
import com.mercadonatest.eanapi.modules.provider.domain.services.ProviderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ProviderController.CONTROLLER_PATH)
@Validated
@Tag(name = ProviderController.SWAGGER_TAG)
@RequiredArgsConstructor
public class ProviderController {
    public static final String SWAGGER_TAG = "Provider API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/provider";
    @SuppressWarnings("squid:S1075")
    public static final String GET_PROVIDER_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String PUT_PROVIDER_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String DEL_PROVIDER_SUB_PATH = "/{id}";

    private final ProviderService providerService;

    @PostMapping
    public ResponseEntity<ProviderResponse> create(
            @Valid @RequestBody final ProviderRequest request
    ) {
        final ProviderResponse response = providerService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_PROVIDER_SUB_PATH)
    public ResponseEntity<ProviderResponse> get(
            @PathVariable final long id
    ) {
        final ProviderResponse response = providerService.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageDto<ProviderResponse>> list(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<ProviderResponse> pageDto = providerService.list(page);
        return ResponseEntity.ok(pageDto);
    }

    @PutMapping(PUT_PROVIDER_SUB_PATH)
    public ResponseEntity<Void> update(
            @PathVariable final long id,
            @Valid @RequestBody final ProviderRequest request
    ) {
        providerService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(DEL_PROVIDER_SUB_PATH)
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        providerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
