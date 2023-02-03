import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class TestClass {

    @Test
    public void testAddition() {
        administrator admin = new administrator();
        int adminResult = admin.startAdmin();
        assertEquals(3, adminResult);
    }
}