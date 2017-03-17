package com.smita.configs;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	public CachingConnectionFactory connectionFactory() {
		CachingConnectionFactory connection = new CachingConnectionFactory("localhost");
		connection.setUsername("guest");
		connection.setPassword("guest");
		return connection;
	}

	
	@Bean
	public AmqpTemplate template() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		return rabbitTemplate;
	}
	/**
	 * For direct binding
	 * @return
	 */
	
	
	@Bean
	public Queue queue() {
		return new Queue("Q2");
	}
	
	@Bean
	public Queue queue1() {
		return new Queue("Q1");
	}
	
	@Bean
	public DirectExchange directExchange(){
		return new DirectExchange("E2");
	}
	
	@Bean
	public Binding binding2() {
		return BindingBuilder.bind(queue()).to(directExchange()).with("Key2");
	}
	@Bean
	public Binding binding1() {
		return BindingBuilder.bind(queue1()).to(directExchange()).with("Key1");
	}
	/**
	 * End of direct binding
	 * @return
	 */
	
	/**
	 * For fanout binding/broadcasting
	 */
	
	
	@Bean
	public FanoutExchange fanoutExchange(){
		return new FanoutExchange("E1");
	}
	@Bean
	public Queue queue3() {
		return new Queue("Q3");
	}
	
	@Bean
	public Queue queue4() {
		return new Queue("Q4");
	}
	
	@Bean
	public Binding binding3() {
		return BindingBuilder.bind(queue3()).to(fanoutExchange());
	}
	@Bean
	public Binding binding4() {
		return BindingBuilder.bind(queue4()).to(fanoutExchange());
	}
	/**
	 * End of fanout binding/broadcasting
	 */
	

	@Bean
	public RabbitAdmin admin() {
		RabbitAdmin admin = new RabbitAdmin(connectionFactory());
		admin.declareQueue(queue());
		admin.declareQueue(queue1());
		admin.declareQueue(queue3());
		admin.declareQueue(queue4());
		admin.declareExchange(directExchange());
		admin.declareExchange(fanoutExchange());
		admin.declareBinding(binding2());
		admin.declareBinding(binding1());
		admin.declareBinding(binding3());
		admin.declareBinding(binding4());
		return admin;
	}

}
