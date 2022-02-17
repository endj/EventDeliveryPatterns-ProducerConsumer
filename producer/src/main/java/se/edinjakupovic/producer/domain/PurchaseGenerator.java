package se.edinjakupovic.producer.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PurchaseGenerator {
    private final static double PRICE_UPPER_BOUND = 10_000;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    public Purchase randomPurchase() {
        return new Purchase(UUID.randomUUID(), RANDOM.nextDouble(PRICE_UPPER_BOUND), System.currentTimeMillis());
    }
}
