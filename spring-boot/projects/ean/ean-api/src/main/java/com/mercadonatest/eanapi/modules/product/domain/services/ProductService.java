package com.mercadonatest.eanapi.modules.product.domain.services;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.product.domain.models.requests.ProductRequest;
import com.mercadonatest.eanapi.modules.product.domain.models.responses.ProductResponse;

public interface ProductService {

    ProductResponse create(final ProductRequest request);

    ProductResponse get(final long id);

    PageDto<ProductResponse> list(final int pageNum);

    Void update(final long id, final ProductRequest request);

    Void delete(final long id);
}
