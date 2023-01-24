package ressources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static ressources.Request.displayRequests;
import static ressources.administrator.displayPending;

public class Bot {
    private String botName;
    private int botId;
    private String botType;
    private String csvFile;
    //List to hold "Request" objects
    List<Request> requestsPending = new ArrayList<>();
    List<Request> requestsApproved = new ArrayList<>();
    List<Request> requestsRejected = new ArrayList<>();

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
                System.out.println("connect");
                connector.run();
                System.out.println("check for requests");
                updateRequests(requestsPending, requestsApproved, requestsRejected);
            }
        };
        timer.schedule(task, 0, 10000);     // run all 10 seconds
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
                if(request.status == 'n' || request.status == 'N') {            // new Request
                    sendRequests(requestsPending, request.user);
                    requestsPending.add(request);
                } else if(request.status == 't' || request.status == 'T'){      // approved Request
                    requestsApproved.add(request);
                } else if(request.status == 'f' || request.status == 'F'){      // rejected Request
                    requestsRejected.add(request);
                } else {System.out.println("no more request");}
            }
            System.out.println("pending: " + requestsPending.size() +"  approve: " + requestsApproved.size() + "  rejected: " + requestsRejected.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
        getRequests(requestsPending, requestsApproved, requestsRejected);
        displayPending(requestsPending);
    }
    public void updateRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
        requestsPending.clear();
        requestsApproved.clear();
        requestsRejected.clear();
        checkRequests();
    }
    public void getRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
        displayRequests(requestsPending, requestsApproved, requestsRejected);
    }


    private void sendRequests(List<Request> requestsPending, String user) {
        System.out.println("sending requests to Administrator");
        administrator.getRequests(requestsPending, user);
    }

    public static void startInstallationOnClient(List<Request> requestsPending, String user) {
        String message;

        message = "Hello " + user;
        System.out.println(message);
    }




    // getters and setters -> for later use
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

    public List<Request> requestsPending() {
        return requestsPending;
    }

    public void setRequestsPending(List<Request> requestsPending) {
        this.requestsPending = requestsPending;
    }

    public List<Request> getRequestsApproved() {
        return requestsApproved;
    }

    public void setRequestsApproved(List<Request> requestsApproved) {
        this.requestsApproved = requestsApproved;
    }

    public List<Request> getRequestsRejected() {
        return requestsRejected;
    }

    public void setRequestsRejected(List<Request> requestsRejected) {
        this.requestsRejected = requestsRejected;
    }
}
