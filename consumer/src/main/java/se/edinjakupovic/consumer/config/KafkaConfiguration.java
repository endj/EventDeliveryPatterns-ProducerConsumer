package se.edinjakupovic.consumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import se.edinjakupovic.consumer.consumer.MessageConsumer;
import se.edinjakupovic.consumer.domain.Purchase;

import java.util.HashMap;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.springframework.kafka.support.serializer.JsonDeserializer.*;


@Configuration
public class KafkaConfiguration {

    @Bean
    public RecordMessageConverter converter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public ConsumerFactory<String, Purchase> consumerFactory(KafkaProperties kafkaProperties) {
        var props = new HashMap<String, Object>();
        props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapAddress());
        props.put(GROUP_ID_CONFIG, kafkaProperties.groupId());
        props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(VALUE_DEFAULT_TYPE, "se.edinjakupovic.consumer.domain.Purchase");
        props.put(USE_TYPE_INFO_HEADERS, false);
        props.put(TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Purchase> kafkaListenerContainerFactory(
            ConsumerFactory<String, Purchase> consumerFactory, KafkaProperties kafkaProperties) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Purchase>();
        factory.setConcurrency(kafkaProperties.consumers());
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public MessageConsumer messageConsumer() {
        return new MessageConsumer();
    }
}
