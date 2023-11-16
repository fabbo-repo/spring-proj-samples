package com.mercadonatest.eanapi.modules.ean.domain.services;

import com.mercadonatest.eanapi.modules.ean.domain.models.responses.EanResponse;

public interface EanService {
    EanResponse get(final long eanValue);
}
