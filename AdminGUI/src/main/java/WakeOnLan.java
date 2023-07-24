import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;

public class WakeOnLan {

    // Methode, um die MAC-Adresse in Bytes umzuwandeln
    private static byte[] getMacBytes(String macAddress) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macAddress.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("Ungültiges MAC-Adressenformat.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ungültiges Zeichen in der MAC-Adresse.");
        }
        return bytes;
    }

    // Methode, um das Wake-on-LAN-Paket zu erstellen und zu versenden
    public static void sendWakeOnLanPacket(String macAddress, String hostName) {
        try {
            byte[] macBytes = getMacBytes(macAddress);

            // Erstellen des Magic Packets
            byte[] magicPacket = new byte[102];
            Arrays.fill(magicPacket, (byte) 0xFF);
            for (int i = 6; i < 102; i += 6) {
                System.arraycopy(macBytes, 0, magicPacket, i, 6);
            }

            // Versenden des Magic Packets über das Netzwerk
            InetAddress ipAddress = InetAddress.getByName(hostName); // IP-Adresse-des-Zielrechners
            int port = 9; // Standardport für Wake-on-LAN
            DatagramPacket packet = new DatagramPacket(magicPacket, magicPacket.length, ipAddress, port);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
            System.out.println(magicPacket + " " + magicPacket.length + " " + ipAddress + " " + port);
            System.out.println(packet);
        } catch (Exception e) {
            System.out.println("Fehler beim Senden des Wake-on-LAN-Pakets: " + e.getMessage());
        }
    }

    // Beispielaufruf der Methode
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String macAddress = "00-BE-43-8D-3E-8B"; // Hier die gewünschte MAC-Adresse eingeben00:0E:62:09:23:4B
        String hostName = "192.168.172.139";   // Hier den gewünschten Hostnamen eingeben

        String getHost = scanner.nextLine();
        sendWakeOnLanPacket(macAddress, getHost);
    }
}
