import java.io.*;
import java.net.Socket;

public class SimpleClient extends Handler
{
    public void start() {
        try (this) {
            //сокет для общения
            Socket clientSocket = new Socket("localhost", 101);
            clientSocket.setSoTimeout(30000);
            System.out.println("Сервер подключён");
            createThreads(clientSocket);

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}