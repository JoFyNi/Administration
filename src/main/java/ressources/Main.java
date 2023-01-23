package ressources;

public class Main {
    static String csvFile = "src/main/db/request.csv";
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
