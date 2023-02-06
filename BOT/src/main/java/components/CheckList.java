package components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class CheckList {
    public static String CPDir = "";
    public static JFrame checkFrame;
    public static JTabbedPane checkTabbedPane;
    public static JTable checkTable;
    public static JTable allTable;
    private static DefaultTableModel model;
    private static DefaultTableModel completeModel;
    public CheckList() {
        String[] columnNames = {"Name", "ServiceTag"};
        model = new DefaultTableModel(columnNames, 0);
        completeModel = new DefaultTableModel(columnNames, 0);
    }

    public static void setModel(String user, String serviceTag, String path) {
        String folderPath = "src/main/db/";
        File folder = new File(folderPath);

        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("The specified path is not a directory");
        }

        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.getName().contains(serviceTag)) {
                System.out.println(file.getName() + " >>> " + serviceTag);
                System.out.println("The service tag found in the file name.");
                Object[] rowData = {file.getName(), serviceTag};
                model.addRow(rowData);
                //completeModel.addRow(new Object[]{file.getName()});
                //completeModel.addRow(listOfFiles);
            } else {
                System.out.println("The service tag not found in the file name.");
            }
            compareSelection(model, completeModel);
        }
    }

    public static void compareSelection(DefaultTableModel model, DefaultTableModel completeModel) {


        if (checkFrame == null) {
            checkTable = new JTable(model);
            allTable = new JTable(completeModel);

            checkFrame = new JFrame("Check List");
            checkFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            checkTabbedPane = new JTabbedPane();
            checkTabbedPane.add("List", new JScrollPane(checkTable));
            checkTabbedPane.add("All", new JScrollPane(allTable));

            checkFrame.add(checkTabbedPane);
            checkFrame.pack();
            checkFrame.setVisible(true);
        } else {
            checkTable.setModel(model);
            checkTable.setModel(completeModel);
        }
    }
}
