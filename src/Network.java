import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Philipp on 14.08.2017.
 */

public class Network {

    private String ip = "localhost";
    private String name = "Guest";
    private int port = 42000;
    public Socket socket;
    public DataOutputStream out;
    public DataInputStream in;
    private ServerSocket serverSocket;

    private boolean accepted = false;
    private boolean yourTurn;
    private boolean isServer = true;



    public Network() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bitte gib deine IP ein: ");//valid ip check
        ip = scanner.nextLine();
        System.out.println("Bitte gib deinen Spielernamen ein: ");
        name = scanner.nextLine();
        if (!connect()) initializeServer();
    }

    public void evaluateInputStream() {
        String s = "";
        try {
            if (isAccepted() && (in.available() != 0)) {
                s = in.readUTF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.equals((s.length() != 0 ? s.substring(0, 1).toLowerCase() : s), "!")) {
            //Hier kommen alle möglichen befehle in einem switch hin in der form !kick !aufgeben !...
        } else {
            //Kein Befehl? dann Sende als Chat
        }
    }

    public void listenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("Ein Client hat sich verbunden!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean connect() {
        try {
            socket = new Socket(ip, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            accepted = true;
        } catch (IOException e) {
            System.out.println("Verbindung zu Adresse: " + ip + ":" + port + " konnte nicht hergestellt werden. | Starte Server!");
            return false;
        }
        System.out.println("Verbindung zum Server erfolgreich hergestellt!");
        yourTurn = false;
        isServer = false;
        return true;
    }

    public void initializeServer() {
        try {
            System.out.println(InetAddress.getByName(ip));
            serverSocket = new ServerSocket(port, 8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        yourTurn = true;
    }

    public void disconnectPlayerFromServer() {
        try {
            out.writeBoolean(true);
            serverSocket.close();
            System.out.println("Spieler wurde gekickt!");
            initializeServer();
            System.out.println("Warte auf neuen Spieler... ");
            listenForServerRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isAccepted() {
        return accepted;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void swapTurn() {
        yourTurn = !yourTurn;
    }

    public boolean getisServer() {
        return isServer;
    }
}
