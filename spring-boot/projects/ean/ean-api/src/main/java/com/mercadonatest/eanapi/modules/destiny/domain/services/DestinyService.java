package com.mercadonatest.eanapi.modules.destiny.domain.services;

import com.mercadonatest.eanapi.core.models.dtos.PageDto;
import com.mercadonatest.eanapi.modules.destiny.domain.models.requests.DestinyRequest;
import com.mercadonatest.eanapi.modules.destiny.domain.models.responses.DestinyResponse;

public interface DestinyService {

    DestinyResponse create(final DestinyRequest request);

    DestinyResponse get(final long id);

    PageDto<DestinyResponse> list(final int pageNum);

    Void update(final long id, final DestinyRequest request);

    Void delete(final long id);
}
