package ressources;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static ressources.Bot.startInstallationOnClient;
import static ressources.CheckList.setModel;

public class administrator {
    private String admin = "admin";
    private int adminID = 1111;
    private String adminPassword = "123456";
    private String csvFile;
    static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    static char[] newStatus = {'t','f','n'};
    public void startAdmin() {
        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("check for requests");
                new administrator(admin, adminID, adminPassword, csvFile);
            }
        };
        timer.schedule(task, 0, 10000);     // run all 10 seconds
    }
    public static String answer = "pending";
    public administrator(String admin, int adminID, String adminPassword, String csvFile) {
        this.admin = admin;
        this.adminID = adminID;
        this.adminPassword = adminPassword;
        this.csvFile = csvFile;
        System.out.println("admin " + admin + "  adminID " + adminID + "  adminPassword " + adminPassword);
    }
    public static void getRequests(List<Request> requestsPending, String user){
        //System.out.println("Request from >>>" + user + "<<< to applied to administrator");
        displayPending(requestsPending);
        // verarbeiten -> auswahl Append or deny
        // rücksignal/ rückmeldung -> bot, startet methode startInstallationOnClient(...request...beschreibung(programm)) -> client erhält signal -> installation -> anfrage wird gelöscht da, fertig
    }
    public static void processAnswer(List<Request> requestsPending, String user){
        System.out.println(user);
        //Bot.startInstallationOnClient(requestsPending, checkUser);
        // rücksignal/ rückmeldung -> bot, startet methode startInstallationOnClient(...request...beschreibung(programm)) -> client erhält signal -> installation -> anfrage wird gelöscht da, fertig
    }
    public static JFrame requestFrame;
    public static JTable tablePending;
    public static JTabbedPane tabbedPane;
    public static void displayPending(List<Request> requestsPending) {
        // Create column names
        String[] columnNamesPending = {"status", "user", "email", "path", "host", "date"};
        // Create data for each dataColumns in the table
        Object[][] dataPending = new Object[requestsPending.size()][6];
        for (int i = 0; i < requestsPending.size(); i++) {
            dataPending[i][0] = requestsPending.get(i).status;
            dataPending[i][1] = requestsPending.get(i).user;
            dataPending[i][2] = requestsPending.get(i).email;
            dataPending[i][3] = requestsPending.get(i).path;
            dataPending[i][4] = requestsPending.get(i).host;
            dataPending[i][5] = requestsPending.get(i).currentDate;
        }
        if (requestFrame == null) {
            // Create a new table instance
            tablePending = new JTable(dataPending, columnNamesPending);
            tablePending.addMouseListener(new MouseAdapter() {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem approve = new JMenuItem("approve");
                JMenuItem reject = new JMenuItem("reject");
                JMenuItem copyPathItem = new JMenuItem("copy path");
                JMenuItem checkList = new JMenuItem("compare with checkList");
                JLabel label = new JLabel();
                String selectedValue = null;;
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)){
                        if (e.getSource()==approve) {
                            System.out.println(e.getSource()==approve);
                        }
                    }
                    if (SwingUtilities.isRightMouseButton(e)){
                        popupMenu.add(approve);
                        popupMenu.add(reject);
                        popupMenu.add(copyPathItem);
                        popupMenu.add(checkList);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        approve.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource()==approve) {
                                    answer = "approve";
                                    int selectedRow = tablePending.getSelectedRow();
                                    Object selectedValueUser = tablePending.getModel().getValueAt(selectedRow, 1);
                                    Object selectedValuePath = tablePending.getModel().getValueAt(selectedRow, 3);
                                    Object selectedValueServiceTag = tablePending.getModel().getValueAt(selectedRow, 4);
                                    StringSelection selectionPath = new StringSelection(selectedValuePath.toString());  // selection = path of .exe
                                    clipboard.setContents(selectionPath, null);
                                    processAnswer(requestsPending, selectedValueUser.toString());
                                    startInstallationOnClient(selectedValueUser.toString(), selectedValueServiceTag.toString(), selectedValuePath.toString());
                                    tablePending.setValueAt(newStatus[0], selectedRow, 0);
                                }
                            }
                        });
                        reject.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                answer = "reject";
                                System.out.println("reject");
                                // rücksignal/ rückmeldung -> bot, startet methode deny request -> client erhält Nachicht "abgelehnt weil, ....."
                                int selectedRow = tablePending.getSelectedRow();
                                tablePending.setValueAt(newStatus[1], selectedRow, 0);
                                // send dialog to checkUser...
                            }
                        });
                        copyPathItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tablePending.getSelectedRow();
                                Object selectedValue = tablePending.getModel().getValueAt(selectedRow, 3);
                                StringSelection selection = new StringSelection(selectedValue.toString());
                                clipboard.setContents(selection, null);
                                System.out.println(clipboard);
                            }
                        });
                        checkList.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int selectedRow = tablePending.getSelectedRow();
                                Object selectedValueUser = tablePending.getModel().getValueAt(selectedRow, 1);
                                Object selectedValuePath = tablePending.getModel().getValueAt(selectedRow, 3);
                                Object selectedValueServiceTag = tablePending.getModel().getValueAt(selectedRow, 4);
                                setModel(selectedValueUser.toString(), selectedValueServiceTag.toString(), selectedValuePath.toString());
                            }
                        });
                    }
                    selectedValue = null;
                }
            });

            requestFrame = new JFrame("[Admin]Requests:  [Pending " + requestsPending.size() + "]");
            requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Pending", new JScrollPane(tablePending));
            requestFrame.add(tabbedPane);
            requestFrame.setSize(600, 800);
            requestFrame.pack();
            requestFrame.setVisible(true);
        } else {
            // updating the tables
            tablePending.setModel(new DefaultTableModel(dataPending, columnNamesPending));
            // updating the counter from the title bar
            requestFrame.setTitle("Requests:  [Pending " + requestsPending.size() + "]");
        }
    }
}
