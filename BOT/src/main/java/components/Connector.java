package components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Connector extends NetworkClient implements connection {
    private static MulticastSocket mySocket;
    public Connector(int port) {
        super(port);
    }
    private static List<String> logs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("Server started on port " + connection.multicastPORT);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            getMessage(inputStream);
            Scanner scanner = new Scanner(System.in);
            String message = "";
            try {
                while (!message.equals("exit chat")) {
                    switch (message) {
                        case "update server":
                            //startUpdate();
                            System.out.println("updating...");
                            break;
                        case "update client":
                            message = (" tippe a serviceTag -> example: update Client G2HS52 \n");
                            System.out.println("Client: " + message);
                            logs.add("Client: " + message);
                            break;
                        case "open disk management":
                            //openDiskManagement();
                            System.out.println("Client: " + message);
                            logs.add("Client: " + message);
                            break;
                        case "help":
                            message = (" - update server -> starting Windows updates on server \n" +
                                    " - update client [serviceTag] -> starting Windows updates on client \n " +
                                    " - open disk management ->  Opening disk management \n" +
                                    " - approved clients -> list of all approved requests \n");
                            System.out.println("Client: " + message);
                            logs.add("Client: " + message);
                            break;
                        case "approved clients":
                            //getInfoPool();
                            message = "information";
                            System.out.println("Client: " + message);
                            logs.add("Client: " + message);
                            break;
                        default:
                            //consoleOutput.append(" Unknown command, tippe help for help\n");
                            break;
                        }

                    System.out.print("You: ");
                    message = scanner.nextLine();
                    outputStream.writeUTF(message);
                    outputStream.flush();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //message = inputStream.readUTF();
                //System.out.println("Client: " + message);
                //logs.add("Client: " + message);
            } catch (IOException e) {
                System.out.println("Client disconnected");
            } finally {
                inputStream.close();
                outputStream.close();
                socket.close();
            }
        }
    }


    public static void connect() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Server started on port " + connection.multicastPORT);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                getMessage(inputStream);
                Scanner scanner = new Scanner(System.in);
                String message = "";
                try {
                    while (!message.equals("exit chat")) {
                        switch (message) {
                            case "update server":
                                //startUpdate();
                                System.out.println("updating...");
                                break;
                            case "update client":
                                message = (" tippe a serviceTag -> example: update Client G2HS52 \n");
                                System.out.println("Client: " + message);
                                logs.add("Client: " + message);
                                break;
                            case "open disk management":
                                //openDiskManagement();
                                System.out.println("Client: " + message);
                                logs.add("Client: " + message);
                                break;
                            case "help":
                                message = (" - update server -> starting Windows updates on server \n" +
                                        " - update client [serviceTag] -> starting Windows updates on client \n " +
                                        " - open disk management ->  Opening disk management \n" +
                                        " - approved clients -> list of all approved requests \n");
                                System.out.println("Client: " + message);
                                logs.add("Client: " + message);
                                break;
                            case "approved clients":
                                //getInfoPool();
                                message = "information";
                                System.out.println("Client: " + message);
                                logs.add("Client: " + message);
                                break;
                            default:
                                //consoleOutput.append(" Unknown command, tippe help for help\n");
                                break;
                        }

                        System.out.print("You: ");
                        message = scanner.nextLine();
                        outputStream.writeUTF(message);
                        outputStream.flush();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //message = inputStream.readUTF();
                    //System.out.println("Client: " + message);
                    //logs.add("Client: " + message);
                } catch (IOException e) {
                    System.out.println("Client disconnected");
                } finally {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getMessage(DataInputStream inputStream) throws IOException {
        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "";
                try {
                    message = inputStream.readUTF();
                    System.out.println("Client: " + message);
                    logs.add("Client: " + message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(task, 0, 100);
    }
    public static List<String> getLogs() {
        return logs;
    }

    public void run() {
        while (true) {
            try {
                byte[] buffer = packageReceive();
                process(buffer);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] packageReceive() throws IOException {
        byte[] buffer = new byte[messageValue];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        mySocket.receive(packet);
        return buffer;
    }

    public void process(byte[] buffer) throws InterruptedException {
        String message = new String(buffer);
        message = message.trim();
        StringTokenizer token = new StringTokenizer(message, separator);
        String t1 = token.nextToken();
        String t2 = token.nextToken();
    }
}
