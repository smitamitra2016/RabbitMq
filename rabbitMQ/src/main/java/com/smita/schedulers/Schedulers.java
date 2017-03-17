package com.smita.schedulers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.smita.entities.Location;
import com.smita.helpers.LatitudeGenerator;

@Component
public class Schedulers {
	@Autowired
	private AmqpTemplate template;
	
	@Autowired
	private RestTemplate restTemplate;
	

	@Scheduled(fixedDelay=1000)
	public void directExchangeScheduler() {
		double value = Math.random();
		template.convertAndSend("E2","Key2",Double.toString(value));
	}
	
	@Scheduled(fixedDelay=10000)
	public void workQueues(){
		Random r = new Random();
		char c1 = (char)(r.nextInt(26) + 'a');
		char c2 = (char)(r.nextInt(26) + 'a');
		template.convertAndSend("E2","Key1",String.valueOf(c1+" "+c2));
	}
	
	@Scheduled(fixedDelay=5000)
	public void broadcastTime(){
		template.convertAndSend("E1","",String.valueOf(Calendar.getInstance().getTime()));
	}
	
	@Scheduled(fixedDelay=20000)
	public void broadcastLocation(){
		Map<String,String> coords = new HashMap<String,String>();
		coords.put("latitude", LatitudeGenerator.getLatitide());
		coords.put("longitude", "-1.81602098644987");
		Location locDetails = restTemplate.getForObject("http://nominatim.openstreetmap.org/reverse?format=json&lat={latitude}&lon={longitude}&addressdetails=1", Location.class, coords);
		template.convertAndSend("E1","",locDetails.toString());
	}
}
