package ressources;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.StringTokenizer;

import static ressources.connection.*;

public class connector {
    private static MulticastSocket mySocket;

    public connector() {

    }

    public static void run() {
        while (true) {
            try {
                byte[] buffer = packageReceive();
                process(buffer);

            } catch (IOException e) {
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

    public static void process(byte[] buffer) {
        String message = new String(buffer);
        message = message.trim();

        StringTokenizer token = new StringTokenizer(message, separator);
        String t1 = token.nextToken();
        String t2 = token.nextToken();

        if (t1.equals(connectionOnline)) {
            // get connection to clients
        } else if (t1.equals(connectionBreak)) {
            // do something wehen then connection break
        } else if (t1.equals(privat)) {
            String t3 = token.nextToken();
            String t4 = token.nextToken();

        } else {
            System.out.println("Error");
        }
    }
}
