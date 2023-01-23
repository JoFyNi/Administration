package ressources;

public class Main {
    static String csvFile = "src/main/db/request.csv";
    public static void main(String[] args) {
        Bot bot = new Bot("Bot", 1, "Admin", csvFile);
        bot.startBot();
    }
}
