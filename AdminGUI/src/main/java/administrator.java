import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;

public class administrator {
    public static JFrame requestFrame;
    public static JTable tablePending;
    public static JTabbedPane tabbedPane;
    public static JSplitPane splitPane;
    public static JTextField input;
    private static Console console;
    private String admin = "Admin";
    private int adminID = 1111;
    private String adminPassword = "123456";
    private static String csvFile;
    static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    static char[] newStatus = {'t','f','n'};

    public administrator() {
    }
    public int startAdmin() {
        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("check for requests");
                updateRequests(connection.requestsPending, connection.requestsApproved, connection.requestsRejected);
            }
        };
        timer.schedule(task, 0, 10000);     // run all 10 seconds
        return 0;
    }
    public static String answer = "pending";
    public administrator(String admin, int adminID, String adminPassword, String csvFile) {
        this.admin = admin;
        this.adminID = adminID;
        this.adminPassword = adminPassword;
        this.csvFile = csvFile;
        System.out.println("Admin " + admin + "  adminID " + adminID + "  adminPassword " + adminPassword);
    }
    static void checkRequests() {
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                Request request = new Request();
                request.status = data[0].charAt(0);                             // column 1 [status] from request
                request.user = data[1];                                         // column 2 [name] from request
                request.email = data[2];                                        // column 3 [email] from request
                request.path = data[3];                                         // column 4 [InstallPath] from request
                request.host = data[4];                                         // column 5 [serviceTag] from request
                request.currentDate = data[5];                                  // column 6 [creationDate] from request
                if(request.status == 'n' || request.status == 'N') {            // new Request
                    connection.requestsPending.add(request);
                } else if(request.status == 't' || request.status == 'T'){      // approved Request
                    connection.requestsApproved.add(request);
                } else if(request.status == 'f' || request.status == 'F'){      // rejected Request
                    connection.requestsRejected.add(request);
                }
            }
            System.out.println("pending: " + connection.requestsPending.size());
        } catch (Exception e) {
            System.exit(1);
        }
        displayPending(connection.requestsPending);
    }
    public static void updateRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
        requestsPending.clear();
        requestsApproved.clear();
        requestsRejected.clear();
        checkRequests();
    }
    public static void displayPending(List<Request> requestsPending) {
        // Create column names
        String[] columnNamesPending = {"status", "user", "email", "path", "host", "date"};
        // Create data for each dataColumns in the table
        Object[][] dataPending = new Object[requestsPending.size()][6];
        for (int i = 0; i < requestsPending.size(); i++) {
            dataPending[i][0] = requestsPending.get(i).status;                  // column 1 [status] from request
            dataPending[i][1] = requestsPending.get(i).user;                    // column 2 [name] from request
            dataPending[i][2] = requestsPending.get(i).email;                   // column 3 [email] from request
            dataPending[i][3] = requestsPending.get(i).path;                    // column 4 [InstallPath] from request
            dataPending[i][4] = requestsPending.get(i).host;                    // column 5 [serviceTag] from request
            dataPending[i][5] = requestsPending.get(i).currentDate;             // column 6 [creationDate] from request
        }
        if (requestFrame == null) {
            // Create a new table instance
            // right click HUB
            tablePending = new JTable(dataPending, columnNamesPending);
            tablePending.addMouseListener(new MouseAdapter() {
                final JPopupMenu popupMenu = new JPopupMenu();
                final JMenuItem approve = new JMenuItem("approve");
                final JMenuItem reject = new JMenuItem("reject");
                final JMenuItem copyPath = new JMenuItem("copy path");
                final JMenuItem copyServiceTag = new JMenuItem("copy serviceTag");
                final JMenuItem copyName = new JMenuItem("copy name");
                final JMenuItem checkList = new JMenuItem("compare with checkList");
                final JLabel label = new JLabel();
                String selectedValue = null;

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)){
                        if (e.getSource()==approve) {
                            System.out.println(e.getSource()==approve);
                        }
                    }
                    if (SwingUtilities.isRightMouseButton(e)){
                        popupMenu.add(approve);                         // adding approve button to right-click HUB
                        popupMenu.add(reject);                          // adding reject button to right-click HUB
                        popupMenu.add(copyPath);                        // adding a InstallPath copy button to right-click HUB
                        popupMenu.add(copyServiceTag);                  // adding a serviceTag copy button to right-click HUB
                        popupMenu.add(copyName);                        // adding name copy button to right-click HUB
                        popupMenu.add(checkList);                       // adding open checkList button to right-click HUB
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        approve.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                answer = "approve";
                                int selectedRow = tablePending.getSelectedRow();
                                if (selectedRow != -1) {
                                    Object selectedValueUser = tablePending.getValueAt(selectedRow, 1);             // gathering the column information from the right-clicked row
                                    Object selectedValueEmail = tablePending.getValueAt(selectedRow, 2);            // gathering the column information from the right-clicked row
                                    Object selectedValuePath = tablePending.getValueAt(selectedRow, 3);             // gathering the column information from the right-clicked row
                                    Object selectedValueServiceTag = tablePending.getValueAt(selectedRow, 4);       // gathering the column information from the right-clicked row
                                    Object selectedValueDate = tablePending.getValueAt(selectedRow, 5);             // gathering the column information from the right-clicked row

                                    String newLine = "t" + "," + selectedValueUser.toString() + "," + selectedValueEmail.toString() + "," + selectedValuePath.toString() + "," + selectedValueServiceTag.toString() + "," + selectedValueDate.toString();
                                    List<String> lines = new ArrayList<>();
                                    String line;
                                    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                                        while ((line = br.readLine()) != null) {
                                            lines.add(line);
                                        }
                                        br.close();
                                        if (!lines.contains(newLine)) {
                                            lines.set(selectedRow, newLine);
                                            System.out.println("newLine = " + newLine);
                                            System.out.println("selectedRow = " + selectedRow);
                                            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false));
                                            for (String lineToWrite : lines) {
                                                bw.write(lineToWrite);
                                                bw.newLine();
                                            }
                                            bw.close();
                                        } else {
                                            System.out.println("Line already exists, no new line created");
                                        }
                                    } catch (Exception ee) {
                                        System.out.println(ee.getMessage());
                                    }
                                    updateRequests(connection.requestsPending, connection.requestsApproved, connection.requestsRejected);
                                    DefaultTableModel model = (DefaultTableModel) tablePending.getModel();
                                    model.removeRow(selectedRow);
                                    model.insertRow(selectedRow, newLine.split(","));
                                }
                            }
                        });
                        reject.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                answer = "approve";
                                int selectedRow = tablePending.getSelectedRow();
                                if (selectedRow != -1) {
                                    Object selectedValueUser = tablePending.getValueAt(selectedRow, 1);            // gathering the column information from the right-clicked row
                                    Object selectedValueEmail = tablePending.getValueAt(selectedRow, 2);           // gathering the column information from the right-clicked row
                                    Object selectedValuePath = tablePending.getValueAt(selectedRow, 3);            // gathering the column information from the right-clicked row
                                    Object selectedValueServiceTag = tablePending.getValueAt(selectedRow, 4);      // gathering the column information from the right-clicked row
                                    Object selectedValueDate = tablePending.getValueAt(selectedRow, 5);            // gathering the column information from the right-clicked row

                                    String newLine = "f" + "," + selectedValueUser.toString() + "," + selectedValueEmail.toString() + "," + selectedValuePath.toString() + "," + selectedValueServiceTag.toString() + "," + selectedValueDate.toString();
                                    List<String> lines = new ArrayList<>();
                                    String line;
                                    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                                        while ((line = br.readLine()) != null) {
                                            lines.add(line);
                                        }
                                        br.close();
                                        if (!lines.contains(newLine)) {
                                            lines.set(selectedRow, newLine);
                                            System.out.println("newLine = " + newLine);
                                            System.out.println("selectedRow = " + selectedRow);
                                            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false));
                                            for (String lineToWrite : lines) {
                                                bw.write(lineToWrite);
                                                bw.newLine();
                                            }
                                            bw.close();
                                        } else {
                                            System.out.println("Line already exists, no new line created");
                                        }
                                    } catch (Exception ee) {
                                        System.out.println(ee.getMessage());
                                    }
                                    updateRequests(connection.requestsPending, connection.requestsApproved, connection.requestsRejected);
                                    DefaultTableModel model = (DefaultTableModel) tablePending.getModel();
                                    model.removeRow(selectedRow);
                                    model.insertRow(selectedRow, newLine.split(","));
                                }
                            }
                        });
                        copyPath.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tablePending.getSelectedRow();
                                Object selectedValue = tablePending.getValueAt(selectedRow, 3);
                                StringSelection selection = new StringSelection(selectedValue.toString());
                                clipboard.setContents(selection, null);
                            }
                        });
                        copyServiceTag.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tablePending.getSelectedRow();
                                Object selectedValueServiceTag = tablePending.getValueAt(selectedRow, 4);
                                StringSelection selection = new StringSelection(selectedValueServiceTag.toString());
                                clipboard.setContents(selection, null);
                            }
                        });
                        copyName.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tablePending.getSelectedRow();
                                Object selectedValueUser = tablePending.getValueAt(selectedRow, 1);
                                StringSelection selection = new StringSelection(selectedValueUser.toString());
                                clipboard.setContents(selection, null);
                            }
                        });
                        checkList.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tablePending.getSelectedRow();
                                Object selectedValueUser = tablePending.getValueAt(selectedRow, 1);
                                Object selectedValuePath = tablePending.getValueAt(selectedRow, 3);
                                Object selectedValueServiceTag = tablePending.getValueAt(selectedRow, 4);
                                //CheckList.setModel(selectedValueUser.toString(), selectedValueServiceTag.toString(), selectedValuePath.toString());
                            }
                        });
                    }
                    selectedValue = null;
                }
            });
            input = new JTextField();
            input.addActionListener(e -> {
                console.getOutputComponent(input.getText());
                input.setText("");
                // Verarbeiten Sie die Eingabe hier
            });
            requestFrame = new JFrame("[Admin]Requests:  [Pending " + requestsPending.size() + "]");
            requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tabbedPane = new JTabbedPane();
            console = new Console();
            tabbedPane.addTab("Pending", new JScrollPane(tablePending));
            splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, new JScrollPane(console.getOutputComponent(input.getText())));
            splitPane.setResizeWeight(0.7);
            requestFrame.add(splitPane, BorderLayout.CENTER);
            requestFrame.add(input, BorderLayout.SOUTH);
            //requestFrame.add(tabbedPane);
            requestFrame.setSize(1200, 1600);
            requestFrame.pack();
            requestFrame.setVisible(true);
        } else {
            // updating the tables if the JFrame is already existing
            tablePending.setModel(new DefaultTableModel(dataPending, columnNamesPending));
            // updating the counter from the title bar
            requestFrame.setTitle("Requests:  [Pending " + requestsPending.size() + "]");
        }
    }

    public static void processAnswer(List<Request> requestsPending, String user){
        System.out.println(user);
        //Bot.startInstallationOnClient(requestsPending, checkUser);
        // rücksignal/ rückmeldung -> bot, startet methode startInstallationOnClient(...request...beschreibung(programm)) -> client erhält signal -> installation -> anfrage wird gelöscht da, fertig
    }
}
