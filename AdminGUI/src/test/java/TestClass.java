import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestClass {

    @Test
    public void testAddition() {
        administrator admin = new administrator();
        int adminResult = admin.startAdmin();
        assertEquals(3, adminResult);
    }

    public static class HasTempFolder {
        @Rule
        public TemporaryFolder folder = new TemporaryFolder();

        @Test
        public void testUsingTempFolder() throws IOException {
            folder.getRoot(); // Previous file permissions: `drwxr-xr-x`; After fix:`drwx------`
            File createdFile= folder.newFile("myfile.txt"); // unchanged/irrelevant file permissions
            File createdFolder= folder.newFolder("subfolder"); // unchanged/irrelevant file permissions
            // ...
        }
    }
}
