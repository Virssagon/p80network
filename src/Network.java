import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Philipp on 14.08.2017.
 */
@SuppressWarnings("unused")
public class Network {

    private String ip = "localhost";
    private int port = 42000;
    private Scanner scanner = new Scanner(System.in);
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public Network() {
        System.out.println("Bitte gib deine IP ein: ");//valid ip check
        ip = scanner.nextLine();
        System.out.println("Bitte gib deinen Port ein: ");
        port = scanner.nextInt();
        while (port < 1 || port > 65535) {
            System.out.println("Invalider Port! Bitte gib einen neuen Port ein: ");
            port = scanner.nextInt();
        }
    }
}
