package ressources;

import Clients.user;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ressources.connection.*;

public class connector {
    private static MulticastSocket mySocket;
    private Main main;
    private Vector<user>userList;
    public connector(Main main) {
        try {
            String msg = "Hello";
            InetAddress mcastaddr = InetAddress.getByName(multicastIP);
            InetSocketAddress group = new InetSocketAddress(mcastaddr, multicastPORT);
            NetworkInterface netIf = NetworkInterface.getByName("bge0");
            mySocket = new MulticastSocket(multicastPORT);
            mySocket.joinGroup(new InetSocketAddress(mcastaddr, 0), netIf);
            byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket hi = new DatagramPacket(msgBytes, msgBytes.length, group);
            mySocket.send(hi);
            // get their responses!
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            mySocket.receive(recv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void run() {
        while (true) {
            try {
                byte[] buffer = packageReceive();
                process(buffer);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static byte [] packageReceive() throws IOException {
        byte[] buffer = new byte[messageValue];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        mySocket.receive(packet);
        return buffer;
    }
    public static void process(byte[] buffer) throws InterruptedException {
        String message = new String(buffer);
        message = message.trim();
        StringTokenizer token = new StringTokenizer(message, separator);
        String t1 = token.nextToken();
        String t2 = token.nextToken();
        if (t1.equals(connectionOnline)) {
            // get connection to clients
        } else if (t1.equals(connectionBreak)) {
            Thread.sleep(breakTime);
            process(buffer);
            // do something wehen then connection break
        } else if (t1.equals(privat)) {
            String t3 = token.nextToken();
            String t4 = token.nextToken();
        } else {
            System.out.println("Error");
        }
    }
}
