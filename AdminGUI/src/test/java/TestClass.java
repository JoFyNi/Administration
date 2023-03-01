import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestClass {

    private Object List;

    @Test
    public void testAddition() throws Throwable {

        // starten des admin programms (allgemein)
        String csvFile = "AdminGUI/src/main/db/fourHeader.csv";
        administrator admin = new administrator("admin", 1, "admin", csvFile);
        Assert.assertEquals(0, admin.startAdmin());

        //String pending = "n";
        //String approved = "f";
        //String rejected = "t";
        //double result = admin.checkRequests(pending, approved, rejected);

        //java.util.List<Request> pendingList = new ArrayList<>();
        //java.util.List<Request> approvedList = new ArrayList<>();
        //java.util.List<Request> rejectedList = new ArrayList<>();
        //boolean resultat = admin.updateRequests(pendingList, approvedList, rejectedList);


        //überprüfen der einzelnen methoden
        // checkRequest
        administrator.checkRequests(connection.requestsPending, connection.requestsApproved, connection.requestsRejected);
        // updateRequest -> cleared alle Listen und schreibt sie neu (aktualisiert sie))
        administrator.updateRequests(connection.requestsPending, connection.requestsApproved, connection.requestsRejected);
        assertEquals(List, connection.requestsPending.size());
        assertEquals(List, connection.requestsApproved.size());
        assertEquals(List, connection.requestsRejected.size());
        // displayPending -> überprüfung, ob die gesamte (alle mit n/N) auf dem JTable abgebildet werden
        // die Buttons und mouse klicks die richtigen Werte weiter bzw. zurückgeben
        administrator.displayPending(connection.requestsPending, connection.requestsApproved, connection.requestsRejected);
        assertEquals(List, connection.requestsPending.size());

    }

    public static class hasTempFolder {
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
