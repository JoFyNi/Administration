public class Main {
    static String csvFile = "AdminGUI/src/main/db/fourHeader.csv";

    public static void main(String[] args) {
        try {
            // creating new administration tool
            Administrator admin = new Administrator("admin", 1, "admin", csvFile);
            admin.startAdmin();

            Monitoring MS = new Monitoring();
            MS.monitorSystem();
        } catch (Exception e) {
            e.printStackTrace();
            main(args);
        }
    }
}
