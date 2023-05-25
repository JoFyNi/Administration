import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AdministratorTest {
    private String csvFile = "AdminGUI/src/main/db/fourHeader.csv";

    @Test
    public void testCheckRequests() {
        List<Request> requestsPending = new ArrayList<>();
        List<Request> requestsApproved = new ArrayList<>();
        List<Request> requestsRejected = new ArrayList<>();
        Administrator.csvFile = csvFile;
        Administrator.checkRequests(requestsPending, requestsApproved, requestsRejected);
        assertEquals(3, requestsPending.size());
        assertEquals(1, requestsApproved.size());
        assertEquals(1, requestsRejected.size());
    }

    @Test
    public void testUpdateRequests() {
        List<Request> requestsPending = new ArrayList<>();
        List<Request> requestsApproved = new ArrayList<>();
        List<Request> requestsRejected = new ArrayList<>();
        Administrator.csvFile = csvFile;
        Administrator.updateRequests(requestsPending, requestsApproved, requestsRejected);
        assertEquals(3, requestsPending.size());
        assertEquals(1, requestsApproved.size());
        assertEquals(1, requestsRejected.size());
    }
}

