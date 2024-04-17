package com.devex.SportsScrapper.configuration;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Component("sportsPropertiesService")
public class SportsPropertiesService 
{
	Logger log = LoggerFactory.getLogger(SportsPropertiesService.class);
	
	@Autowired
	TeamsMappingProperties teamsMappingProperties;
	
	@Resource(name = "teamsMappingAllProperties")
	private Map<String, String> teamsMappingAllProperties;
	
	public TeamsMappingProperties getTeamsMappingProperties() {
		return teamsMappingProperties;
	}

	public Map<String, String> getTeamsMappingAllProperties() {
		return teamsMappingAllProperties;
	}

	@PostConstruct
	public void propertiesTest()
	{
		log.info("Lucknow Super Giants League "+teamsMappingProperties.getProperty("Lucknow Super Giants"));
		log.info("teamsMappingAllProperties "+teamsMappingAllProperties);
	}
}
