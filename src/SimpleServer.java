import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer extends Handler {
    public void start() {
        try (this) {
            ServerSocket s = new ServerSocket(101);//сокет для общения
            Socket clientSocket = s.accept();
            System.out.println("Клиент подключён");
            createThreads(clientSocket);


        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}