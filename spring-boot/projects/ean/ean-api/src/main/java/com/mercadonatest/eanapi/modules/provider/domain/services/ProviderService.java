package com.mercadonatest.eanapi.modules.provider.domain.services;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.provider.domain.models.requests.ProviderRequest;
import com.mercadonatest.eanapi.modules.provider.domain.models.responses.ProviderResponse;

public interface ProviderService {

    ProviderResponse create(final ProviderRequest request);

    ProviderResponse get(final long id);

    PageDto<ProviderResponse> list(final int pageNum);

    Void update(final long id, final ProviderRequest request);

    Void delete(final long id);
}
