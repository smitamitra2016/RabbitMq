package com.smita;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RabbitMqApplication.class)
public class RabbitMqApplicationTests {

	@Bean
	public CachingConnectionFactory testConnectionFactory() {
		CachingConnectionFactory connection = new CachingConnectionFactory("localhost");
		connection.setUsername("guest");
		connection.setPassword("guest");
		return connection;
	}
	
	private Queue testQueue1;
	
	private Queue testQueue2;
	
	private DirectExchange testExchange;
	
	private Binding testBinding1;
	
	private Binding testBinding2;
	
	
	@Bean
	public AmqpTemplate testTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(testConnectionFactory());
		return rabbitTemplate;
	}
	
	@Bean
	public RabbitAdmin admin() {
		RabbitAdmin admin = new RabbitAdmin(testConnectionFactory());
		return admin;
	}
	
	@Before
	public void initialise(){
		testQueue1 = new Queue("testQ1");
		testQueue2 = new Queue("testQ2");
		testExchange = new DirectExchange("TestExchange");
		testBinding1 = BindingBuilder.bind(testQueue1).to(testExchange).with("Key1");
		testBinding2 = BindingBuilder.bind(testQueue2).to(testExchange).with("Key2");
		admin().declareQueue(testQueue1);
		admin().declareQueue(testQueue2);
		admin().declareExchange(testExchange);
		admin().declareBinding(testBinding1);
		admin().declareBinding(testBinding2);
	}
	
	@After
	public void destroy(){
		if(testExchange!=null){
			admin().deleteExchange("TestExchange");
		}
		if(testQueue1!=null){
			admin().deleteQueue("testQ1");
		}
		if(testQueue2!=null){
			admin().deleteQueue("testQ2");
		}
	}
	
	@Test
	public void testQueueMessage(){
		testTemplate().convertAndSend("TestExchange","Key1","Message to Queue 1");
		assertEquals(testTemplate().receiveAndConvert("testQ1"), "Message to Queue 1");
		testTemplate().convertAndSend("TestExchange","Key2","Message to Queue 2");
		assertEquals(testTemplate().receiveAndConvert("testQ2"), "Message to Queue 2");
	}
	
	

}
