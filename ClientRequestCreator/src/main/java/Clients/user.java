package Clients;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.Date;


public class user {
    private static final String csvFile = "src/main/db/fourHeader.csv";
    public user() {}
    private static String name;
    private static String email;
    private static String path;
    private static final Date currentDate = new Date();

    /**
     * dialog window for the user to create a request.
     * Input files for full name and email
     * selectable .exe for the installation path
     * @throws IOException
     */
    public static void createRequest() throws IOException  {
        JDialog dialog = new JDialog();
        dialog.setTitle("request");
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("enter full name: ");
        JTextField nameField = new JTextField(20);
        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("enter Email: ");
        JTextField emailField = new JTextField(20);
        JPanel messengePanel = new JPanel();
        JLabel messageLabel = new JLabel("select the .exe you want to have installed: ");
        JFileChooser fileChooser = new JFileChooser();
        JButton sendButton = new JButton("Send");
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        messengePanel.add(messageLabel);
        messengePanel.add(fileChooser);
        dialog.add(namePanel);
        dialog.add(emailPanel);
        dialog.add(messengePanel);
        dialog.add(sendButton);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setVisible(true);
        sendButton.addActionListener(e -> {
            name = nameField.getText();
            email= emailField.getText();
            path = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(path);
            if (name.equals("") || email.equals("") || path.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
            } else {
                try {
                    addRequest("n," + name + ", " +  email + ", " + path + ", " + Inet4Address.getLocalHost() + ", " + currentDate +"\n");
                    //Bot.getByAddress(Inet4Address.getLocalHost().toString(), Inet4Address.getLocalHost().getAddress());
                    String clientInformation = name + " , " + email + " , " + path + " , " + Inet4Address.getLocalHost() + " , " + currentDate + "\n";
                    //Bot.getInformation(clientInformation, Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().freeMemory(), Runtime.getRuntime().totalMemory());
                    copyHardwareInfo(name);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dispose();
            }
        });
    }

    /**
     * collecting hardware information from the requested client
     * @param name serviceTag
     * @throws IOException
     * writing the information's into a log file
     */
    public static void copyHardwareInfo(String name) throws IOException{
        try {
            String filePath = "./" + name + ".txt";
            // Use "dxdiag /t" variant to redirect output to a given file
            ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","dxdiag","/t",filePath);
            System.out.println("-- Executing dxdiag command --");
            Process p = pb.start();
            p.waitFor();

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            System.out.println(String.format("-- Printing %1$1s info --",filePath));
            while((line = br.readLine()) != null){
                if(line.trim().startsWith("Card name:") || line.trim().startsWith("Current Mode:")){
                    System.out.println(line.trim());
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * adding Request tot table
     * @param requestTag installation request / path / etc...
     * @return true for next steps
     * @throws IOException
     */
    public static boolean addRequest(String requestTag) throws IOException {
        FileWriter fileWriter = new FileWriter(csvFile, true);
        fileWriter.append(requestTag);
        fileWriter.close();
        return true;
    }

    public static void main(String[] args) throws IOException {
        user user = new user();
        createRequest();
    }
}
