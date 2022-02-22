package com.pkglobal.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.pkglobal.models.Customer;

@Configuration
public class KafkaProducerConfig {

	
	@Bean
	public ProducerFactory<String, Customer> producerFactory(){
		Map<String,Object> producerConfig=new HashMap<>();
		producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
		return new DefaultKafkaProducerFactory<>(producerConfig);
	}
	
	@Bean
	public KafkaTemplate<String, Customer> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
}
