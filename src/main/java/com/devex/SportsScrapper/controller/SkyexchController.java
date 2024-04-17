package com.devex.SportsScrapper.controller;

import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SkyexchController 
{
	Logger log = LoggerFactory.getLogger(SkyexchController.class);
	
	//@PostConstruct
	public void test()
	{
		//log.info("Match ==> "+getMatchUrl());
		//getMatchContent();
		//todaysMatches();
		inPlayMatches();
	}
	
	public String getMatchUrl()
	{
		String matchUrl = null;
		
		try 
		{
			String url = "https://www.skyexch.art/exchange/member/inplay/index.jsp#InPlay";
			
			 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

			 ChromeOptions options = new ChromeOptions();
		        options.addArguments("--headless");
			 
		        // Create a new instance of the Chrome driver
		        WebDriver driver = new ChromeDriver(options);

		        // Navigate to the Angular-based website
		        driver.get(url);

		        // Wait for the dynamic content to load
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
		        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("eventType")));

		        // Extract the content
		        String content = element.getText();

		        // Output the content
		        System.out.println(content);
		        
		        
		        //find all the anchor elements from the WebElement
		        
		        List<WebElement> anchors = element.findElement(By.xpath("./ancestor::div[1]")).findElements(By.xpath(".//a[@id='vsName']"));

		        
		        System.out.println("parent id  "+element.findElement(By.xpath("./ancestor::div[1]")).getAttribute("id"));
		        
		        //print size of anchors
		        System.out.println(anchors.size());
		        	
		        //print text content of the anchor elements
		        for (WebElement anchor : anchors) 
                {
                    System.out.println(anchor.getText());
                    System.out.println(anchor.getAttribute("href"));
                    System.out.println(anchor.getAttribute("innerHTML"));
                    String teamsLable = anchor.getAttribute("innerHTML");
                    String teams [] = teamsLable.split("<span>v</span>");
                    if(isIplTeam(teams[0]) || isIplTeam(teams[1]))
                    {
                    	matchUrl =  anchor.getAttribute("href");
                    }
                    
                }
		        // Close the browser
		        driver.quit();
		        return matchUrl;
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return matchUrl;
		}
	}
	
	public void getMatchContent2()
	{
		try 
		{
			//String url = getMatchUrl();
			String url = "https://iceexch.com/sport/event-detail/33195179";
			log.info("Processing ==> " + url);
			
			 if(null!=url)
			 {
				 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

				 ChromeOptions options = new ChromeOptions();
			     options.addArguments("--headless");
				 
		        // Create a new instance of the Chrome driver
		        WebDriver driver = new ChromeDriver(options);

		        // Navigate to the webpage
		        driver.get(url);
		        
		        //System.out.println(driver.getPageSource());
		        
//		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//		        
//		        wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));

		     // Define a custom JavaScript function to check for the completion of JavaScript execution
		        String script = "return document.readyState === 'complete';";

		        // Use FluentWait to wait until the JavaScript execution is complete
		        Wait<WebDriver> wait = new FluentWait<>(driver)
		                .withTimeout(Duration.ofSeconds(30))
		                .pollingEvery(Duration.ofSeconds(3))
		                .ignoring(Exception.class);

		        wait.until(webDriver -> {
		            JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		            return (Boolean) executor.executeScript(script);
		        });

		        
		        WebElement parentElement = driver.findElement(By.id("matchTrackerWidget"));
		        // Locate the parent element
		       // WebElement parentElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("matchTrackerWidget")));
		        
		       // WebElement specificElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sr-simcrick-scb__wrapper")));
		        
		        System.out.println(driver.getPageSource());
		        
		        // Find all child div elements within the parent element
		        List<WebElement> childDivs = parentElement.findElements(By.xpath(".//div"));

		        // Extract and print the text inside each child div
		        for (WebElement childDiv : childDivs) {
		            System.out.println(childDiv.getText());
		        }

		        // Close the browser
		        driver.quit();
			 }
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void getMatchContent()
	{
		try 
		{
			//String url = getMatchUrl();
			//String url = "https://iceexch.com/sport/event-detail/33195179";
			String url = "https://iceexch.com/sport/event-detail/33181108";
			log.info("Processing ==> " + url);
			
			 if(null!=url)
			 {
				 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

				 ChromeOptions options = new ChromeOptions();
			     options.addArguments("--headless");
				 
		        // Create a new instance of the Chrome driver
		        WebDriver driver = new ChromeDriver();

		        // Navigate to the webpage
		        driver.get(url);
		        
		        
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//		        wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//  
//		        WebElement element = driver.findElement(By.tagName("app-live-score")); // Replace "button" with the actual tag name you're targeting
//		        WebElement btn = element.findElement(By.tagName("button"));
//		        		btn.click();
//		        		btn.click();
//		        		btn.click();
//		        		
//		        		
//		        		
//		        
//		       WebElement specificElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("sr-simcrick-scb__wrapper")));
//		        
//		        
//		        // Find all child div elements within the parent element
//		        List<WebElement> childDivs = specificElement.findElements(By.xpath(".//div"));
//
//		        // Extract and print the text inside each child div
//		        for (WebElement childDiv : childDivs) {
//		           //System.out.println(" ===> "+childDiv.getText().replace("\n", ""));
//		            if(childDiv.getText().startsWith("INN"))
//		            {
//		            	 String inn[] = childDiv.getText().replace("\n", "").split("\\|");
//		            	 System.out.println("INNING ===> "+inn[0]);
//		            	 System.out.println("OVER ===> "+inn[1]);
//		            }
//		            
//		        }
		        
		        
		        WebElement oddsDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("event_card")));
		        
		        List<WebElement> teamOdds = oddsDiv.findElements(By.className("card_event"));
		        
		        for (WebElement childDiv : teamOdds) 
		        {
		        	//wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".rating li")));
			        
		        	
		        	//System.out.println("ODDs ===> "+childDiv.getText().replace("\n", ""));
		        	//System.out.println(childDiv.findElement(By.className("rating")).getText().replace("\n", ""));
		        	WebElement teamsDiv = childDiv.findElement(By.cssSelector(".teams h2"));
		        	System.out.println("team ===> "+teamsDiv.getText().replace("\n", ""));
		        	
		        	Thread.sleep(5000);
		        	
		        	List<WebElement> oddList = childDiv.findElements(By.cssSelector(".rating li"));
		        	System.out.println("oddList "+oddList.size());
		        	System.out.println("back ===> "+oddList.get(2).findElement(By.tagName("h2")).getText().replace("\n", ""));
		        	System.out.println("lay ===> "+oddList.get(3).findElement(By.tagName("h2")).getText().replace("\n", ""));
		        	
		        }
		        
		        // Close the browser
		        driver.quit();
			 }
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void todaysMatches()
	{
		try 
		{

			//String url = getMatchUrl();
			//String url = "https://iceexch.com/sport/event-detail/33195179";
			String url = "https://iceexch.com/sport";
			log.info("Processing ==> " + url);
			
			 if(null!=url)
			 {
				 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

				 ChromeOptions options = new ChromeOptions();
			     options.addArguments("--headless");
				 
		        // Create a new instance of the Chrome driver
		        WebDriver driver = new ChromeDriver();

		        // Navigate to the webpage
		        driver.get(url);
		        
		        List<WebElement> navList = driver.findElements(By.cssSelector("nav .nav li a"));
	
		        System.out.println("navList.size() "+navList.size());
		        
		        
		        //navList.stream().forEach(n -> System.out.println(n.getText()));
		        //filter element who has text Today
		        WebElement todayNav = navList.stream().filter(n -> n.getText().trim().toLowerCase().contains("today")).findFirst().get();
		    
		        Thread.sleep(5000);
		        
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30)); 
		        wait.until(ExpectedConditions.elementToBeClickable(todayNav)); 
		        
		        todayNav.findElement(By.xpath("..")).click();
		        todayNav.findElement(By.xpath("..")).click();
		        
		        // Close the browser
		        driver.quit();
			 }
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	
	public void inPlayMatches()
	{
		try 
		{

			//String url = getMatchUrl();
			//String url = "https://iceexch.com/sport/event-detail/33195179";
			String url = "https://iceexch.com/sport";
			log.info("Processing ==> " + url);
			
			 if(null!=url)
			 {
				 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

				 ChromeOptions options = new ChromeOptions();
			     options.addArguments("--headless");
				 
		        // Create a new instance of the Chrome driver
		        WebDriver driver = new ChromeDriver();

		        // Navigate to the webpage
		        driver.get(url);
		        
		        Thread.sleep(3000);
		        
		        List<WebElement> sportsList = driver.findElements(By.cssSelector("app-market-card .games-heading"));
	
		        System.out.println("sportsList.size() "+sportsList.size());
		        
		        WebElement cricketCard = sportsList.stream().filter(n -> n.getText().trim().toLowerCase().contains("cricket")).findFirst().get();
			    
		       // System.out.println( cricketCard.findElement(By.xpath("..")).getText());
		        
		        WebElement cricketCardDiv = cricketCard.findElement(By.xpath(".."));
		        
		        List<WebElement> allMatches = cricketCardDiv.findElements(By.cssSelector(".teams h2"));
		        
		        System.out.println("allMatches.size() "+allMatches.size());
		        
		        for(WebElement match : allMatches)
		        {
		        	String teams[] = match.getText().trim().split("V/S");
		        	if(isIplTeam(teams[0]) || isIplTeam(teams[1]))
		        	{
		        		match.findElement(By.xpath("..")).click();
		        	}
		        }
		        
		        log.info("redirected to "+driver.getCurrentUrl());
		        
		        //keepGettingMatchDetails(driver.getCurrentUrl());
		        getMatchDetails(driver.getCurrentUrl());
		        //while time is less than 1 am keep calling method at every 2 seconds
		        
		        
		        
		        // Close the browser
		        driver.quit();
			 }
		
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void keepGettingMatchDetails(String url)
	{
		 Calendar now = Calendar.getInstance();
		 System.out.println("HOUR_OF_DAY > "+now.get(Calendar.HOUR_OF_DAY));
	        if (now.get(Calendar.HOUR_OF_DAY) > 19) {
	            // Schedule the task to run every 2 seconds
	            Timer timer = new Timer();
	            TimerTask task = new TimerTask() {
	                @Override
	                public void run() {
	                    // Call your method here
	                    System.out.println("Method called at: " + Calendar.getInstance().getTime());
	                    getMatchDetails(url);
	                }
	            };
	            // Schedule the task to run every 2 seconds
	            timer.schedule(task, 0, 2000); // 0 initial delay, 2000 milliseconds = 2 seconds
	        }
	}
	
	public void getMatchDetails(String url)
	{
		try 
		{
			
			log.info("Processing ==> " + url);
			
			 if(null!=url)
			 {
				 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

				 ChromeOptions options = new ChromeOptions();
			     options.addArguments("--headless");
				 
		        // Create a new instance of the Chrome driver
		        WebDriver driver = new ChromeDriver();

		        // Navigate to the webpage
		        driver.get(url);
		        
		        
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		        wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
  
		        WebElement element = driver.findElement(By.tagName("app-live-score")); // Replace "button" with the actual tag name you're targeting
		        WebElement btn = element.findElement(By.tagName("button"));
		        		btn.click();
		        		btn.click();
		        		btn.click();
		        		
		      // Thread.sleep(2000); 		
		        		
		        		
		       contetReader(wait);
		        
		        // Close the browser
		       driver.quit();
			 }
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	public void getMatchDetailsRecursive(String url)
	{
		try 
		{
			
			log.info("Processing ==> " + url);
			
			 if(null!=url)
			 {
				 System.setProperty("webdriver.chrome.driver", "C:\\Users\\aarunsaw\\Downloads\\chromedriver-win64\\chromedriver.exe");

				 ChromeOptions options = new ChromeOptions();
			     options.addArguments("--headless");
				 
		        // Create a new instance of the Chrome driver
		        WebDriver driver = new ChromeDriver();

		        // Navigate to the webpage
		        driver.get(url);
		        
		        
		        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		        wait.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
  
		        WebElement element = driver.findElement(By.tagName("app-live-score")); // Replace "button" with the actual tag name you're targeting
		        WebElement btn = element.findElement(By.tagName("button"));
		        		btn.click();
		        		btn.click();
		        		btn.click();
		        		
		        		
		       contetReader(wait);
		        
		        // Close the browser
		       driver.quit();
			 }
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
	}

	private void contetReader(WebDriverWait wait) throws InterruptedException 
	{
		Thread.sleep(15000);
		WebElement specificElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("slideScore")));
		    
		    
		    // Find all child div elements within the parent element
		    List<WebElement> childDivs = specificElement.findElements(By.xpath(".//div"));

		    // Extract and print the text inside each child div
		    for (WebElement childDiv : childDivs) {
		       //System.out.println(" ===> "+childDiv.getText().replace("\n", ""));
		        if(childDiv.getText().startsWith("INN"))
		        {
		        	 String inn[] = childDiv.getText().replace("\n", "").split("\\|");
		        	 System.out.println("INNING ===> "+inn[0]);
		        	 System.out.println("OVER ===> "+inn[1]);
		        }
		        
		    }
		    
		    WebElement oddsDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("event_card")));
		    
		    List<WebElement> teamOdds = oddsDiv.findElements(By.className("card_event"));
		    
		    for (WebElement childDiv : teamOdds) 
		    {
		    	WebElement teamsDiv = childDiv.findElement(By.cssSelector(".teams h2"));
		    	System.out.println("team ===> "+teamsDiv.getText().replace("\n", ""));
		    	
		    	Thread.sleep(5000);
		    	
		    	List<WebElement> oddList = childDiv.findElements(By.cssSelector(".rating li"));
		    	System.out.println("oddList "+oddList.size());
		    	System.out.println("back ===> "+oddList.get(2).findElement(By.tagName("h2")).getText().replace("\n", ""));
		    	System.out.println("lay ===> "+oddList.get(3).findElement(By.tagName("h2")).getText().replace("\n", ""));
		    	
		    }
	}
	
	
	public boolean isIplTeam(String team)
	{
		List<String> iplTeams = List.of(
	            "Royal Challengers Bengaluru",
	            "Chennai Super Kings",
	            "Mumbai Indians",
	            "Kolkata Knight Riders",
	            "Delhi Capitals",
	            "Sunrisers Hyderabad",
	            "Rajasthan Royals",
	            "Punjab Kings",
	            "Gujarat Lions",
	            "Lucknow Super Giants",
	            "Metro Cc"
	        );
		
		//return true if team is available in list of teams
		return iplTeams.contains(team);
	}
	
	
}
