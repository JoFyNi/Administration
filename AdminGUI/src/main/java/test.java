import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class test {
    static String testFile = "AdminGUI/src/main/db/test.csv";
    public static List<Request> requestsPending = new ArrayList<>();
    public static List<Request> requestsApproved = new ArrayList<>();
    public static List<Request> requestsRejected = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        checkRequests();
    }


    private static void checkRequests() {
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {
            List<String> lines = new ArrayList<>();
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lines.add(line);
// use comma as separator
                String[] data = line.split(cvsSplitBy);
                Request request = new Request();
                request.status = data[0].charAt(0);
                request.user = data[1];
                request.email = data[2];
                request.path = data[3];
                request.host = data[4];
                request.currentDate = data[5];
                requestsPending.add(request);

                if (!data[0].equals("n")) {
                    data[0] = "n";
                    String newLine = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + "," + data[5];
                    lines.set(lineNumber, newLine);
                }
                lineNumber++;
            }
            System.out.println("pending: " + requestsPending.size());
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(testFile, false));
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.exit(1);
        }
        System.out.println(requestsPending);

    }

}
