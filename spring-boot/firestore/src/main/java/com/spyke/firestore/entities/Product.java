package com.spyke.firestore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;

    private String name;

    private String description;

    private Double price;

    private List<PriceHistory> previousPrices;
}
