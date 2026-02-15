package com.idream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/first")
public class FirstController {

	@Autowired
	private RestClient restClient;

	@Autowired
	private Environment environment;

	private static final Logger LOGGER = LoggerFactory.getLogger(FirstController.class);
	
	@GetMapping("/get-data")
	public String getData() {
		LOGGER.info("*----------- First micro service call starts here -----------*");
		String uri = environment.getProperty("second.service.end-point", "");
		LOGGER.info("second.service.end-point url = " + uri);
		ResponseEntity<String> response = restClient
											.post()
											.uri(uri)
											.retrieve()
											.toEntity(String.class);
		
		LOGGER.info("Response received from micro service B -> " + response.getBody());
		String resp = "response from first micro service + " + response.getBody();
		LOGGER.info("Response sent from micro service A -> " + resp);
		LOGGER.info("*----------- First micro service call ends here -----------*");
		return "response from first micro service + " + response.getBody();
	}
}