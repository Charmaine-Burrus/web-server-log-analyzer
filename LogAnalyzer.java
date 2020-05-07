import java.util.*;
import edu.duke.*;
/**
 * Creates and analyzes an ArrayList of LogEntry records. Used for analysis of web server log file - number of unique visitors, hashmaps of date to IP addresses, date with most visits, IP addresses of those who visited most, etc.
 * 
 * @ceb
 * @5-720
 */
public class LogAnalyzer {
    
    private ArrayList<LogEntry> records;
    
    public LogAnalyzer() {
        records = new ArrayList<LogEntry>();
    }
    
    public void printAll() {
        for (LogEntry logEntry : records) {
            System.out.println(logEntry);  //uses the toString method from logEntry class to print the details of each logEntry (not the memory location)
        }
    }
    
    public void readFile(String filename) {   //adds each line of file as Log Entry to records
        records.clear();  //comment this out if you will be reading in multiple files
        FileResource fileResource = new FileResource("WebLogProgram/" + filename);
        for (String line : fileResource.lines()) {
            WebLogParser webLogParser = new WebLogParser();
            LogEntry currLogEntry = webLogParser.parseEntry(line);
            records.add(currLogEntry);            
        }       
    }
    
    public void printAllSCHigherThanNum(int num) {  //prints all Log Entries with Status Codes higher than parameter num
        System.out.println("Log Entries with Status Codes higher than " + num);
        for (LogEntry currLogEntry : records) {
            int currStatusCode = currLogEntry.getStatusCode();
            if (currStatusCode > num) {
                System.out.println(currLogEntry);
            }
        }
    }
    
    public ArrayList<String> uniqueIPVisitsOnDay(String somedayMmm_DD) {  //returns an ArrayList<String> of unique IP addresses that had access on the given day
        ArrayList<String> uniqueIPVisits = new ArrayList<String>();
        for (LogEntry currLogEntry : records) {
            String date = currLogEntry.getAccessTime().toString();
            String trimmedDate = date.substring(4,10);
            if (trimmedDate.equals(somedayMmm_DD)) {
                String currIPAddress = currLogEntry.getIpAddress();
                if (!uniqueIPVisits.contains(currIPAddress)) {
                    uniqueIPVisits.add(currIPAddress);
                }
            }
        }
        return uniqueIPVisits;
    }
    
    public int countUniqueIPsInSCRange(int low, int high) { //returns the number of unique IP addresses that have a status code in the range from low to high, inclusive
        ArrayList<String> uniqueIPVisits = new ArrayList<String>();
        for (LogEntry currLogEntry : records) {
            int currStatusCode = currLogEntry.getStatusCode();
            if (currStatusCode >= low && currStatusCode <= high) {
                String currIPAddress = currLogEntry.getIpAddress();
                if (!uniqueIPVisits.contains(currIPAddress)) {
                    uniqueIPVisits.add(currIPAddress);
                }
            }
        }
        return uniqueIPVisits.size();
    }
    
    public HashMap<String,Integer> countVisitsPerIP() {  //returns HashMap of IPAddressto#ofVisits in records
        HashMap<String,Integer> IPAddresstoNumberofVisits = new HashMap<String,Integer>();
        for (LogEntry currLogEntry : records) {
            String ip = currLogEntry.getIpAddress();
            if (!IPAddresstoNumberofVisits.containsKey(ip)) {
                IPAddresstoNumberofVisits.put(ip, 1);
            }
            else {
                int currValue = IPAddresstoNumberofVisits.get(ip);
                IPAddresstoNumberofVisits.put(ip, currValue+1);
            }
        }
        return IPAddresstoNumberofVisits;
    }
    
    public int countUniqueIPs() {   //returns total number of unique IP addresses in records
        HashMap<String,Integer> IPAddresstoNumberofVisits = countVisitsPerIP();
        int numOfUniqueIPs = IPAddresstoNumberofVisits.size();
        return numOfUniqueIPs;
    }
    
    public int mostNumberVisitsByIP(HashMap<String,Integer> IPAddresstoNumberofVisits) {  //method returns the maximum number of visits to this website by a single IP address
        int highestNumofVisits = 0;
        for (String ip : IPAddresstoNumberofVisits.keySet()) {
            if (IPAddresstoNumberofVisits.get(ip) > highestNumofVisits) {
                highestNumofVisits = IPAddresstoNumberofVisits.get(ip);
            }
        }
        return highestNumofVisits;
    }
    
    public ArrayList<String> getIPsMostVisits(HashMap<String,Integer> IPAddresstoNumberofVisits) {//method returns an ArrayList of Strings of IP addresses that all have the maximum number of visits to this website
        ArrayList<String> iPsMostVisits = new ArrayList<String>();
        int highestNumOfVisitsPerIP = mostNumberVisitsByIP(IPAddresstoNumberofVisits);
        for (String ip : IPAddresstoNumberofVisits.keySet()) {
            if (IPAddresstoNumberofVisits.get(ip) == highestNumOfVisitsPerIP) {
                iPsMostVisits.add(ip);
            }
        }
        return iPsMostVisits;
    }
    
    public HashMap<String,ArrayList<String>> getIPsForDays() { //returns HashMap of days to an ArrayList of IP addresses that occurred on that day (including repeated IP addresses)
        HashMap<String,ArrayList<String>> dateToIPAddresses = new HashMap<String,ArrayList<String>>();
        for (LogEntry logEntry : records) {
            String date = logEntry.getAccessTime().toString();
            date = date.substring(4,10);
            //create the arraylist<String>
            ArrayList<String> arrayListForDate = new ArrayList<String>();
            //if that date is already in the HashMap
            if (dateToIPAddresses.containsKey(date)){
                //get the ArrayList of IP addresses from the map
                arrayListForDate = dateToIPAddresses.get(date);
            }
            //add ip address to arraylist
            arrayListForDate.add(logEntry.getIpAddress());
            //add pair to map
            dateToIPAddresses.put(date, arrayListForDate);
        }
        return dateToIPAddresses;
    }
    
    public String getDayWithMostIPVisits(HashMap<String,ArrayList<String>> dateToIPAddresses) {  //return String of the day that has the most IP address visits
        String dayWithMostIPVisits = "";
        int highestNumofVisits = 0;
        for (String date : dateToIPAddresses.keySet()) {
            if (dateToIPAddresses.get(date).size() > highestNumofVisits) {
                highestNumofVisits = dateToIPAddresses.get(date).size();
                dayWithMostIPVisits = date;
            }
        }
        return dayWithMostIPVisits;
    }
    
    public ArrayList<String> getIPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> dateToIPAddresses, String somedayMmm_DD) {  //This method returns an ArrayList<String> of IP addresses that had the most accesses on the given day.
        ArrayList<String> allIPsFromSomeday = dateToIPAddresses.get(somedayMmm_DD);
        HashMap <String,Integer> IPstoVisits = new HashMap<String,Integer>();
        for (String ip : allIPsFromSomeday) {
            if (!IPstoVisits.containsKey(ip)) {
                IPstoVisits.put(ip, 1);
            }
            else {
                int currValue = IPstoVisits.get(ip);
                IPstoVisits.put(ip, currValue+1);
            }
        }
        ArrayList<String> IPsWithMostVisits = getIPsMostVisits(IPstoVisits);
        return IPsWithMostVisits;
    }
}
