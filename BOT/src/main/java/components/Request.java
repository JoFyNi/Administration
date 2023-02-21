package components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Request {
    public static JFrame frame;
    public static JTable tablePending;
    public static JTable tableApproved;
    public static JTable tableRejected;
    public static JTabbedPane tabbedPane;
    public static String setRequest;
    static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    static char[] newStatus = {'t', 'f', 'n'};
    public char status;
    public String user;
    public String email;
    public String path;
    public String host;
    public String currentDate;
    String ip;
    public Request(String test) {
    }
    public Request() {
    }
    public Request(char status, String user, String email, String path, String host, String currentDate) {
        this.status = status;
        this.user = user;
        this.email = email;
        this.path = path;
        this.host = host;
        this.currentDate = currentDate;
    }

    public static void displayRequests(List<Request> requestsPending, List<Request> requestsApproved, List<Request> requestsRejected) {
        // Create column names
        String[] columnNamesPending = {"status", "user", "email", "path", "host", "date"};
        String[] columnNamesApproved = {"status", "user", "email", "path", "host", "date"};
        String[] columnNamesRejected = {"status", "user", "email", "path", "host", "date"};
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
        Object[][] dataApproved = new Object[requestsApproved.size()][6];
        for (int i = 0; i < requestsApproved.size(); i++) {
            dataApproved[i][0] = requestsApproved.get(i).status;
            dataApproved[i][1] = requestsApproved.get(i).user;
            dataApproved[i][2] = requestsApproved.get(i).email;
            dataApproved[i][3] = requestsApproved.get(i).path;
            dataApproved[i][4] = requestsPending.get(i).host;
            dataApproved[i][5] = requestsPending.get(i).currentDate;
        }
        Object[][] dataRejected = new Object[requestsRejected.size()][6];
        for (int i = 0; i < requestsRejected.size(); i++) {
            dataRejected[i][0] = requestsRejected.get(i).status;
            dataRejected[i][1] = requestsRejected.get(i).user;
            dataRejected[i][2] = requestsRejected.get(i).email;
            dataRejected[i][3] = requestsRejected.get(i).path;
            dataRejected[i][4] = requestsPending.get(i).host;
            dataRejected[i][5] = requestsPending.get(i).currentDate;
        }
        if (frame == null) {
            // Create a new table instance
            tablePending = new JTable(dataPending, columnNamesPending);
            tableApproved = new JTable(dataApproved, columnNamesApproved);
            tableRejected = new JTable(dataRejected, columnNamesRejected);
            frame = new JFrame("Requests:  [Pending " + requestsPending.size() + " / Approved " + requestsApproved.size() + " / Rejected " + requestsRejected.size() + "]");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Pending", new JScrollPane(tablePending));
            tabbedPane.addTab("Approved", new JScrollPane(tableApproved));
            tabbedPane.addTab("Rejected", new JScrollPane(tableRejected));
            tablePending.addMouseListener(new MouseAdapter() {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem approve = new JMenuItem("approve");
                JMenuItem reject = new JMenuItem("reject");
                JMenuItem copyPathItem = new JMenuItem("copy path");
                String selectedValue = null;
                ;

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (e.getSource() == approve) {
                            System.out.println(e.getSource() == approve);
                        }
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu.add(approve);
                        popupMenu.add(reject);
                        popupMenu.add(copyPathItem);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        approve.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == approve) {
                                    setRequest = "approve";
                                    int selectedRow = tablePending.getSelectedRow();
                                    Object selectedValueUser = tablePending.getModel().getValueAt(selectedRow, 1);
                                    Object selectedValuePath = tablePending.getModel().getValueAt(selectedRow, 3);
                                    Object selectedValueServiceTag = tablePending.getModel().getValueAt(selectedRow, 4);
                                    StringSelection selectionPath = new StringSelection(selectedValuePath.toString());  // selection = path of .exe
                                    clipboard.setContents(selectionPath, null);
                                    //processAnswer(requestsPending, selectedValueUser.toString());
                                    Bot.startInstallationOnClient(selectedValueUser.toString(), selectedValueServiceTag.toString(), selectedValuePath.toString());
                                    tablePending.setValueAt(newStatus[0], selectedRow, 0);
                                }
                            }
                        });
                        reject.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == approve) {
                                    setRequest = "reject";
                                    int selectedRow = tablePending.getSelectedRow();
                                    tablePending.setValueAt(newStatus[1], selectedRow, 0);
                                }
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
                    }
                    selectedValue = null;
                }
            });
            tableApproved.addMouseListener(new MouseAdapter() {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem pending = new JMenuItem("pending");
                JMenuItem reject = new JMenuItem("reject");
                JMenuItem copyPathItem = new JMenuItem("copy path");
                String selectedValue = null;
                ;

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (e.getSource() == pending) {
                            System.out.println(e.getSource() == pending);
                        }
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu.add(pending);
                        popupMenu.add(reject);
                        popupMenu.add(copyPathItem);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        pending.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == pending) {
                                    setRequest = "pending";
                                    int selectedRow = tablePending.getSelectedRow();
                                    tablePending.setValueAt(newStatus[0], selectedRow, 0);
                                }
                            }
                        });
                        reject.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == pending) {
                                    setRequest = "reject";
                                    int selectedRow = tablePending.getSelectedRow();
                                    tablePending.setValueAt(newStatus[1], selectedRow, 0);
                                }
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
                    }
                    selectedValue = null;
                }
            });
            tableRejected.addMouseListener(new MouseAdapter() {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem approve = new JMenuItem("approve");
                JMenuItem pending = new JMenuItem("pending");
                JMenuItem copyPathItem = new JMenuItem("copy path");
                String selectedValue = null;
                ;

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if (e.getSource() == pending) {
                            System.out.println(e.getSource() == pending);
                        }
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        popupMenu.add(approve);
                        popupMenu.add(pending);
                        popupMenu.add(copyPathItem);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                        approve.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == pending) {
                                    setRequest = "approve";
                                    int selectedRow = tablePending.getSelectedRow();
                                    Object selectedValueUser = tablePending.getModel().getValueAt(selectedRow, 1);
                                    Object selectedValuePath = tablePending.getModel().getValueAt(selectedRow, 3);
                                    Object selectedValueServiceTag = tablePending.getModel().getValueAt(selectedRow, 4);
                                    StringSelection selectionPath = new StringSelection(selectedValuePath.toString());  // selection = path of .exe
                                    clipboard.setContents(selectionPath, null);
                                    //processAnswer(requestsPending, selectedValueUser.toString());
                                    Bot.startInstallationOnClient(selectedValueUser.toString(), selectedValueServiceTag.toString(), selectedValuePath.toString());
                                    tablePending.setValueAt(newStatus[0], selectedRow, 0);
                                }
                            }
                        });
                        pending.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == pending) {
                                    setRequest = "pending";
                                    int selectedRow = tablePending.getSelectedRow();
                                    tablePending.setValueAt(newStatus[1], selectedRow, 0);
                                }
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
                    }
                    selectedValue = null;
                }
            });
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
            frame.setTitle("Requests:  [Pending " + requestsPending.size() + " / Approved " + requestsApproved.size() + " / Rejected " + requestsRejected.size() + "]");
        }
    }
}