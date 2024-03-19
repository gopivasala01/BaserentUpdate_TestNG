package mainPackage;

public class AppConfig 
{
	   public static boolean saveButtonOnAndOff= true;
		
	   public static String URL ="https://app.propertyware.com/pw/login.jsp";
	   public static String username ="mds0418@gmail.com";
	   public static String password ="KRm#V39fecMDGg#";
	   
	   public static String excelFileLocation = "E:\\Automation\\Base Rent Update";
	   public static String downloadFilePath = "C:\\SantoshMurthyP\\Initial Rents Update - Branches\\Tennessee";
	   
	   public static String buildingPageURL = "https://app.propertyware.com/pw/leases/lease_detail.do?entityID=";
	   //Mail credentials
	   public static String fromEmail = "bireports@beetlerim.com";
	   public static String fromEmailPassword = "Welcome@123";
	   
	   public static String toEmail = "gopi.v@beetlerim.com";
	   public static String CCEmail = "gopi.v@beetlerim.com";
	   
	   public static String mailSubject = "Base Rent Update for  ";
	   
	   public static String[] LeaseAgreementFileNames = {"REVISED_Lease_","Lease_","Leases_"};
	   
	   public static String[] IAGClientList = {"510.","AVE.","BTH.","CAP.","FOR.","HRG.","HS.","MAN.","MCH.","OFF.","PIN.","RF.","SFR3.","TH.","HH.","Lofty.Ai","TA."};
	   
	   public static String connectionUrl = "jdbc:sqlserver://azrsrv001.database.windows.net;databaseName=HomeRiverDB;user=service_sql02;password=xzqcoK7T;encrypt=true;trustServerCertificate=true;";
	   
	  // public static String leaseFetchQuery  = "Select Company, Building,leaseName from Automation.InitialRentsUpdate where Status ='Pending' and Company ='Georgia'";
	   
	   public static String pendingLeasesQuery = "Select ID, Company, LeaseEntityID,DateDiff(Day,MoveInDate,Getdate()) as datedifference,moveInDate from Automation.BaseRentUpdate where Company in('California','Georgia','OKC','San Antonio') and Status in ('Active','Active - Notice Given','Active - TTO','Active - Month to Month') and Automation_Status is null";//and Automation_Notes in ('Base Rent could not be saved','Lease not found','Wrong Lease Entity ID')  ";//and LeaseEntityID = '3569451017'";//Company ='Alabama' and convert(date, Automation_Completiondate )<> '2023-07-05'";

	   
	   public static String failedLeasesQuery = "Select Company, LeaseEntityID,DateDiff(Day,MoveInDate,Getdate()) as datedifference,moveInDate from Automation.BaseRentUpdate where  Company='Alabama' and Status ='Failed'";
	   
	   public static String getLeasesWithStatusforCurrentDay = "Select Company, Building,ThirdPartyUnitID, Leaseidnumber, LeaseName,LeaseStatus,leaseExecutionDate, StartDate, EndDate, MonthlyRent, MonthlyRentFromPW, PetRent, PetRentFromPW,Status, Notes from Automation.InitialRentsUpdate and company in ('Tennessee') ";//where Format(convert(datetime, CompletedDate, 101),'dd MM yyyy') = format(getdate(),'dd MM yyyy') ";//and company in ('Florida','North Carolina')";
	   
	   
	   public static String getMonthlyRentChargeCode(String company)
	   {
		   
		   switch(company)
		   {
		   case "Austin":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "California":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "California pfw":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Chattanooga":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Chicago":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Colorado Springs":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Kansas City":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Houston":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Maine":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Savannah":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "North Carolina":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Alabama":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029,40051,40061";
		   case "Arkansas":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Dallas/Fort Worth":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Indiana":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Little Rock":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "San Antonio":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Tulsa":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029,20030";
		   case "Georgia":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "OKC":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "South Carolina":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Florida":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Tennessee":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "New Mexico":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Ohio":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Pennsylvania":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Lake Havasu":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Saint Louis":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Maryland":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Virginia":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Boise":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Idaho Falls":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Utah":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Montana":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Spokane":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Columbia - St Louis":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Arizona":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "New Jersey":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Hawaii":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Washington DC":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
		   case "Delaware":
			   return "40010,40011,40012,40013,40014,40015,40016,40017,40018,40019,40020,40021,40022,40023,40024,40025,40026,40027,40028,40029";
			      
		   }
		   return "";
	   }
	   
	   public static String getSubsidizedMonthlyRentChargeCode(String company) {
		    switch(company) {
		        case "Austin":
		        case "California":
		        case "California pfw":
		        case "Chattanooga":
		        case "Chicago":
		        case "Colorado Springs":
		        case "Delaware":
		        case "Kansas City":
		        case "Houston":
		        case "Hawaii":
		        case "Maine":
		        case "Savannah":
		        case "North Carolina":
		        case "Arkansas":
		        case "Dallas/Fort Worth":
		        case "Indiana":
		        case "Little Rock":
		        case "San Antonio":
		        case "Tulsa":
		        case "Georgia":
		        case "OKC":
		        case "South Carolina":
		        case "Florida":
		        case "Tennessee":
		        case "New Mexico":
		        case "Ohio":
		        case "Pennsylvania":
		        case "Lake Havasu":
		        case "Saint Louis":
		        case "Maryland":
		        case "Virginia":
		        case "Boise":
		        case "Idaho Falls":
		        case "Utah":
		        case "Montana":
		        case "Spokane":
		        case "Columbia - St Louis":
		        case "Arizona":
		        case "New Jersey":
		        case "Alabama":
		        case "Washington DC":
		            return "40200 - Subsidized Rent";
		    }
		    return "";
		}
	   
	   
}