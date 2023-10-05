package com.spyke.firestore.controllers;

import com.spyke.firestore.entities.PriceHistory;
import com.spyke.firestore.entities.Product;
import com.spyke.firestore.repositories.PriceHistoryRepository;
import com.spyke.firestore.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;

    private final PriceHistoryRepository priceHistoryRepository;

    @PostMapping
    public Product createProduct(@RequestBody Product product) throws ExecutionException, InterruptedException {
        priceHistoryRepository.initializeData(product.getName());
        return productRepository.save(product);
    }

    @GetMapping("/{name}")
    public List<PriceHistory> getLastFiveProductPrices(@PathVariable String name)
            throws ExecutionException, InterruptedException {
        return priceHistoryRepository.findLastFiveProductPrices(name);
    }
}
