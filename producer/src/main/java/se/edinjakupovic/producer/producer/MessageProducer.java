package se.edinjakupovic.producer.producer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import se.edinjakupovic.producer.domain.Purchase;
import se.edinjakupovic.producer.domain.PurchaseGenerator;

@Slf4j
public class MessageProducer {

    private final KafkaTemplate<String, Purchase> template;
    private final String topic;
    private final PurchaseGenerator purchaseGenerator;

    public MessageProducer(KafkaTemplate<String, Purchase> template, String topic, PurchaseGenerator purchaseGenerator) {
        this.template = template;
        this.topic = topic;
        this.purchaseGenerator = purchaseGenerator;
    }

    public void sendPurchaseEvent() {
        var purchase = purchaseGenerator.randomPurchase();
        log.info("Sending event {}", purchase);
        template.send(topic, purchase);
    }

}
