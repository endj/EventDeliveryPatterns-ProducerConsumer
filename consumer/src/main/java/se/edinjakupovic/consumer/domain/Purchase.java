package se.edinjakupovic.consumer.domain;

import java.util.UUID;

public record Purchase(UUID productId, double price, long purchasedAt) {}

