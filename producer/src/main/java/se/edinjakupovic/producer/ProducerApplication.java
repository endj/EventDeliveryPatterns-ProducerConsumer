package se.edinjakupovic.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import se.edinjakupovic.producer.producer.MessageProducer;

import java.util.Scanner;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class ProducerApplication implements CommandLineRunner {

	private final MessageProducer messageProducer;

	public ProducerApplication(MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var messages = 10;
		log.info("Sending 10 events");
		while(messages--> 0) {
			messageProducer.sendPurchaseEvent();
			Thread.sleep(1000);
		}
		log.info("Done");
	}
}
