package Admin;

import javax.swing.*;
import java.io.*;
import java.net.Inet4Address;
import java.util.Arrays;
import java.util.Scanner;

public class Console {
    private JTextArea consoleOutput;

    public Console() {
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
    }

    public void processCommand(String command) {
        if (command.matches("update client .*")) {
            String[] splitCommand = command.split(" ");
            String client = splitCommand[2];
            startClientUpdate(client);
        } else {
            switch (command) {
                case "update server":
                    startUpdate();
                    break;
                case "update client":
                    consoleOutput.append(" tippe a serviceTag -> example: update Client G2HS52 \n");
                    break;
                case "open disk management":
                    openDiskManagement();
                    break;
                case "help":
                    consoleOutput.append(" - update server -> starting Windows updates on server \n" +
                            " - update client [serviceTag] -> starting Windows updates on client \n " +
                            " - open disk management ->  Opening disk management \n" +
                            " - approved clients -> list of all approved requests \n");
                    break;
                case "approved clients":
                    try {
                        getInfoPool();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    consoleOutput.append(" Unknown command, tippe help for help\n");
                    break;
            }
        }
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
        consoleOutput.append(" " + input + "\n");
        processCommand(input);
        return consoleOutput;
    }

    public void getInfoPool() throws FileNotFoundException {
        File clientInfoFile = new File("src/main/java/Clients/clientInfos.txt");
        try {
            BufferedReader clientReader = new BufferedReader(new FileReader(clientInfoFile));
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