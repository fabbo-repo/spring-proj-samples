package com.spyke.firestore.repositories;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.WriteBatch;
import com.google.firebase.cloud.FirestoreClient;
import com.spyke.firestore.entities.PriceHistory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class PriceHistoryRepository {
    public void initializeData(final String name) {
        final Firestore firestore = FirestoreClient.getFirestore();

        final WriteBatch writeBatch = firestore.batch();

        final Random r = new Random();
        for (int i = 0; i < 20; i++) {
            final DocumentReference document = firestore
                    .collection(name).document();
            writeBatch
                    .set(
                            document,
                            PriceHistory
                                    .builder()
                                    .price(0 + (100 - 0) * r.nextDouble())
                                    .epoch(randomLocalDateTime().toInstant(ZoneOffset.UTC).toEpochMilli())
                                    .build()
                    );
        }
        writeBatch.commit();
    }

    public List<PriceHistory> findLastFiveProductPrices(final String name)
            throws ExecutionException, InterruptedException {
        final Firestore firestore = FirestoreClient.getFirestore();

        return firestore
                .collection(name)
                .orderBy("epoch", Query.Direction.DESCENDING)
                .limit(5)
                .get()
                .get()
                .toObjects(PriceHistory.class);
    }

    private LocalDateTime randomLocalDateTime() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDateTime.of(LocalDate.ofEpochDay(randomDay), LocalTime.of(0, 0));
    }
}
