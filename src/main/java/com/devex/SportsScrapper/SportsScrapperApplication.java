package com.devex.SportsScrapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.devex.SportsScrapper.controller.IceExchangeController;

import jakarta.annotation.PostConstruct;

@EnableMongoAuditing
@SpringBootApplication
public class SportsScrapperApplication {
	
	static Logger log = LoggerFactory.getLogger(SportsScrapperApplication.class);
	
	static IceExchangeController iceExchangeControllerRef;
	
	@Autowired
	IceExchangeController iceExchangeController;
	
	@PostConstruct
	public void init()
	{
		SportsScrapperApplication.iceExchangeControllerRef = iceExchangeController;
	}
	

	public static void main(String[] args) {
		SpringApplication.run(SportsScrapperApplication.class, args);
		
		log.info("SportsScrapperApplication args ==> "+List.of(args));
		
		if(List.of(args).contains("play"))
		{
			iceExchangeControllerRef.inPlayMatches();
		}
	}

}
