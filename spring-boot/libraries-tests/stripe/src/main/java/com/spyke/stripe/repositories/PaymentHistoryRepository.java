package com.spyke.stripe.repositories;

import com.spyke.stripe.entities.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, UUID> {
}
