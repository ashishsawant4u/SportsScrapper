package com.devex.SportsScrapper.controller;
import static com.devex.SportsScrapper.common.SportsUtils.getMatchCode;
import static com.devex.SportsScrapper.common.SportsUtils.isMatchTime;
import static com.devex.SportsScrapper.common.SportsUtils.isOverComplete;
import static com.devex.SportsScrapper.common.SportsUtils.isValidTeam;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.devex.SportsScrapper.CricketMatchOddsModel;
import com.devex.SportsScrapper.configuration.SportsPropertiesService;
import com.devex.SportsScrapper.repository.IplMatchOddsRepository;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Controller
public class IceExchangeController 
{
	Logger log = LoggerFactory.getLogger(IceExchangeController.class);
	
	@Resource(name = "iplMatchOddsRepository")
	IplMatchOddsRepository iplMatchOddsRepository;
	
	@Resource(name = "sportsPropertiesService")
	SportsPropertiesService sportsPropertiesService;
	
	//@PostConstruct
	public void test()
	{
		System.out.println("isOverComplete "+isOverComplete(" 6.6/20 OV"));
		System.out.println("isOverComplete "+isOverComplete(" 6.2/20 OV"));
	}
	
	
	//@PostConstruct
	//cron expression for every day at 19:30
	@Scheduled(cron = "0 30 19 * * *")
	public void inPlayMatches()
	{
		// Create a new instance of the Chrome driver
        WebDriver driver = null;
        
		try 
		{

			String url = "https://iceexch.com/sport";
			log.info("Processing ==> " + url);
			
			 if(null!=url)
			 {
				 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

				 ChromeOptions options = new ChromeOptions();
			     options.addArguments("--headless");
				 options.addArguments("--window-size=1920,1080");
				 options.addArguments("--no-sandbox");
				 options.addArguments("--disable-gpu");
				 options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
			     driver = new ChromeDriver(options);
			     
		        // Navigate to the webpage
		        driver.get(url);
		        
		        Thread.sleep(12000);
		        
		        List<WebElement> sportsList = driver.findElements(By.cssSelector("app-market-card .games-heading"));
	
		        log.info("sportsList.size() "+sportsList.size());
		        
		        WebElement cricketCard = sportsList.stream().filter(n -> n.getText().trim().toLowerCase().contains("cricket")).findFirst().get();
			    
		        WebElement cricketCardDiv = cricketCard.findElement(By.xpath(".."));
		        
		        List<WebElement> allMatches = cricketCardDiv.findElements(By.cssSelector(".teams h2"));
		        
		        log.info("allMatches.size() "+allMatches.size());
		        
		        boolean foundDesiredMatches = false;
		        
		        for(WebElement match : allMatches)
		        {
		        	String teams[] = match.getText().trim().split("V/S");
		        	if(isValidTeam(teams[0]) || isValidTeam(teams[1]))
		        	{
		        		match.findElement(By.xpath("..")).click();
		        		foundDesiredMatches = true;
		        		break;
		        	}
		        }
		        
		        if(foundDesiredMatches)
		        {
		        	log.info("redirected to "+driver.getCurrentUrl());
			        
			        //wait for page to loaded fully
			        Thread.sleep(15000);
			        
			        while(isMatchTime())
			        {
			        	CricketMatchOddsModel matchModel = matchDetails(driver);
			        	//wait for next ball then call again matchDetails
			        	Thread.sleep(20000);
			        	 
			        	 if(isOverComplete(matchModel.getOvers()))
			        	 {
			        		 log.info("Over Complete "+matchModel.getOvers()+" starting new session...");
			        		 //start new browser session once over gets completed
			        		driver.quit();
			        		//wait for next over to start
			        		Thread.sleep(20000);
			        		inPlayMatches();
			        	 }
			        	 
			        }
			        
			        // Close the browser once we are beyond match hours
			        log.info("Closing browser as we are beyond match hours...");
			        driver.quit();
		        }
		        else
		        {
		        	log.info("Did not found desired match, closing browser");
		        	driver.quit();
		        }
		        
		        
		        
			 }
		
		} catch (Exception e) {
			log.info(e.getMessage());
			log.info("something went wrong try again...");
			driver.quit();
			inPlayMatches();
		}
	}
	
