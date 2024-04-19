package com.devex.SportsScrapper.controller;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devex.SportsScrapper.CricketMatchOddsModel;
import com.devex.SportsScrapper.configuration.SportsPropertiesService;
import com.devex.SportsScrapper.repository.IplMatchOddsRepository;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/chart")
public class ChartController 
{
	@Resource(name = "iplMatchOddsRepository")
	IplMatchOddsRepository iplMatchOddsRepository;
	
	@Resource(name = "sportsPropertiesService")
	SportsPropertiesService sportsPropertiesService;
	
	@RequestMapping("/{matchCode}")
	@ResponseBody
	public List<CricketMatchOddsModel> getChart(@PathVariable String matchCode)
	{
		List<CricketMatchOddsModel> data = iplMatchOddsRepository.findByMatchCode(matchCode);
		
		BiPredicate<String, String> lessThan3 = (t1b,t2b)-> Double.parseDouble(t1b) <=
				5.0 && Double.parseDouble(t2b) <= 5.0;
		
		return data.stream().filter(d->lessThan3.test(d.getTeam1Back(), d.getTeam2Back())).collect(Collectors.toList());
	}
	
	@RequestMapping("/view/{matchCode}")
	public String chartLandingPage(@PathVariable String matchCode,Model model)
	{
		
		model.addAttribute("matchCode", matchCode);
		
		return "chartLandingPage";
	}
}
