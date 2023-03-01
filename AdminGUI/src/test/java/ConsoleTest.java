import org.junit.Test;

import static org.junit.Assert.*;

public class ConsoleTest {

    @Test
    public void testProcessCommand() {
        Console console = new Console();

        // Test "update server" command
        String output = console.processCommand("update server");
        assertEquals("updating...", output);

        // Test "update client" command
        output = console.processCommand("update client");
        assertEquals(" tippe a serviceTag -> example: update Client G2HS52 \n", output);

        // Test "open disk management" command
        output = console.processCommand("open disk management");
        assertEquals("Client: open disk management", output);

        // Test "help" command
        output = console.processCommand("help");
        assertTrue(output.contains("- update server -> starting Windows updates on server"));
        assertTrue(output.contains("- update client [serviceTag] -> starting Windows updates on client"));
        assertTrue(output.contains("- open disk management ->  Opening disk management"));
        assertTrue(output.contains("- approved clients -> list of all approved requests"));

        // Test "approved clients" command
        output = console.processCommand("approved clients");
        assertTrue(output.contains("information"));

        // Test unknown command
        output = console.processCommand("unknown command");
        assertEquals("Error, unknown service", output);
    }
}
