import javax.swing.*;
import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Console {
    private JTextArea consoleOutput;

    public Console() {
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
    }

    public String processCommand(String message) {
        System.out.println("processCommand");
        AdminConnector.connectClient(message);
        consoleOutput.append(message);
        return message;
    }
    private void startUpdate() {
        consoleOutput.append(" Starting update...\n");
        try {
            Runtime.getRuntime().exec("powershell.exe -NoProfile -ExecutionPolicy Bypass -File src/main/java/Admin/Commands/WindowsUpdate.ps1 " + Inet4Address.getLocalHost().getHostName());
            System.out.println(Inet4Address.getLocalHost().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startClientUpdate(String client) {
        consoleOutput.append(" Starting update on " + client + "\n");
        try {
            String sendCommand = "powershell.exe -NoProfile -ExecutionPolicy Bypass -File src/main/java/Admin/Commands/WindowsUpdate.ps1 " + client;
            System.out.println(sendCommand);
            Runtime.getRuntime().exec(sendCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void openDiskManagement() {
        consoleOutput.append(" Opening disk management...\n");
        // Open disk management
    }
    public JComponent getOutputComponent(String input) {
        consoleOutput.append("\n");
        processCommand(input);
        return consoleOutput;
    }
    public void getInfoPool() throws FileNotFoundException {
        File clientInfoFile = new File("ClientRequestCreator/src/main/java/Clients/clientInfos.txt");
        try {
            BufferedReader clientReader = new BufferedReader(new FileReader(clientInfoFile.getAbsolutePath()));
            String line;
            while ((line = clientReader.readLine()) != null) {
                consoleOutput.append(" " + line + "\n");
            }
            clientReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
