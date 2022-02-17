package se.edinjakupovic.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import se.edinjakupovic.producer.domain.Purchase;
import se.edinjakupovic.producer.domain.PurchaseGenerator;
import se.edinjakupovic.producer.producer.MessageProducer;

import java.util.HashMap;

import static org.apache.kafka.clients.producer.ProducerConfig.*;
import static org.springframework.kafka.support.serializer.JsonSerializer.ADD_TYPE_INFO_HEADERS;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic topic(KafkaProperties properties) {
        return TopicBuilder.name(properties.topic())
                .partitions(properties.partitions())
                .replicas(properties.replicas())
                .build();
    }

    @Bean
    public ProducerFactory<String, Purchase> producerFactory(KafkaProperties kafkaProperties) {
        System.out.println(kafkaProperties);
        var configProperties = new HashMap<String, Object>();
        configProperties.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapAddress());
        configProperties.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProperties.put(VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProperties);
    }

    @Bean
    public KafkaTemplate<String, Purchase> purchaseProducer(ProducerFactory<String, Purchase> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public MessageProducer messageProducer(KafkaTemplate<String, Purchase> producer, KafkaProperties kafkaProperties,
                                           PurchaseGenerator purchaseGenerator) {
        return new MessageProducer(producer, kafkaProperties.topic(), purchaseGenerator);
    }


}
