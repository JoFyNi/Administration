package ressources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Request {
    char status;
    String user;
    String email;

    public Request() {
    }

    public Request(char status, String user, String email) {
        this.status = status;
        this.user = user;
        this.email = email;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static JFrame frame;
    public static JTable tableTrue;
    public static JTable tableFalse;
    public static JTabbedPane tabbedPane;

    public static void displayRequests(List<Request> requestsTrue, List<Request> requestsFalse) {
        // Create column names
        String[] columnNamesTrue = {"status", "user", "email"};
        String[] columnNamesFalse = {"status", "user", "email"};
        // Create data
        Object[][] dataTrue = new Object[requestsTrue.size()][3];
        for (int i = 0; i < requestsTrue.size(); i++) {
            dataTrue[i][0] = requestsTrue.get(i).status;
            dataTrue[i][1] = requestsTrue.get(i).user;
            dataTrue[i][2] = requestsTrue.get(i).email;
        }
        Object[][] dataFalse = new Object[requestsFalse.size()][3];
        for (int i = 0; i < requestsFalse.size(); i++) {
            dataFalse[i][0] = requestsFalse.get(i).status;
            dataFalse[i][1] = requestsFalse.get(i).user;
            dataFalse[i][2] = requestsFalse.get(i).email;
        }


        if (frame == null) {
            // Create a new table instance
            tableTrue = new JTable(dataTrue, columnNamesTrue);
            tableFalse = new JTable(dataFalse, columnNamesFalse);
            frame = new JFrame("Requests  [true " + requestsTrue.size() + " / false " + requestsFalse.size()+"]");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("True", new JScrollPane(tableTrue));
            tabbedPane.addTab("False", new JScrollPane(tableFalse));
            frame.add(tabbedPane);
            frame.pack();
            frame.setSize(500, 300);
            frame.setVisible(true);
        } else {
            // updating the tables
            tableTrue.setModel(new DefaultTableModel(dataTrue, columnNamesTrue));
            tableFalse.setModel(new DefaultTableModel(dataFalse, columnNamesFalse));
            // updating the counter from the title bar
            frame.setTitle("Requests  [true " + requestsTrue.size() + " / false " + requestsFalse.size()+"]");
        }
    }
}