package Admin;

import ressources.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.List;

import static ressources.connection.messageValue;
import static ressources.connection.multicastIP;

public class AdminConnector extends AdminNetworkClient {
    private static MulticastSocket mySocket;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    public AdminConnector(int port) {
        super(port);
    }
    @Override
    public void connect() {
        // Implement server-specific connection logic
        try {
            socket = new Socket(multicastIP, multicastPORT);
            System.out.println("Verbunden mit Server " + multicastIP + " auf Port " + multicastPORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("Hallo Server");
            System.out.println("Nachricht an Server gesendet");

            String receivedMessage = in.readUTF();
            System.out.println("Nachricht vom Server empfangen: " + receivedMessage);

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Request> packageReceive(List<Request> requestsPending) throws IOException {
        byte[] buffer = new byte[messageValue];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        mySocket.receive(packet);
        return requestsPending;
    }
    public static byte[] sendingPackage(List<Request> requestsPending) throws IOException {
        byte[] buffer = new byte[messageValue];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        mySocket.send(packet);
        return buffer;
    }
}
