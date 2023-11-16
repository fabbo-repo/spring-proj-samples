package com.mercadonatest.eanapi.modules.destiny.domain.models.entities;

import com.mercadonatest.eanapi.core.models.Auditable;
import com.mercadonatest.eanapi.modules.destiny.domain.models.enums.DestinyTypeEnum;
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
@Table(name = DestinyJpaEntity.TABLE_NAME)
public class DestinyJpaEntity extends Auditable<String> {
    public final static String TABLE_NAME = "product_destiny";

    public final static String SORT_FIELD = "createdAt";

    public final static String ID_COL = "id";
    public final static String MIN_EAN_VALUE_COL = "min_ean_value";
    public final static String MAX_EAN_VALUE_COL = "max_ean_value";
    private final static String ADDRESS_COL = "address";
    private final static String TYPE_COL = "destiny_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COL)
    private Long id;

    @Column(name = MIN_EAN_VALUE_COL, nullable = false)
    private int minEanValue;

    @Column(name = MAX_EAN_VALUE_COL, nullable = false)
    private int maxEanValue;

    @Column(name = ADDRESS_COL, length = 200)
    private String address;

    @Column(name = TYPE_COL, nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private DestinyTypeEnum type;
}
