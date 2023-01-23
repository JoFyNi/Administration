package ressources;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Bot bot = new Bot("RequestBot", 1, "CSV", "src/main/db/request.csv");
        bot.startBot();
        if (Request.getRequest()==true) {
            Thread.sleep(1000);
        } else { Request.getRequest();}
    }
}
