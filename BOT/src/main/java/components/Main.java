package components;

public class Main {
    static String csvFile = "BOT/src/main/db/fourHeader.csv";

    // random generated csv file from  https://www.convertcsv.com/generate-test-data.htm
    public static void main(String[] args) {
        try {
            Bot bot = new Bot("Bot", 1, "Admin", csvFile);
            bot.startBot();
            Connector.connect();
            //Connector connector = new Connector(multicastPORT);
            //connector.connect();
        } catch (Exception e) {
            e.printStackTrace();
            main(args);
        }
    }
}
