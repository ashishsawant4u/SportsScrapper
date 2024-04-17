package com.devex.SportsScrapper.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@Configuration
@PropertySource("classpath:teams-mapping.properties")
public class TeamsMappingProperties 
{
	@Autowired
	private Environment env;

	public String getProperty(String key) 
	{
		return env.getProperty(key);
	}
	
	@Bean
	public static PropertiesFactoryBean teamsMappingAllProperties() 
	{
	    PropertiesFactoryBean bean = new PropertiesFactoryBean();
	    bean.setLocation(new ClassPathResource("teams-mapping.properties"));
	    return bean;
	}

}
