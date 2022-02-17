package se.edinjakupovic.consumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import se.edinjakupovic.consumer.domain.Purchase;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class MessageConsumer {
    private CountDownLatch latch = new CountDownLatch(1);

    @KafkaListener(containerFactory = "kafkaListenerContainerFactory",
    topics = "${kafka.topic}")
    public void onPurchase(Purchase purchase) {
        log.info("Got message {}", purchase);
        latch.countDown();
    }
}
