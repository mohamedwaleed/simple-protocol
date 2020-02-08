package my.simple.protocol.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainTest {

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public static void sendMessage(String msg) throws IOException {
        out.println(msg);
    }

    public static void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        try {
            startConnection("localhost", 50000);
            String resp1 = in.readLine();
            System.out.println(resp1);

            sendMessage("HI, I AM Mohamed");

            resp1 = in.readLine();
            System.out.println(resp1);

            sendMessage("BYE MATE!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