	private CricketMatchOddsModel matchDetails(WebDriver driver) throws InterruptedException 
	{
		CricketMatchOddsModel matchModel = new CricketMatchOddsModel(); 
		matchModel.setMatchCode(getMatchCode(driver.getCurrentUrl()));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement specificElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("slideScore")));
		    
		    
		    // Find all child div elements within the parent element
		    List<WebElement> childDivs = specificElement.findElements(By.xpath(".//div"));

		    // Extract and print the text inside each child div
		    for (WebElement childDiv : childDivs) 
		    {
		       //System.out.println(" ===> "+childDiv.getText().replace("\n", ""));
		        if(childDiv.getText().startsWith("INN"))
		        {
		        	 String inn[] = childDiv.getText().replace("\n", "").split("\\|");
		        	 //log.info("INNING ===> "+inn[0]);
		        	 //log.info("OVER ===> "+inn[1]);
		        	 matchModel.setInnings(String.valueOf(inn[0]).trim());
		        	 matchModel.setOvers(String.valueOf(inn[1]).trim());
		        	 break;
		        }
		    }
		    
		    String team1ScoreSelectorA = "app-live-score .sr-simcrick-scb__result.srm-left";
		    String team2ScoreSelectorA = "app-live-score .sr-simcrick-scb__result.srm-right";
		    
		    String team1ScoreSelectorB = ".sr-lmt-plus-21-scb__team-wrapper .srm-left .sr-lmt-plus-21-score__wrapper";
		    String team2ScoreSelectorB = ".sr-lmt-plus-21-scb__team-wrapper .srm-right .sr-lmt-plus-21-score__wrapper";
		    
		    String team1ScoreSelector = team1ScoreSelectorA;
		    String team2ScoreSelector = team2ScoreSelectorA;
		    
		    try 
		    {
		    	driver.findElement(By.cssSelector(team1ScoreSelector));
			} 
		    catch (Exception e) 
		    {
				log.info("failed to get score due to wrong selector trying with another one "+e.getMessage());
				 team1ScoreSelector = team1ScoreSelectorB;
			     team2ScoreSelector = team2ScoreSelectorB;
			}
		    
		    
		    WebElement team1Score = driver.findElement(By.cssSelector(team1ScoreSelector));
		    //log.info("team1Score "+team1Score.getText().replace("\n", ""));
		    
		    WebElement team2Score = driver.findElement(By.cssSelector(team2ScoreSelector));
		    //log.info("team2Score "+team2Score.getText().replace("\n", ""));
		    
		    matchModel.setTeam1Score(team1Score.getText().replace("\n", ""));
            matchModel.setTeam2Score(team2Score.getText().replace("\n", ""));
		    
		    WebElement oddsDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("event_card")));
		    
		    List<WebElement> teamOdds = oddsDiv.findElements(By.className("card_event"));
		    
		    int teamIndex = 1;
		    for (WebElement childDiv : teamOdds) 
		    {
		    	WebElement teamsDiv = childDiv.findElement(By.cssSelector(".teams h2"));
		    	//log.info("team ===> "+teamsDiv.getText().replace("\n", ""));
		    	
		    	List<WebElement> oddList = childDiv.findElements(By.cssSelector(".rating li"));
		    	//System.out.println("oddList "+oddList.size());
		    	String backOdds = oddList.get(2).findElement(By.tagName("h2")).getText().replace("\n", "");
		    	//log.info("back ===> "+backOdds);
		    	String layOdds = oddList.get(3).findElement(By.tagName("h2")).getText().replace("\n", "");
		    	//log.info("lay ===> "+layOdds);
		    	
		    	if(teamIndex == 1)
		    	{
		    		matchModel.setTeam1Name(teamsDiv.getText().replace("\n", ""));
		    		matchModel.setTeam1Back(backOdds);
		    		matchModel.setTeam1Lay(layOdds);
		    		teamIndex++;
		    	}
		    	else
		    	{
		    		matchModel.setTeam2Name(teamsDiv.getText().replace("\n", ""));
		    		matchModel.setTeam2Back(backOdds);
		    		matchModel.setTeam2Lay(layOdds);
		    	}
		    }
		    matchModel.setLeague(sportsPropertiesService.getTeamsMappingAllProperties().get(matchModel.getTeam1Name()));
		    
		    saveIfNotExist(matchModel);
		    
		    return matchModel;
	}
	
	public void saveIfNotExist(CricketMatchOddsModel model)
	{
		List<CricketMatchOddsModel> existing = iplMatchOddsRepository.findByMatchCodeAndInningsAndOvers(model.getMatchCode(), model.getInnings(), model.getOvers());
		
		if(existing.isEmpty())
		{
			log.info(model.toString());
			iplMatchOddsRepository.save(model);
		}
	}
	
	
}
