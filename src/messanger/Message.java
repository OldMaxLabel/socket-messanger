package messanger;

import java.io.Serializable;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * Класс Message
 * @author Maxim_I
 * @version 1.0
 *
 * Позволяет формировать сообщения ля
 * отправки пользователям
 */
public class Message implements Serializable {

    /**
     * Поле sender
     * отправитель
     */
    private String sender;
    /**
     * Поле text
     * текст сообщения
     */
    private String text;
    /**
     * Поле dateTime
     * дата и время отправки сообщения
     */
    private LocalDateTime dateTime;

    private transient Socket socket;
    private int port;

    /**
     * Конструктор для создания сообщения
     * приватный
     *
     * @param sender строка для имени отправителя
     * @param text строка для текста сообщения
     * @see Message#getMessage(String, String)
     */
    private Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime() {
        dateTime = LocalDateTime.now();
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setPort() {
        this.port = socket.getLocalPort();
    }

    public int getPort() {
        return this.port;
    }

    /**
     * Метод getMessage
     * Метод формирования сообщения
     * статический
     *
     * @param sender строка для имени отправителя
     * @param text строка для текста сообщения
     * @return объект типа Message, сформированное сообщение
     */
    public static Message getMessage(String sender, String text) {
        return new Message(sender, text);
    }

    /**
     * Метод toString
     * Метод для строкового представления сообщения
     * переопределен
     *
     * @return строковое представления объекта типа Message
     */
    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", port='" + this.port + '\'' +
                ", dateTime=" + dateTime.toLocalDate() +
                '}';
    }
}
