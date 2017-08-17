import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Philipp on 14.08.2017.
 */

public class Network{

    private String ip = "localhost";
    private int port = 42000;
    private Scanner scanner = new Scanner(System.in);
    private Socket socket;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    private boolean accepted = false;
    private ServerSocket serverSocket;
    private int errors=0;
    private boolean yourTurn;
    private boolean isServer = true;

    public Network() {
        System.out.println("Bitte gib deine IP ein: ");//valid ip check
        ip = scanner.nextLine();
        System.out.println("Bitte gib deinen Port ein: ");
        port = scanner.nextInt();
        while (port < 1 || port > 65535) {
            System.out.println("Invalider Port! Bitte gib einen neuen Port ein: ");
            port = scanner.nextInt();
        }
        if (!connect()) initializeServer();
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean isYourTurn(){
        return yourTurn;
    }
    public void swapTurn(){
        yourTurn = !yourTurn;
    }
    public boolean isisServer(){
        return isServer;
    }

    public void listenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("Ein Client hat sich verbunden!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean connect() {
        try {
            socket = new Socket(ip, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
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
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
        } catch (Exception e) {
            e.printStackTrace();
        }
        yourTurn = true;
    }

    public void incrementError(){
        errors++;
    }
    public int getErrors(){
        return errors;
    }
}
