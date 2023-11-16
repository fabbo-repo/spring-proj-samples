package com.mercadonatest.eanapi.modules.product.domain.models.entities;

import com.mercadonatest.eanapi.core.models.Auditable;
import com.mercadonatest.eanapi.modules.provider.domain.models.entities.ProviderJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = ProductJpaEntity.TABLE_NAME)
public class ProductJpaEntity extends Auditable<String> {
    public final static String TABLE_NAME = "product";

    public final static String SORT_FIELD = "createdAt";

    public final static String ID_COL = "id";
    private final static String NAME_COL = "name";
    private final static String DESCRIPTION_COL = "description";
    private final static String PRICE_COL = "price";
    private final static String STOCK_COL = "stock";
    public final static String EAN_VALUE_COL = "ean_value";
    private final static String PROVIDER_ID_COL = "product_provider_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID_COL)
    private Long id;

    @Column(name = NAME_COL, nullable = false, length = 20)
    private String name;

    @Column(name = DESCRIPTION_COL, length = 200)
    private String description;

    @Column(name = PRICE_COL, nullable = false)
    private double price;

    @Column(name = STOCK_COL, nullable = false)
    private long stock;

    @Column(name = EAN_VALUE_COL, nullable = false)
    private int eanValue;

    @ManyToOne
    @JoinColumn(name = PROVIDER_ID_COL, referencedColumnName = ProviderJpaEntity.ID_COL, nullable = false)
    private ProviderJpaEntity provider;
}
