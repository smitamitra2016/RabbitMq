package com.smita.helpers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ShowData {
	
	/*@RabbitListener(bindings = @QueueBinding(
	        value = @Queue(value = "Q2"),
	        exchange = @Exchange(value = "E2"),
	        key = "Key2"))*/
	public void printData(String val) {
		System.out.println(val);
	}
	
	public String addDelimiter(String val) {
		return val+" ;; ";
	}
	
	public String addNewLine(String val) {
		return val+" \r\n ";
	}
	
	@RabbitListener(queues={"Q3"})
	public void showTimeAndLocation(String val){
		System.out.println(val);
	}

}
