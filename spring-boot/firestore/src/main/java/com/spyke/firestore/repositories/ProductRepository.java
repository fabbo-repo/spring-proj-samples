package com.spyke.firestore.repositories;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.spyke.firestore.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class ProductRepository {
    private static final String COLLECTION_NAME = "product";

    public Product save(final Product product) throws ExecutionException, InterruptedException {
        final Firestore firestore = FirestoreClient.getFirestore();

        final DocumentReference document = firestore
                .collection(COLLECTION_NAME).document();
        product.setId(document.getId());
        document.set(product);

        return product;
    }
}
