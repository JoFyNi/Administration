package Clients;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class user {
    private static String csvFile = "src/main/db/fourHeader.csv";

    public user() {}

    private static String name;
    private static String email;
    private static String message;
    public static void createRequest() throws IOException  {


        JDialog dialog = new JDialog();
        dialog.setTitle("request");
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("enter full name: ");
        JTextField nameField = new JTextField(20);

        JPanel emailPanel = new JPanel();
        JLabel emailLabel = new JLabel("enter Email: ");
        JTextField emailField = new JTextField(20);

        JPanel messengePanel = new JPanel();
        JLabel messageLabel = new JLabel("enter Message: ");
        JTextArea messageField = new JTextArea("", 5, 20);

        JButton sendButton = new JButton("Send");
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        messengePanel.add(messageLabel);
        messengePanel.add(messageField);

        dialog.add(namePanel);
        dialog.add(emailPanel);
        dialog.add(messengePanel);
        dialog.add(sendButton);

        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setVisible(true);

        sendButton.addActionListener(e -> {
            name = nameField.getText();
            email= emailField.getText();
            message = messageField.getText();
            String requestTag = "n," + name + "," + email + "," + message;

            if (name.equals("") || email.equals("") || message.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
            } else {
                try {
                    addRequest(requestTag);
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
        createRequest();
    }
}
