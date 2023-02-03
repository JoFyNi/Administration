import java.awt.*;
import java.awt.datatransfer.Clipboard;

public class Request {
    public char status;
    public String user;
    public String email;
    public String path;
    public String host;
    String ip;
    public String currentDate;
    static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    static char[] newStatus = {'t','f','n'};
    public Request(String test) {
    }
    public Request() {
    }
    public Request(char status, String user, String email, String path, String host, String currentDate) {
        this.status = status;
        this.user = user;
        this.email = email;
        this.path = path;
        this.host = host;
        this.currentDate = currentDate;
    }
}