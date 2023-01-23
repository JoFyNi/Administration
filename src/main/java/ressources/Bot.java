package ressources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

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
                checkRequests();

                System.out.println("send requests");
                sendRequests();
            }
        };
        timer.schedule(task, 0, 10000);
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
                    System.out.println("request " + requestsTrue.add(request));
                }else if(request.status == 'f' || request.status == 'F'){
                    requestsFalse.add(request);
                    System.out.println("request " + requestsFalse.add(request));
                } else {System.out.println("failed to parse");}

                if(requestsTrue.size() > 200) {
                    System.out.println("starting delete: Request limit reached (size above 200) -> " + requestsTrue.size());
                    requestsTrue.remove(0);     // is this necessary? // is not working yet
                }
                if(requestsFalse.size() > 100) {
                    System.out.println("starting delete false Request: Request limit reached (size above 100) -> " + requestsFalse.size());
                    requestsFalse.remove(0);    // is this necessary? // is not working yet
                }
            }
            System.out.println(requestsTrue.size());
            System.out.println(requestsFalse.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
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

    // method to run the bot
    public void runBot() {
        // code to run the bot goes here
    }

    // method to stop the bot
    public void stopBot() {
        Scanner scanner = new Scanner(System.in);
        String exitLine = scanner.nextLine();
        if  (exitLine == "exit") {
            System.exit(0);
        }
        // code to stop the bot goes here
    }


    public void getRequests() {
        // get requests from users.csv
        // list request with user id (name)
        // list what kind of request it is
        // installation request -> witch programm the user wants to install
    }

    public List<Request> sendRequests() {
        // create a message for the Administrator ("new request")
        // create a message for the user ("request accepted")
        // create a message for the user ("request rejected")
        return requestsTrue;
    }

    public void manageRequests() {
        // sort requests by user id (name) and type
        // take requests from users and put it into a list of requests, displayed on a table inside a frame
    }
}
