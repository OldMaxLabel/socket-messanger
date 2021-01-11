package messanger;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Класс ServerReader
 * @author Maxim_I
 * @version 1.0
 *
 * На каждое подключение создается отдельный поток.
 * Поток получает сообщения от клиента.
 */
public class ServerReader implements Runnable {

    private Connection connection;
    private CopyOnWriteArraySet<Connection> clientSocketSet;
    private ArrayBlockingQueue<Message> messageQueue;

    public ServerReader(Connection connection,
                        CopyOnWriteArraySet<Connection> clientSocketSet,
                        ArrayBlockingQueue<Message> messageQueue) {
        this.connection = Objects.requireNonNull(connection);
        this.clientSocketSet = clientSocketSet;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {

            while (true) {
                Message messageFromClient = null;
                try {
                    messageFromClient = connection.readMessage();
//                    System.out.println("Server read message: " + messageFromClient);
                } catch (IOException | ClassNotFoundException ioException) {
                    System.out.println("Connection closed!: " + ioException);
                    try {
                        connection.close();
                    } catch (Exception e) {
                        System.out.println("Connection closed!: " + e);
                    }
                } catch (NullPointerException n) {
                    System.out.println("Connection closed!: " + n);
                    break;
                }

                if (messageFromClient == null) {
                    clientSocketSet.remove(connection);
                    System.out.println("Client disconnected: " + connection);
                    System.out.println(clientSocketSet);
                    break;
                } else if ("/exit".equalsIgnoreCase(messageFromClient.getText())) {
                    clientSocketSet.remove(connection);
                    System.out.println("Client disconnected: " + connection);
                    System.out.println(clientSocketSet);
                    break;
                }

                try {
//                    System.out.println("Server put message: " + messageFromClient);
                    messageQueue.put(messageFromClient);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
