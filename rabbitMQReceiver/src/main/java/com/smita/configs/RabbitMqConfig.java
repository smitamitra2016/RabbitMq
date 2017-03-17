package com.smita.configs;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.ConnectionFactory;
import com.smita.helpers.ShowData;
import com.smita.routes.FileWriterRoute;

@Configuration
public class RabbitMqConfig {

	public ShowData showData() {
		return new ShowData();
	}

	@Bean
	public FileWriterRoute fileWriterRoute() {
		return new FileWriterRoute();
	}

	@Bean
	public SpringCamelContext camelContext(ApplicationContext applicationContext) throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(applicationContext);
		camelContext.addRoutes(fileWriterRoute());
		return camelContext;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory connection = new ConnectionFactory();
		connection.setHost("localhost");
		connection.setUsername("guest");
		connection.setPassword("guest");
		return connection;
	}
	
	
	
}
