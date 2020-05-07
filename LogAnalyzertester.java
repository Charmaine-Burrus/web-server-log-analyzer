import java.util.*;
/**
 * Tests LogAnalyzer class which is used for analysis of web server log file.
 * 
 * @ceb
 * @5-7-20
 */
public class LogAnalyzertester {
    
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4.", new Date(), "example request", 200, 500);
        System.out.println(le);
        
        LogEntry le2 = new LogEntry("1.2.100.4.", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("short-test_log");
        logAnalyzer.printAll();
    }
    
    public void testCountUniqueIPs() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("short-test_log");
        int uniqueIPs = logAnalyzer.countUniqueIPs();
        System.out.println("There are " + uniqueIPs + " unique IPs.");
    }
    
    public void testPrintAllSCHigherThanNum() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("weblog1_log");
        logAnalyzer.printAllSCHigherThanNum(400);
    }
    
    public void testUniqueIPVisitsOnDay() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("weblog1_log");
        String date = "Mar 24";
        ArrayList<String> arrayList = logAnalyzer.uniqueIPVisitsOnDay(date);
        System.out.println("# of Unique IPs for " + date + " was " + arrayList.size());
        System.out.println("Those IPs were: ");
        for (String IP : arrayList) {
            System.out.println(IP);
        }
    }
    
    public void testCountUniqueIPsInSCRange() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("weblog1_log");
        int low = 300;
        int high = 399;
        int IPsInRange = logAnalyzer.countUniqueIPsInSCRange(low, high);
        System.out.println("# of Unique IPs with Status Code between " + low + " and " + high + " was " + IPsInRange);
    }
    
    public void tester() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.readFile("weblog3-short_log");
        System.out.println("Testing file: weblog3-short_log");
        
        System.out.println("\ntester countVisitsPerIP:");
        HashMap<String,Integer> IPAddresstoNumberVisits = logAnalyzer.countVisitsPerIP();
        System.out.println(IPAddresstoNumberVisits);
        
        System.out.println("\ntester mostNumberVisitsByIP:");
        int numOfMostVisitsPerIP = logAnalyzer.mostNumberVisitsByIP(IPAddresstoNumberVisits);
        System.out.println("The highest number of visits was " + numOfMostVisitsPerIP);
        
        System.out.println("\ntester getIPsMostVisits");
        ArrayList<String> IPsWithMostVisits = logAnalyzer.getIPsMostVisits(IPAddresstoNumberVisits);
        System.out.println(IPsWithMostVisits);
        
        System.out.println("\ntester getIPsForDays");
        HashMap<String,ArrayList<String>> allIPsForDays = logAnalyzer.getIPsForDays();
        System.out.println(allIPsForDays);
        
        System.out.println("\ntester getDayWithMostIPVisits");
        String DayWithMostIPVisits = logAnalyzer.getDayWithMostIPVisits(allIPsForDays);
        System.out.println("The Day with the most visits was " + DayWithMostIPVisits);
        
        System.out.println("\ntester getIPsWithMostVisitsOnDay");
        String date = "Sep 21";
        ArrayList<String> iPsWithMostVisitsOnDay = logAnalyzer.getIPsWithMostVisitsOnDay(allIPsForDays, date);
        System.out.println(iPsWithMostVisitsOnDay);
    }
    
    

}
