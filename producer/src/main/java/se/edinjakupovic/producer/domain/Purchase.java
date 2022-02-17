package se.edinjakupovic.producer.domain;


import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class Purchase {
    UUID productId;
    double price;
    long purchasedAt;
}
