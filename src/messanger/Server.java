package messanger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Класс Server
 * @author Maxim_I
 * @version 1.0
 *
 * Все соединения (подключения) сохраняются в сете (потокобезопасном).
 *
 * На каждое подключение создается отдельный поток.
 * Поток ServerReader - получает сообщения от клиента.
 *
 * Поток Writer рассылает сообщения всем клиентам, кроме отправителя.
 */
public class Server {

    private CopyOnWriteArraySet<Connection> clientSocketSet = new CopyOnWriteArraySet<>();
    private ArrayBlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(25, true);

    public void start() throws IOException, ClassNotFoundException {
        try (ServerSocket serverSocket = new ServerSocket(8090)) {

            System.out.println("Server started");

            new Thread(new ServerWriter(clientSocketSet, messageQueue)).start();

            while (true) {

                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                clientSocketSet.add(connection);
                System.out.println("New client join: " + socket);
                System.out.println(clientSocketSet);

                new Thread(new ServerReader(connection, clientSocketSet, messageQueue)).start();
            }

        }

    }

    /**
     * Метод main
     * для запуска сервера
     *
     * @throws Exception ошибка во время работы потоков
     */
    public static void main(String[] args) {

        Server server = new Server();
        try {
            server.start();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }

}
