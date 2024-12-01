import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public interface Node extends AutoCloseable {
    void sendGetM(BufferedReader reader,BufferedWriter out,boolean get);
    void message(String word, boolean flag, BufferedWriter out) throws IOException;
    void createThreads(Socket clientSocket);
}
