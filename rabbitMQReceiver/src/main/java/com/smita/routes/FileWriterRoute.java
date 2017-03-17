package com.smita.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileWriterRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("rabbitmq://localhost/E2?routingKey=Key1&queue=Q1&connectionFactory=#connectionFactory&autoDelete=false&autoAck=true")
		.to("bean:showData?method=addDelimiter").to("file:///C:\\Users\\Nomad Digital\\project\\test\\outbox?fileName=random.txt&fileExist=Append").end();

		from("rabbitmq://localhost/E2?routingKey=Key2&queue=Q2&connectionFactory=#connectionFactory&autoDelete=false&autoAck=true")
		.to("bean:showData?method=printData").end();
		
		from("rabbitmq://localhost/E1?queue=Q4&connectionFactory=#connectionFactory&autoDelete=false&autoAck=true&exchangeType=fanout")
		.to("bean:showData?method=addNewLine").to("file:///C:\\Users\\Nomad Digital\\project\\test\\outbox?fileName=timeLocation.txt&fileExist=Append").end();;
	}

}
