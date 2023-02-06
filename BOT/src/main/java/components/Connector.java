package components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

public class Connector extends NetworkClient implements connection {
    private static MulticastSocket mySocket;

    public Connector(int port) {
        super(port);
    }

    public static byte[] sendingPackage(List<Request> requestsPending) throws IOException {
        byte[] buffer = new byte[messageValue];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        mySocket.send(packet);
        return buffer;
    }

    @Override
    public void connect() {
        try {
            ServerSocket serverSocket = new ServerSocket(connection.multicastPORT);
            System.out.println("Server gestartet auf Port " + connection.multicastPORT);

            Socket socket = serverSocket.accept();
            System.out.println("Client verbunden");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String receivedMessage = in.readUTF();
            System.out.println("Nachricht vom Client empfangen: " + receivedMessage);

            out.writeUTF("Nachricht erhalten");
            System.out.println("Nachricht an Client gesendet");

            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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