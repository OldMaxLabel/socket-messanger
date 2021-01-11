package messanger;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

/**
 * Класс Client
 *
 * @author Maxim_I
 * @version 1.0
 *
 * Читает сообщения из консоли, формирует объекты Message и отправляет на сервер.
 *
 * Получает входящие сообщения и выводит в консоль.
 */
public class Client {

    private String ip;
    private int port;

    public Client(String ip, int port) {
        this.ip = Objects.requireNonNull(ip);
        this.port = Objects.requireNonNull(port);
    }

    public void start() throws Exception {

        try (Socket socket = new Socket(ip, port);
             Connection connection = new Connection(socket)) {

            Thread writer = new Thread(new ClientWriter(connection));
            Thread reader = new Thread(new ClientReader(connection));
            writer.start();
            reader.start();
            writer.join();
            reader.join();

        } catch (IOException e) {
            System.out.println("Client closed: " + e);
        }

    }

    /**
     * Метод main
     * для запуска клиентов
     *
     * @throws Exception ошибка во время работы потоков
     */
    public static void main(String[] args) {

        try {
            new Client("127.0.0.1", 8090).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
