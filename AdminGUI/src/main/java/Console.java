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

    /**
     * JComponent -> part of the main JFrame
     * serves as console
     * @param input text input from user
     * @return reply
     */
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

    /**
     * selection of reply's for different text requests
     * @param message text input from user
     * @return answer
     */
    public String processCommand(String message) {
        System.out.println("processCommand");
        if (message.equals("")) {
            consoleOutput.append("Error, unknown service");
            message = "Error, unknown service";
        }
        else if (message.contains("update server")) {
            startUpdate();
            consoleOutput.append("updating...");
            message = "updating...";
        }
        else if (message.contains("update client")) {
            consoleOutput.append("type a serviceTag");
            Scanner scanner = new Scanner(message);
            message = scanner.next();
            message = (" tippe a serviceTag -> example: update Client G2HS52 \n");
            consoleOutput.append("Client: " + message);
        }
        else if (message.contains("open disk management")) {
            openDiskManagement();
            consoleOutput.append("Client: " + message);
            message = "Client: " + message;
        }
        else if (message.contains("help")) {
            message = (" - update server -> starting Windows updates on server \n" +
                    " - update client [serviceTag] -> starting Windows updates on client \n " +
                    " - open disk management ->  Opening disk management \n" +
                    " - approved clients -> list of all approved requests \n");
            consoleOutput.append("Client: " + message);
        }
        else if (message.contains("approved clients")) {
            getInfoPool();
            message = "information";
            consoleOutput.append("Client: " + message);
        }
        consoleOutput.append(message);
        return message;
    }

    /**
     * starting update
     */
    private void startUpdate() {
        consoleOutput.append(" Starting update...\n");
        try {
            Runtime.getRuntime().exec("powershell.exe -NoProfile -ExecutionPolicy Bypass -File src/main/java/Admin/Commands/WindowsUpdate.ps1 " + Inet4Address.getLocalHost().getHostName());
            System.out.println(Inet4Address.getLocalHost().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * starting update of the clients computer
     * @param client serviceTag (computer name)
     */
    private void startClientUpdate(String client) {
        consoleOutput.append(" Starting update on " + client + "\n");
        try {
            String updateCommand = "powershell.exe -NoProfile -ExecutionPolicy Bypass -File src/main/java/Admin/Commands/WindowsUpdate.ps1 " + client;
            System.out.println(updateCommand);
            Runtime.getRuntime().exec(updateCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * opens the disk manager for general overview
     */
    private void openDiskManagement() {
        consoleOutput.append(" Opening disk management...\n");
        // Open disk management
    }

    /**
     * methode to gather user information
     */
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
