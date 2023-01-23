package ressources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static ressources.Request.displayRequests;

public class Bot {
    private String botName;
    private int botId;
    private String botType;
    private String csvFile;
    //List to hold "Request" objects
    List<Request> requestsTrue = new ArrayList<>();
    List<Request> requestsFalse = new ArrayList<>();

    public Bot(String botName, int botId, String botType, String csvFile) {
        this.botName = botName;
        this.botId = botId;
        this.botType = botType;
        this.csvFile = csvFile;
    }

    public void startBot() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("check for requests");
                updateRequests(requestsTrue, requestsFalse);
                System.out.println("send requests");
                sendRequests();
            }
        };
        timer.schedule(task, 0, 10000);
    }

    private void sendRequests() {
    }

    private void checkRequests() {
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                Request request = new Request();
                request.status = data[0].charAt(0);
                request.user = data[1];
                request.email = data[2];
                if(request.status == 't' || request.status == 'T') {
                    requestsTrue.add(request);
                }else if(request.status == 'f' || request.status == 'F'){
                    requestsFalse.add(request);
                } else {System.out.println("no more request");}
            }

            System.out.println("true: " + requestsTrue.size() +"  false: " + requestsFalse.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
        getRequests(requestsTrue, requestsFalse);

    }
    public void updateRequests(List<Request> requestsTrue, List<Request> requestsFalse){
        requestsTrue.clear();
        requestsFalse.clear();
        checkRequests();
    }
    public void getRequests(List<Request> requestsTrue, List<Request> requestsFalse) {
        displayRequests(requestsTrue, requestsFalse);
    }


    // getters and setters
    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public int getBotId() {
        return botId;
    }

    public void setBotId(int botId) {
        this.botId = botId;
    }

    public String getBotType() {
        return botType;
    }

    public void setBotType(String botType) {
        this.botType = botType;
    }

    public String getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(String csvFile) {
        this.csvFile = csvFile;
    }

    public List<Request> getRequestsTrue() {
        return requestsTrue;
    }

    public void setRequestsTrue(List<Request> requestsTrue) {
        this.requestsTrue = requestsTrue;
    }

    public List<Request> getRequestsFalse() {
        return requestsFalse;
    }

    public void setRequestsFalse(List<Request> requestsFalse) {
        this.requestsFalse = requestsFalse;
    }

    // method to stop the bot
    public void stopBot() {
        Scanner scanner = new Scanner(System.in);
        String exitLine = scanner.findInLine("Do you want to stop the bot? (y/n)");
        if  (exitLine == "y") {
            System.exit(0);
        }
        // code to stop the bot goes here
    }

    public void manageRequests() {
        // sort requests by user id (name) and type
        // take requests from users and put it into a list of requests, displayed on a table inside a frame
    }
}
