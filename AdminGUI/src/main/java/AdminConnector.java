import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AdminConnector {
    public AdminConnector(int port) {
    }
    public static String connectClient(String message) {
        System.out.println("connectClient");
        if (message.equals("start")) {
            System.out.println("start");
            try {
                Socket socket = new Socket("localhost", 8888);
                System.out.println("Connected to server from Admin");
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                getMessage(inputStream);
                Scanner scanner = new Scanner(System.in);
                while (!message.equals("exit chat")) {
                    System.out.print("You: ");
                    message = scanner.nextLine();
                    outputStream.writeUTF(message);
                    outputStream.flush();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                inputStream.close();
                outputStream.close();
                socket.close();
                scanner.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            Thread.onSpinWait();
        }
        return message;
    }
    public static void getMessage(DataInputStream inputStream) throws IOException {
        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                String message = "";
                try {
                    message = inputStream.readUTF();
                    message = "Server: " + message;
                    System.out.println(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(task, 0, 100);
    }
}
