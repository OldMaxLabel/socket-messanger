package messanger;

import java.io.IOException;
import java.util.Objects;

/**
 * Класс ClientReader
 * @author Maxim_I
 * @version 1.0
 *
 * Получает входящие сообщения и выводит в консоль
 */
public class ClientReader implements Runnable {

    private Connection connection;

    public ClientReader(Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    @Override
    public void run() {

            while (true) {

                Message messageFromServer;
                try {
                    messageFromServer = connection.readMessage();
                    System.out.println(messageFromServer);
                } catch (IOException | ClassNotFoundException | NullPointerException e) {
                    System.out.println("ClientReader closed: " + e);
                    try {
                        connection.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    break;
                }


            }

    }

}
