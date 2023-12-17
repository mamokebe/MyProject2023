package com.mystore.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.mystore.actiondriver.Action;
import com.mystore.utility.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	//private static Actions action;
	
	public static Properties prop;
	//public static WebDriver driver;
	
	//Declare ThreadLocal Driver
	public static ThreadLocal<RemoteWebDriver> driver= new ThreadLocal<>();
	
	@BeforeSuite(groups = {"Smoke","Sanity","Regression"})
	public void loadConfig() {
		ExtentManager.setExtent();
		DOMConfigurator.configure("log4j.xml");
		
		try {
			prop = new Properties();
			System.out.println("super construction invoked");
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"\\Configuration\\config.properties");
			prop.load(ip);
			System.out.println("driver: " + driver);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static  WebDriver getDriver() {
		//Get Driver from threadLocalMap
		return driver.get();
		}
	
	public static void launchApp(String browserName) throws Throwable {
		//WebDriverManager.chromedriver().setup();
		//String browserName = prop.getProperty("browser");
		if (browserName.contains("Chrome")) {
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver();	
			//Set Browser to ThreadLocalMap
			driver.set(new ChromeDriver());
		} else if (browserName.contains("FireFox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
		} else if (browserName.contains("IE")) {
			WebDriverManager.iedriver().setup();
			//driver = new InternetExplorerDriver();
			driver.set(new InternetExplorerDriver());
		}
		
		//getDriver().manage().window().maximize();
		//Action.implicitWait(getDriver(), 10);
		//Action.pageLoadTimeOut(getDriver(), 30);
         //getDriver().get(prop.getProperty("url"));
		
		
		  //Maximize the screen 
		getDriver().manage().window().maximize(); 
		//Delete all the cookies 
		getDriver().manage().deleteAllCookies(); 
		//Implicit TimeOuts
		 getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		 
		 //implicitlyWait (Integer.parseInt(prop.getProperty("implicitWait")),TimeUnit.SECONDS);
		 //PageLoad TimeOuts 
		 getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
		 
		  //(Integer.parseInt(prop.getProperty("pageLoadTimeOut")),TimeUnit.SECONDS);
		  //Launching the URL 
		 getDriver().get(prop.getProperty("url"));
		 
		
	}
	
	@AfterSuite (groups = {"Smoke","Sanity","Regression"})
	public void afterSuite() {
		ExtentManager.endReport();
	}

	
}
