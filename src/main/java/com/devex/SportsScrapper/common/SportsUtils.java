package com.devex.SportsScrapper.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.devex.SportsScrapper.configuration.SportsPropertiesService;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Component
public class SportsUtils 
{
	static Logger log = LoggerFactory.getLogger(SportsUtils.class);
	
	static SportsPropertiesService sportsPropertiesService;
	
	@Resource(name = "sportsPropertiesService")
	SportsPropertiesService sportsPropertiesServiceRef;
	
	@PostConstruct     
	private void setSportsPropertiesService () 
	{
		SportsUtils.sportsPropertiesService = sportsPropertiesServiceRef;
	}
	
	public static String getMatchCode(String url)
	{
		List<String> urlComponents  = List.of(url.split("\\/"));
		return urlComponents.get(urlComponents.size()-1);
	}
	
	public static boolean isValidTeam(String team)
	{
		Set<String> iplTeams = sportsPropertiesService.getTeamsMappingAllProperties().keySet();
		
		//return true if team is available in list of teams
		return iplTeams.contains(team);
	}
	
	public static boolean isMatchTime()
	{
		Calendar now = Calendar.getInstance();
		
		String hr = String.valueOf(now.get(Calendar.HOUR_OF_DAY)).length() > 1 ? String.valueOf(now.get(Calendar.HOUR_OF_DAY)) : "0"+now.get(Calendar.HOUR_OF_DAY);
		 
		String min = String.valueOf(now.get(Calendar.MINUTE)).length() > 1 ? String.valueOf(now.get(Calendar.MINUTE)) : "0"+now.get(Calendar.MINUTE);
		 
		String timeToCheck = hr+":"+min; 

        LocalTime givenTime = LocalTime.parse(timeToCheck, DateTimeFormatter.ofPattern("HH:mm"));

        LocalTime startTime = LocalTime.of(13, 30); // 7:30 PM
        LocalTime endTime = LocalTime.of(1, 0); // 1 AM

        boolean isWithinRange = givenTime.isAfter(startTime) &&  !LocalTime.now().equals(LocalTime.MIDNIGHT);
        
        if(!isWithinRange)
        {
        	log.info("timeToCheck "+timeToCheck);
            log.info("isWithinRange "+isWithinRange);
        }
        
        return isWithinRange;
	}
}
