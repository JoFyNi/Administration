import javax.swing.*;
import java.io.*;
import java.net.Inet4Address;

public class Console {
    private JTextArea consoleOutput;

    public Console() {
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
    }

    public String processCommand(String message) {
        System.out.println("processCommand");

        while (!message.equals("exit chat")) {
            switch (message) {
                case "update server":
                    //startUpdate();
                    consoleOutput.append("updating...");
                    break;
                case "update client":
                    message = (" tippe a serviceTag -> example: update Client G2HS52 \n");
                    consoleOutput.append("Client: " + message);
                    break;
                case "open disk management":
                    //openDiskManagement();
                    consoleOutput.append("Client: " + message);
                    break;
                case "help":
                    message = (" - update server -> starting Windows updates on server \n" +
                            " - update client [serviceTag] -> starting Windows updates on client \n " +
                            " - open disk management ->  Opening disk management \n" +
                            " - approved clients -> list of all approved requests \n");
                    consoleOutput.append("Client: " + message);
                    break;
                case "approved clients":
                    //getInfoPool();
                    message = "information";
                    consoleOutput.append("Client: " + message);
                    break;
                default:
                    //consoleOutput.append(" Unknown command, tippe help for help\n");
                    break;
            }
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
