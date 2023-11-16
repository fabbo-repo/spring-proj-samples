package com.mercadonatest.eanapi.modules.provider.domain.models.entities;

import com.mercadonatest.eanapi.core.models.Auditable;
import com.mercadonatest.eanapi.modules.provider.domain.models.enums.ProviderTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = ProviderJpaEntity.TABLE_NAME)
public class ProviderJpaEntity extends Auditable<String> {
    public final static String TABLE_NAME = "product_provider";

    public final static String SORT_FIELD = "createdAt";

    public final static String ID_COL = "id";
    private final static String TYPE_COL = "provider_type";
    public final static String EAN_VALUE_COL = "ean_value";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COL)
    private Long id;

    @Column(name = TYPE_COL, nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ProviderTypeEnum type;

    @Column(name = EAN_VALUE_COL, nullable = false)
    private int eanValue;
}
