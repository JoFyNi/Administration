package Clients;

import ressources.Bot;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static ressources.Bot.getInformation;

public class user {
    private static final String csvFile = "src/main/db/fourHeader.csv";

    public user() {}

    private static String name;
    private static String email;
    private static String path;
    private static final InetAddress ip;
    static {
        try {
            ip = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
    private static final Date currentDate = new Date();
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
                    addRequest("n," + name + ", " +  email + ", " + path + ", " + ip.getHostName() + ", " + currentDate +"\n");

                    Bot.getByAddress(ip.getHostName(), ip.getAddress());

                    String clientInformation = name + " , " + email + " , " + path + " , " + Inet4Address.getLocalHost() + " , " + currentDate + "\n";
                    getInformation(clientInformation, Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().freeMemory(), Runtime.getRuntime().totalMemory());

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dispose();
            }
        });

    }
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
