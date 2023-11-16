package com.mercadonatest.eanapi.modules.destiny.application.controller;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.destiny.domain.models.requests.DestinyRequest;
import com.mercadonatest.eanapi.modules.destiny.domain.models.responses.DestinyResponse;
import com.mercadonatest.eanapi.modules.destiny.domain.services.DestinyService;
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
@RequestMapping(path = DestinyController.CONTROLLER_PATH)
@Validated
@Tag(name = DestinyController.SWAGGER_TAG)
@RequiredArgsConstructor
public class DestinyController {
    public static final String SWAGGER_TAG = "Destiny API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/destiny";
    @SuppressWarnings("squid:S1075")
    public static final String GET_DESTINY_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String PUT_DESTINY_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String DEL_DESTINY_SUB_PATH = "/{id}";

    private final DestinyService destinyService;

    @PostMapping
    public ResponseEntity<DestinyResponse> create(
            @Valid @RequestBody final DestinyRequest request
    ) {
        final DestinyResponse response = destinyService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_DESTINY_SUB_PATH)
    public ResponseEntity<DestinyResponse> get(
            @PathVariable final long id
    ) {
        final DestinyResponse response = destinyService.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageDto<DestinyResponse>> list(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<DestinyResponse> pageDto = destinyService.list(page);
        return ResponseEntity.ok(pageDto);
    }

    @PutMapping(PUT_DESTINY_SUB_PATH)
    public ResponseEntity<Void> update(
            @PathVariable final long id,
            @Valid @RequestBody final DestinyRequest request
    ) {
        destinyService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(DEL_DESTINY_SUB_PATH)
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        destinyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
