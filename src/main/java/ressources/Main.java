package ressources;

public class Main {
    static String csvFile = "src/main/db/fourHeader.csv";
    // random generated csv file from  https://www.convertcsv.com/generate-test-data.htm

    public static void main(String[] args) {
        try {
            Bot bot = new Bot("Bot", 1, "Admin", csvFile);
            bot.startBot();
        } catch (Exception e) {
            e.printStackTrace();
            main(args);
        }
    }
}
