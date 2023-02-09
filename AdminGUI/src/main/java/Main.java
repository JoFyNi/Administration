public class Main {
    static String csvFile = "AdminGUI/src/main/db/fourHeader.csv";

    public static void main(String[] args) {
        try {
            // creating new administration tool
            administrator admin = new administrator("admin", 1, "admin", csvFile);
            admin.startAdmin();
            //AdminConnector adminConnector = new AdminConnector(multicastPORT);
            //adminConnector.connect();
        } catch (Exception e) {
            e.printStackTrace();
            main(args);
        }
    }
}
