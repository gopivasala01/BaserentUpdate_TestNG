package mainPackage;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;


public class RunnerClass {
	
    public static String[][] pendingLeases;
	public static String downloadFilePath;
	public static WebDriverWait wait;
	
	public static String previousRecordCompany;
	public static boolean loggedOut = false;
	public static ExtentSparkReporter htmlReporter;
	 
	public static ExtentReports extent;
    //helps to generate the logs in the test report.
	public static ExtentTest test;
    

    // Use ThreadLocal to store a separate ChromeDriver instance for each thread
    private static ThreadLocal<ChromeDriver> driverThreadLocal = new ThreadLocal<ChromeDriver>();
    private static ThreadLocal<String> baseRentAmountThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> portfolioNameThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> baseRentFromPWThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> failedReasonThreadLocal = new ThreadLocal<>();
    
    
  /*  @BeforeClass
    public static void startReport() {
        // initialize the HtmlReporter
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") +"/ExtentReports/testReport.html");
 
        //initialize ExtentReports and attach the HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
 
 
        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setDocumentTitle("Simple Automation Report");
        htmlReporter.config().setReportName("Base Rent Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        
    }*/
 

    @BeforeMethod
    public boolean setUp(){
        // Set up WebDriverManager to automatically download and set up ChromeDriver
    	//System.setProperty("webdriver.http.factory", "jdk-http-client");
    	try {
    			WebDriverManager.chromedriver().clearDriverCache().setup();
    		 	//WebDriverManager.chromedriver().setup();
    	        RunnerClass.downloadFilePath = AppConfig.downloadFilePath;
    			Map<String, Object> prefs = new HashMap<String, Object>();
    		    // Use File.separator as it will work on any OS
    		    prefs.put("download.default_directory",RunnerClass.downloadFilePath);
    	        ChromeOptions options = new ChromeOptions();
    	        options.addArguments("--remote-allow-origins=*");
    	        options.addArguments("--headless");
    	        options.addArguments("--disable-gpu");  //GPU hardware acceleration isn't needed for headless
    	        options.addArguments("--no-sandbox");  //Disable the sandbox for all software features
    	        options.addArguments("--disable-dev-shm-usage");  //Overcome limited resource problems
    	        options.addArguments("--disable-extensions");  //Disabling extensions can save resources
    	        options.addArguments("--disable-plugins");
    	        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
    	        // Create a new ChromeDriver instance for each thread
    	        ChromeDriver driver = new ChromeDriver(options);
    	        driver.manage().window().maximize();
    	        //test = extent.createTest("Login Page");
    	        // Store the ChromeDriver instance in ThreadLocal
    	        driverThreadLocal.set(driver);
    	        driver.get(AppConfig.URL);
    	        driver.findElement(Locators.userName).sendKeys(AppConfig.username);
    	        driver.findElement(Locators.password).sendKeys(AppConfig.password);
    	        Thread.sleep(2000);
    	        driver.findElement(Locators.signMeIn).click();
    	        Thread.sleep(3000);
    	        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    	        
    	        try
    	        {
    	        if(driver.findElement(Locators.loginError).isDisplayed())
    	        {
    	        	System.out.println("Login failed");
    				return false;
    	        }
    	        }
    	        catch(Exception e) {}
    	       
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    	return true;
        
        
    }

    @Test(dataProvider = "testData")
    public void testMethod(String ID,String company, String leaseEntityID,String dateDifference,String moveInDate) throws Exception {
    	String portfolioName="";
    	String baseRentAmount ="";
    	String baseRentFromPW="";
    	String failedReason="";
    	
    	System.out.println("<-------- "+leaseEntityID+" -------->");
    	// Retrieve the thread-specific ChromeDriver instance from ThreadLocal
        ChromeDriver driver = driverThreadLocal.get();
        if(company.equalsIgnoreCase("Chicago PFW")) {
			   company = "Chicago";
		  }
		if(company.equalsIgnoreCase("California PFW")) {
			   company = "California pfw";
		  }
		try {
			String expiredURL = driver.getCurrentUrl();
			if(expiredURL.contains("https://app.propertyware.com/pw/expired.jsp") || expiredURL.equalsIgnoreCase("https://app.propertyware.com/pw/expired.jsp?cookie") || expiredURL.contains(AppConfig.URL)) {
				loggedOut = true;
				driver.navigate().to(AppConfig.URL);
				driver.findElement(Locators.userName).sendKeys(AppConfig.username); 
				driver.findElement(Locators.password).sendKeys(AppConfig.password);
			    Thread.sleep(2000);
			    driver.findElement(Locators.signMeIn).click();
			    Thread.sleep(3000);
			}
		}
		catch(Exception e) {}
		if (PropertyWare.selectLease(driver,company,leaseEntityID) == false) {
			portfolioName = getPortfolioName();
			failedReason = getFailedReason();
			String query = "Update Automation.BaseRentUpdate set Automation_Status='Failed',Automation_Notes='"+ failedReason + "',Automation_CompletionDate =getdate() where ID = '" + ID + "'";
			DataBase.updateTable(query);
			previousRecordCompany = company;	
			portfolioName="";
	    	baseRentAmount ="";
	    	baseRentFromPW="";
	    	failedReason="";
			
		}
		else {
			portfolioName = getPortfolioName();
			//loggedOut = false;
			//previousRecordCompany = company;
			if (UpdateBaseRent.getBaseRentAmount(driver,company,dateDifference,moveInDate) == false) {
				baseRentAmount = getBaseRentAmount();
				baseRentFromPW = getBaseRentFromPW();
				failedReason = getFailedReason();
				String query = "Update Automation.BaseRentUpdate set Automation_Status='Failed',Automation_Notes='"
						+ failedReason + "',Automation_CompletionDate =getdate(),BaseRentFromAutoCharges='"
						+ baseRentAmount + "',BaseRentFromPW = '" + baseRentFromPW + "',PortfolioName ='"+ portfolioName.replace("'", "''") +"' where ID = '" + ID + "'";
				DataBase.updateTable(query);
				portfolioName="";
		    	baseRentAmount ="";
		    	baseRentFromPW="";
		    	failedReason="";
				
			}
			else {
				baseRentAmount = getBaseRentAmount();
				baseRentFromPW = getBaseRentFromPW();
				if (UpdateBaseRent.updateBaseRent(driver) == false) {
					failedReason = getFailedReason();
					String query = "Update Automation.BaseRentUpdate set Automation_Status='Failed',Automation_Notes='"
							+ failedReason + "',Automation_CompletionDate =getdate(),BaseRentFromAutoCharges='"
							+ baseRentAmount + "',BaseRentFromPW = '" + baseRentFromPW + "',PortfolioName ='"+ portfolioName.replace("'", "''") +"' where ID = '" + ID + "'";
					DataBase.updateTable(query);
					portfolioName="";
			    	baseRentAmount ="";
			    	baseRentFromPW="";
			    	failedReason="";
				}
				else {
					// Update table for successful lease
					try {
						System.out.println("Base Rent Updated");
						failedReason = getFailedReason();
						String query = "Update Automation.BaseRentUpdate set Automation_Status='Completed',Automation_Notes='"+ failedReason + "',Automation_CompletionDate =getdate(),BaseRentFromAutoCharges='"
								+ baseRentAmount + "',BaseRentFromPW = '" + baseRentFromPW + "',PortfolioName ='"+ portfolioName.replace("'", "''") +"' where ID = '" + ID + "'";
						DataBase.updateTable(query);
						portfolioName="";
				    	baseRentAmount ="";
				    	baseRentFromPW="";
				    	failedReason="";
						
					} catch (Exception e) {}
				}
			}
		}
		
    }

    public static String getBaseRentAmount() {
    	if(baseRentAmountThreadLocal.get().isEmpty()) {
    		return "";
    	}
    	else {
    		 return baseRentAmountThreadLocal.get();
    	}
    }

    public static void setBaseRentAmount(String baseRentAmount) {
        baseRentAmountThreadLocal.set(baseRentAmount);
    }
    public static String getPortfolioName() {
    	if(portfolioNameThreadLocal.get().isEmpty()) {
    		return "";
    	}
    	else {
    		 return portfolioNameThreadLocal.get();
    	}
    }

    public static void setPortfolioName(String portfolioName) {
    	portfolioNameThreadLocal.set(portfolioName);
    }
    public static String getBaseRentFromPW() {
    	if(baseRentFromPWThreadLocal.get().isEmpty()) {
    		return "";
    	}
    	else {
    		 return baseRentFromPWThreadLocal.get();
    	}
    }

    public static void setBaseRentFromPW(String baseRentFromPW) {
    	baseRentFromPWThreadLocal.set(baseRentFromPW);
    }
    public static String getFailedReason() {
    	if(failedReasonThreadLocal.get().isEmpty()) {
    		return "";
    	}
    	else {
    		 return failedReasonThreadLocal.get();
    	}
    }

    public static void setFailedReason(String failedReason) {
    	failedReasonThreadLocal.set(failedReason);
    }
    
        
        
        // Add your test code here

  /*  @AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL,result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, result.getTestName());
        }
        else {
            test.log(Status.SKIP, result.getTestName());
        }
    } */
 
    
   @SuppressWarnings("deprecation")
   @AfterMethod
   public void tearDown() {
	   ChromeDriver driver = driverThreadLocal.get();
	    try {
	        if (driver != null) {
	            // Attempt to close the browser window
	            driver.close();
	            
	            // Wait for a short period for the browser process to terminate
	            Thread.sleep(2000);
	            
	            // Check if the WebDriver process is still running
	            if (isProcessRunning(driver)) {
	                // If the process is still running, forcibly kill it
	                driver.quit();
	                killChromeDriverProcess();
	                //Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe"); // For Windows
	            }
	        }
	    } catch (Exception e) {
	    	System.out.println("WebDriverException occurred while quitting the driver: " + e.getMessage());
	    }
	    driverThreadLocal.remove();
	}
   
  /* @AfterTest
   public void tearDownReports() {
       //to write or update test information to reporter
       extent.flush();
   }*/

	private boolean isProcessRunning(WebDriver driver) {
	    try {
	        // Check if the WebDriver process is still running
	        driver.getTitle(); // Accessing a method to check if the WebDriver is still alive
	        return true;
	    } catch (Exception e) {
	        // WebDriver process has terminated
	        return false;
	    }
	}
	
	private void killChromeDriverProcess() throws IOException, InterruptedException {
	    ProcessBuilder processBuilder = new ProcessBuilder("taskkill /F /IM chromedriver.exe"); // For Windows
	    // ProcessBuilder processBuilder = new ProcessBuilder("pkill", "-f", "chromedriver"); // For Unix/Linux
	    processBuilder.inheritIO(); // Redirects the input/output/error streams of the spawned process to the current Java process
	    Process process = processBuilder.start();
	    int exitCode = process.waitFor(); // Wait for the process to terminate
	    if (exitCode != 0) {
	        System.out.println("Failed to kill chromedriver process.");
	    }
	}
   

    @DataProvider(name = "testData", parallel = true)
    public Object[][] testData() {
    	try {
			DataBase.getLeasesList(AppConfig.pendingLeasesQuery);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return pendingLeases;
    }
}