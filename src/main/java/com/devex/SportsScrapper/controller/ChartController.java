package com.devex.SportsScrapper.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devex.SportsScrapper.CricketMatchOddsModel;
import com.devex.SportsScrapper.repository.IplMatchOddsRepository;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/chart")
public class ChartController 
{
	@Resource(name = "iplMatchOddsRepository")
	IplMatchOddsRepository iplMatchOddsRepository;
	
	@RequestMapping("/{matchCode}")
	@ResponseBody
	public List<CricketMatchOddsModel> getChart(@PathVariable String matchCode)
	{
		return iplMatchOddsRepository.findByMatchCode(matchCode);
	}
	
	@RequestMapping("/view")
	public String chartLandingPage()
	{
		return "chartLandingPage";
	}
}
