package Admin;

import static ressources.connection.multicastPORT;

public class Main {
    static String csvFile = "src/main/db/fourHeader.csv";

    public static void main(String[] args) {
        try {
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
