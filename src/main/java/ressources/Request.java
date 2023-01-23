package ressources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Request {
    private JFrame requestFrame;
    char status;
    String user;
    String email;
    //List to hold "ressources.Request" objects
    public static List<Request> requests = new ArrayList<>();

    public Request(char status, String user, String email) {
        this.status = status;
        this.user = user;
        this.email = email;
    }

    public Request() {
        new Request(status, user, email);
        requestFrame = new JFrame("Requests");
        requestFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFrame.setSize(500, 300);
    }

    public static boolean getRequest() {
        String csvFile = "src/main/db/request.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                Request request = new Request();
                request.status = data[0].charAt(0);
                request.user = data[1];
                request.email = data[2];
                requests.add(request);
            }

            //sending requests list to displayRequest method
            displayRequest(requests);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void displayRequest(List<Request> requests) {
        System.out.println("ressources.Request");
        // Create column names
        String[] columnNames = {"status", "user", "email"};

        // Create data
        Object[][] data = new Object[requests.size()][3];
        for (int i = 0; i < requests.size(); i++) {
            data[i][0] = requests.get(i).status;
            data[i][1] = requests.get(i).user;
            data[i][2] = requests.get(i).email;
        }

        // Create a new table instance
        JTable table = new JTable(data, columnNames);
        JFrame frame = new JFrame("Requests");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.pack();
        frame.setSize(500,300);
        frame.setVisible(true);
    }
}
