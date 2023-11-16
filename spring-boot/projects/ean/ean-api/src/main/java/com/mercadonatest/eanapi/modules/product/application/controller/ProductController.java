package com.mercadonatest.eanapi.modules.product.application.controller;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.product.domain.models.requests.ProductRequest;
import com.mercadonatest.eanapi.modules.product.domain.models.responses.ProductResponse;
import com.mercadonatest.eanapi.modules.product.domain.services.ProductService;
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
@RequestMapping(path = ProductController.CONTROLLER_PATH)
@Validated
@Tag(name = ProductController.SWAGGER_TAG)
@RequiredArgsConstructor
public class ProductController {
    public static final String SWAGGER_TAG = "Product API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/product";
    @SuppressWarnings("squid:S1075")
    public static final String GET_PRODUCT_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String PUT_PRODUCT_SUB_PATH = "/{id}";
    @SuppressWarnings("squid:S1075")
    public static final String DEL_PRODUCT_SUB_PATH = "/{id}";

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody final ProductRequest request
    ) {
        final ProductResponse response = productService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_PRODUCT_SUB_PATH)
    public ResponseEntity<ProductResponse> get(
            @PathVariable final long id
    ) {
        final ProductResponse response = productService.get(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageDto<ProductResponse>> list(
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final PageDto<ProductResponse> pageDto = productService.list(page);
        return ResponseEntity.ok(pageDto);
    }

    @PutMapping(PUT_PRODUCT_SUB_PATH)
    public ResponseEntity<Void> update(
            @PathVariable final long id,
            @Valid @RequestBody final ProductRequest request
    ) {
        productService.update(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(DEL_PRODUCT_SUB_PATH)
    public ResponseEntity<Void> delete(@PathVariable final long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
