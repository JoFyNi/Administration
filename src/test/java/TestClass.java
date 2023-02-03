import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import ressources.Bot;

public class TestClass {

    @Test
    public void testAddition() {
        Bot bot = new Bot("Bot", 1, "Admin", "src/main/db/fourHeader.csv");
        int botResult = bot.startBot();
        assertEquals(3, botResult);
    }
}