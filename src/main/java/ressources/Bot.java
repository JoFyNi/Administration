package ressources;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static ressources.Request.displayRequests;
import static ressources.administrator.displayPending;
import static ressources.connection.*;

public class Bot {
    private String botName;
    private int botId;
    private String botType;
    private String csvFile;
    //List to hold "Request" objects


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
                //connector.run();
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
                request.path = data[3];
                request.host = data[4];
                request.currentDate = data[5];
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

    public static void startInstallationOnClient(String user, String serviceTag, String path) {
        System.out.println("Installation: " + path + "  >>>  " + user + ":" + serviceTag);
        // remote verbindung herstellen/
        // start Runtime on user pc
        //String command = "cmd.exe /c start /wait runas /user:Administrator myprogram.exe";
        //Runtime.getRuntime().exec(command);

        try {
            Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start " + path);
            System.out.println(Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start " + path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void getInformation(String clientInfo, int processor, long freeMemory, long totalMemory) throws IOException {
        System.out.println(clientInfo + "\n" + "processor: " + processor + " " + "freeMemory: " + freeMemory + " " + "totalMemory: " + totalMemory);
        File clientInfoFile = new File("src/main/java/Clients/clientInfos.txt");
        FileWriter fileWriter = new FileWriter(clientInfoFile, true);
        fileWriter.append(clientInfo);
        fileWriter.append("\n");
        fileWriter.close();
    }

    public static InetAddress getByAddress(String host, byte[] addr) throws UnknownHostException {
        System.out.println("Host Name: "+ host);
        System.out.println("IP Address: "+ addr);
        return InetAddress.getByAddress(host, addr);
    }
}
