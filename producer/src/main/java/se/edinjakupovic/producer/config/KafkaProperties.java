package se.edinjakupovic.producer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(int replicas, int partitions, String topic, String bootstrapAddress) { }
