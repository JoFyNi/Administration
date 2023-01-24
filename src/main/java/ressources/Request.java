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
    public static JTable tablePending;
    public static JTable tableApproved;
    public static JTable tableRejected;

    public static JTabbedPane tabbedPane;

    public static void displayRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
        // Create column names
        String[] columnNamesPending = {"status", "user", "email"};
        String[] columnNamesApproved = {"status", "user", "email"};
        String[] columnNamesRejected = {"status", "user", "email"};

        // Create data for each dataColumns in the table
        Object[][] dataPending = new Object[requestsPending.size()][3];
        for (int i = 0; i < requestsPending.size(); i++) {
            dataPending[i][0] = requestsPending.get(i).status;
            dataPending[i][1] = requestsPending.get(i).user;
            dataPending[i][2] = requestsPending.get(i).email;
        }
        Object[][] dataApproved = new Object[requestsApproved.size()][3];
        for (int i = 0; i < requestsApproved.size(); i++) {
            dataApproved[i][0] = requestsApproved.get(i).status;
            dataApproved[i][1] = requestsApproved.get(i).user;
            dataApproved[i][2] = requestsApproved.get(i).email;
        }
        Object[][] dataRejected = new Object[requestsRejected.size()][3];
        for (int i = 0; i < requestsRejected.size(); i++) {
            dataRejected[i][0] = requestsRejected.get(i).status;
            dataRejected[i][1] = requestsRejected.get(i).user;
            dataRejected[i][2] = requestsRejected.get(i).email;
        }


        if (frame == null) {
            // Create a new table instance
            tablePending = new JTable(dataPending, columnNamesPending);
            tableApproved = new JTable(dataRejected, columnNamesApproved);
            tableRejected = new JTable(dataRejected, columnNamesRejected);
            tablePending.setEnabled(false);     // set columns/ rows not Edible   change if onclick is created
            tableApproved.setEnabled(false);    // set columns/ rows not Edible   change if onclick is created
            tableRejected.setEnabled(false);    // set columns/ rows not Edible   change if onclick is created
            frame = new JFrame("Requests:  [Pending " + requestsPending.size() + " / Approved " + requestsApproved.size() + " / Rejected " + requestsRejected.size() + "]");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Pending", new JScrollPane(tablePending));
            tabbedPane.addTab("Approved", new JScrollPane(tableApproved));
            tabbedPane.addTab("Rejected", new JScrollPane(tableRejected));
            frame.add(tabbedPane);
            frame.pack();
            frame.setSize(500, 800);
            frame.setVisible(true);
        } else {
            // updating the tables
            tablePending.setModel(new DefaultTableModel(dataPending, columnNamesPending));
            tableApproved.setModel(new DefaultTableModel(dataApproved, columnNamesApproved));
            tableRejected.setModel(new DefaultTableModel(dataRejected, columnNamesRejected));
            // updating the counter from the title bar
            frame.setTitle("Requests:  [Pending " + requestsPending.size() + " / Approved " + requestsApproved.size() +" / Rejected " + requestsRejected.size() + "]");
        }
    }
}