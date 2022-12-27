import java.io.*;
import java.net.*;

/**
 * Этот интерфейс предназначен для предоставления общего протокола для объектов, которые хотят выполнять код, пока они активны.
 * Например, реализован классом .
 * Runnable просто означает, что поток был запущен и еще не остановлен. RunnableThread
 * Следует использовать если планируется переопределить только run().
 */
// Класс потоков для обхода URL-адресов. По сути реализация Runnable
public class CrawlerTask implements Runnable {
    URLPool pool; // Ссылка на пул URL-адресов
    static final String HREF_TAG = "<a href=\"http"; // HTML форма

    public CrawlerTask(URLPool pool) {
        this.pool = pool;
    }

    /**
     * Переопределение одной из главной состовляющей многопоточного программирования с помощью Thread
     * Именно в методе run() мы прописываем ту логику, которую наш поток должен выполнить
     */
    @Override
    public void run() {
        while (true) {
            URLDepthPair pairUD = pool.getNextPair();
            int nowThisDepth = pairUD.getDepth();
            try {
                // Подключение к серверу и отправка запросов и их принятие.
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress(pairUD.getHost(), 80), 3000);
                sock.setSoTimeout(3000); //устанавливает время ожидания сокета
                System.out.println("Connected to " + pairUD.getURLString());
                PrintWriter output = new PrintWriter(sock.getOutputStream(), true); //метод позволяет сокету отправлять данные
                BufferedReader input = new BufferedReader(new InputStreamReader(sock.getInputStream())); //получает данные с другой стороны соединения
                // Отправка HTTP запроса
                output.println("GET " + pairUD.getPath() + " HTTP/1.1");
                output.println("Host: " + pairUD.getHost());
                output.println("Connection: close");
                output.println();
                output.flush();

                // Собираем ссылки со страницы и добавляем их в пух URL-адрессов
                String lineOfCode;
                int lineSize;
                int startIndex;
                while ((lineOfCode = input.readLine()) != null) {
                    // Проверка, есть ли в текущей строке ссылка
                    boolean foundFullLink = false;
                    int idx = lineOfCode.indexOf(HREF_TAG);
                    if (idx > 0) {
                        // Извлекаем ссылку
                        StringBuilder sb = new StringBuilder();
                        startIndex = idx + 9;
                        char c = lineOfCode.charAt(startIndex);
                        lineSize = lineOfCode.length();
                        while (c != '"' && startIndex < lineSize - 1) {
                            sb.append(c);
                            startIndex++;
                            c = lineOfCode.charAt(startIndex);
                            if (c == '"') {
                                foundFullLink = true;
                            }
                        }
                        // Создание новой пары URL-адресс и глубина
                        String newUrl = sb.toString();
                        if (foundFullLink) {
                            URLDepthPair newPair = new URLDepthPair(newUrl, nowThisDepth + 1);
                            // Добавление этой пары в пул
                            pool.addPair(newPair);
                        }
                    }
                }

                // Закрытие сокета
                sock.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}