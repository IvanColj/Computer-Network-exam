import java.io.*;
import java.net.Socket;

public class Handler implements Node {

    public BufferedReader in; // поток чтения из сокета
    public BufferedWriter out; // поток записи в сокет
    public BufferedReader reader; // нам нужен ридер читающий с консоли
    private volatile boolean running = true; // Флаг для управления потоками
    Thread getMsg, sendMsg; // Потоки


    public void sendGetM(BufferedReader reader,BufferedWriter out,boolean get) {
        try {
            while (running) {
                String word = reader.readLine();
                message(word, get, out);
            }
        } catch (IOException _) {
            System.out.println("Соединение закрыто");
        }
    }


    public void close() throws IOException {
        reader.close();
        in.close();
        out.close();
        getMsg.interrupt();
        sendMsg.interrupt();
    }

    public void message(String word, boolean flag, BufferedWriter out) throws IOException {
        if (word.contains("exit")) {
            out.write("Другой юзер: exit" + "\n");
            out.flush();
            running = false;
            close();
        } else if (flag) {
            System.out.println(word);
        } else {
            out.write("Другой юзер: " + word + "\n");
            out.flush();
        }
    }
    public void createThreads(Socket clientSocket){
        try {
            this.reader = new BufferedReader(new InputStreamReader(System.in));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getMsg = new Thread(()-> sendGetM(in,out,true));
        sendMsg = new Thread(()-> sendGetM(reader,out,false));

        getMsg.start();
        sendMsg.start();

        try {
            getMsg.join();
            sendMsg.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}