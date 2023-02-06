package components;

import com.jcraft.jsch.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bot {
    private final String csvFile;

    //List to hold "Request" objects
    public Bot(String botName, int botId, String botType, String csvFile) {
        this.csvFile = csvFile;
        System.out.println("Bot: [" + botName + ":" + botId + "  " + botType + " permission" + "] starting");
    }

    public static void startInstallationOnClient(String user, String serviceTag, String path) {
        System.out.println("Installation: " + path + "  >>>  " + user + ":" + serviceTag);
        String Administrator = " "; // add Administrator (local admin)
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, serviceTag);    // serviceTag zu ip abändern?
            session.setPassword("admin password");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand("cmd.exe /c start /wait runas /user:" + Administrator + " " + path);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            System.out.println(session + " -> " + path + "  from " + serviceTag + " " + user);

            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[connection.messageValue];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, connection.messageValue);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }
            }
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            System.out.println("JSchException (startInstallationOnClient)");
        } catch (IOException e) {
            System.out.println("IOException (startInstallationOnClient)");
        }

        // String connectToClient = System.getProperty(serviceTag);
        //String command = "cmd.exe /c start /wait runas /user:Administrator " + path;
        //Runtime.getRuntime().exec(command);
        //try {
        //    Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /c start " + path);
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}
    }

    public static void getInformation(String clientInfo, int processor, long freeMemory, long totalMemory) throws IOException {
        System.out.println(clientInfo + "\n" + "processor: " + processor + "  freeMemory: " + freeMemory + "  totalMemory: " + totalMemory);
        File clientInfoFile = new File("src/main/java/Clients/clientInfos.txt");
        FileWriter fileWriter = new FileWriter(clientInfoFile, true);
        fileWriter.append(clientInfo);
        fileWriter.append("\n");
        fileWriter.close();
    }

    public static InetAddress getByAddress(String host, byte[] addr) throws UnknownHostException {
        System.out.println("Host Name: " + host);
        System.out.println("IP Address: " + addr);
        return InetAddress.getByAddress(host, addr);
    }

    public void startBot() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("check for requests");
                updateRequests(connection.requestsPending, connection.requestsApproved, connection.requestsRejected);
            }
        };
        timer.schedule(task, 0, 10000);     // run all 10 seconds
    }

    private void checkRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
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
                if (request.status == 'n' || request.status == 'N') {            // new Request
                    requestsPending.add(request);
                } else if (request.status == 't' || request.status == 'T') {      // approved Request
                    requestsApproved.add(request);
                } else if (request.status == 'f' || request.status == 'F') {      // rejected Request
                    requestsRejected.add(request);
                }
            }
            System.out.println("pending: " + requestsPending.size() + "  approve: " + requestsApproved.size() + "  rejected: " + requestsRejected.size());
        } catch (Exception e) {
            System.exit(1);
        }
        Request.displayRequests(requestsPending, requestsApproved, requestsRejected);
    }

    public void updateRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
        requestsPending.clear();
        requestsApproved.clear();
        requestsRejected.clear();
        checkRequests(requestsPending, requestsApproved, requestsRejected);
    }
}
