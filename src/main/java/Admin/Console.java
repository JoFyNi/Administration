package Admin;

import javax.swing.*;
import java.io.*;

public class Console {
    private JTextArea consoleOutput;

    public Console() {
        consoleOutput = new JTextArea();
        consoleOutput.setEditable(false);
    }

    public void processCommand(String command) {
        switch (command) {
            case "start update":
                startUpdate();
                break;
            case "open disk management":
                openDiskManagement();
                break;
            case "help":
                consoleOutput.append(" - start update -> looking for updates and starting them \n" +
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

    private void startUpdate() {
        consoleOutput.append(" Starting update...\n");
        try {
            Runtime.getRuntime().exec("src/main/java/Admin/Commands/WindowsUpdate.bat");
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
