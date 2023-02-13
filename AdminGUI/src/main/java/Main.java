public class Main {
    static String csvFile = "AdminGUI/src/main/db/fourHeader.csv";

    public static void main(String[] args) {
        try {
            administrator admin = new administrator("admin", 1, "admin", csvFile);
            admin.startAdmin();
        } catch (Exception e) {
            e.printStackTrace();
            main(args);
        }
    }
}
