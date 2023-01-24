package ressources;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Request {
    char status;
    String user;
    String email;
    String requestTag;

    public Request(String test) {
    }
    public Request() {
    }

    public Request(char status, String user, String email, String requestTag) {
        this.status = status;
        this.user = user;
        this.email = email;
        this.requestTag = requestTag;
    }

    public static JFrame frame;
    public static JTable tablePending;
    public static JTable tableApproved;
    public static JTable tableRejected;

    public static JTabbedPane tabbedPane;

    public static void displayRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
        // Create column names
        String[] columnNamesPending = {"status", "user", "email", "request"};
        String[] columnNamesApproved = {"status", "user", "email", "request"};
        String[] columnNamesRejected = {"status", "user", "email", "request"};

        // Create data for each dataColumns in the table
        Object[][] dataPending = new Object[requestsPending.size()][4];
        for (int i = 0; i < requestsPending.size(); i++) {
            dataPending[i][0] = requestsPending.get(i).status;
            dataPending[i][1] = requestsPending.get(i).user;
            dataPending[i][2] = requestsPending.get(i).email;
            dataPending[i][3] = requestsPending.get(i).requestTag;
        }
        Object[][] dataApproved = new Object[requestsApproved.size()][4];
        for (int i = 0; i < requestsApproved.size(); i++) {
            dataApproved[i][0] = requestsApproved.get(i).status;
            dataApproved[i][1] = requestsApproved.get(i).user;
            dataApproved[i][2] = requestsApproved.get(i).email;
            dataApproved[i][3] = requestsApproved.get(i).requestTag;
        }
        Object[][] dataRejected = new Object[requestsRejected.size()][4];
        for (int i = 0; i < requestsRejected.size(); i++) {
            dataRejected[i][0] = requestsRejected.get(i).status;
            dataRejected[i][1] = requestsRejected.get(i).user;
            dataRejected[i][2] = requestsRejected.get(i).email;
            dataRejected[i][3] = requestsRejected.get(i).requestTag;
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
            frame.setSize(600, 800);
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