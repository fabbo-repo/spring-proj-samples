package com.spyke.stripe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistory {
    @Id
    @Column
    private UUID id;
    private boolean paymentStatus;
    private LocalDate date;
}
