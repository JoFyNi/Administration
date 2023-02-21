import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Scanner;

public class Console {
    public JTextArea consoleOutput;

    public Console() {
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
    }
    public JComponent getOutputComponent(String input) {
        if (input.equals("")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            consoleOutput.append("\n");
            processCommand(input);
        }
        return consoleOutput;
    }
    public String processCommand(String message) {
        System.out.println("processCommand");
        switch (message) {
            case "update server" -> {
                startUpdate();
                consoleOutput.append("updating...");
            }
            case "update client" -> {
                consoleOutput.append("type a serviceTag");
                Scanner scanner = new Scanner(message);
                message = scanner.next();
                String serviceTag = message.substring(0, message.indexOf(" "));
                message = (" tippe a serviceTag -> example: update Client G2HS52 \n");
                consoleOutput.append("Client: " + message);
            }
            case "open disk management" -> {
                openDiskManagement();
                consoleOutput.append("Client: " + message);
            }
            case "help" -> {
                message = (" - update server -> starting Windows updates on server \n" +
                        " - update client [serviceTag] -> starting Windows updates on client \n " +
                        " - open disk management ->  Opening disk management \n" +
                        " - approved clients -> list of all approved requests \n");
                consoleOutput.append("Client: " + message);
            }
            case "approved clients" -> {
                getInfoPool();
                message = "information";
                consoleOutput.append("Client: " + message);
            }
            default -> {
                consoleOutput.append("Error, unknown service");
            }
            //consoleOutput.append(" Unknown command, tippe help for help\n");
        }
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
    public void getInfoPool() {
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
