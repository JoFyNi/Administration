import java.io.IOException;
import java.net.InetAddress;

public class checkForAssignedAndAvailableIPAddresses {
    static InetAddress inet;
    public static void sendPacket() throws IOException {
        String ipAddress = "192.168.172.";
        for (int i = 0; i < 250 ; i++) {
            String currentIP = ipAddress + i;
            inet = InetAddress.getByName(currentIP);

            System.out.print("Sending Ping Request to " + currentIP);
            System.out.println(inet.isReachable(1000) ? " Host is reachable" : " Host is NOT reachable");

        }
    }

    public static void main(String[] args) throws IOException {
        sendPacket();
    }
}
