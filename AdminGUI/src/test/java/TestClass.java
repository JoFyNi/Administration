import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestClass {

    @Test
    public void testAddition() {
        String csvFile = "AdminGUI/src/main/db/fourHeader.csv";
        administrator admin = new administrator("admin", 1, "admin", csvFile);
        int adminResult = admin.startAdmin();
        assertEquals(1, adminResult); // expected result?
    }

    public static class HasTempFolder {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        @Test
        public void testUsingTempFolder() throws IOException {
            folder.getRoot(); // Previous file permissions: `drwxr-xr-x`; After fix:`drwx------`
            File createdFile= folder.newFile("testFile.txt"); // unchanged/irrelevant file permissions
            File createdFolder= folder.newFolder("subfolder"); // unchanged/irrelevant file permissions
            // ...
        }
    }
}
