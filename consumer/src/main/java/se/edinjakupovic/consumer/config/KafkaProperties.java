package se.edinjakupovic.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(String groupId, String topic, String bootstrapAddress, int consumers) { }