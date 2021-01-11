package messanger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс Connection
 * @author Maxim_I
 * @version 1.0
 *
 * Для записи и чтения из соединения
 */
public class Connection implements AutoCloseable {

    /**
     * Поле socket
     * позволяет устанавливать соединение клиента и сервера
     */
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Connection(Socket socket) throws IOException {
        this.socket = Objects.requireNonNull(socket);
        this.output = new ObjectOutputStream(this.socket.getOutputStream());
        this.input = new ObjectInputStream(this.socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public void writeMessage(Message message) throws IOException {
        output.writeObject(message);
        output.flush();
    }

    public void writeMessage(Message message, int port, LocalDateTime localDateTime) throws IOException {
        message.setDateTime();
        message.setSocket(socket);
        message.setPort();
        output.writeObject(message);
        output.flush();
    }

    /**
     * Метод readMessage
     * для чтения из потока
     *
     * @return возвращает объект типа Message, сообщение от клиента
     * @throws IOException ошибка во время чтения из потока
     * @throws ClassNotFoundException и приведения к типу
     */
    public Message readMessage() throws IOException, ClassNotFoundException {
            return (Message) input.readObject();
    }

    /**
     * Метод close
     * для автоматического закрытия всех потоков
     *
     * @throws Exception ошибка во время закрытия потока
     */
    @Override
    public void close() throws IOException {
        input.close();
        output.close();
        socket.close();
    }
}
