import java.net.*;
import java.util.*;

/**
 * Средство поиска классов обрабатывает аргументы командной строки, создает экземпляр
 * Пул URL-адресов, добавляет введенный URL-адрес в пул и создает номер искателя
 * задачи, вводимые с помощью потоков для их выполнения. Затем, когда обход завершен,
 * выводит список найденных URL-адресов.
 */
//Создание нескольких потоков для обработки URL-адресов с корнем по указанному URL-адресу
public class Crawler {


    public static void main(String[] args) throws MalformedURLException {
        if (args.length != 3) {
            System.out.println("usage: java Crawler <URL> <maximum_depth> <num_threads>");
            return;
        }
        // Анализ параметров командной строки
        String startURL = args[0];
        int maxDepth = Integer.parseInt(args[1]);
        int numThreads = Integer.parseInt(args[2]);

        // Настраиваем наш пул URL-адресов и добавляем в него начальный URL-адрес
        URLPool pool = new URLPool(maxDepth);
        URLDepthPair firstPair = new URLDepthPair(startURL, 0);
        pool.addPair(firstPair);

        // Создание потоков
        // Запускаем их, вызывая у объекта метод start()

        for (int i = 0; i < numThreads; i++) {
            CrawlerTask c = new CrawlerTask(pool);
            Thread thread = new Thread(c);
            thread.start();
        }

        // Продолжайте обход до тех пор, пока все потоки не будут ожидать в пуле URL.
        // Это означает, что мы обошли все страницы глубиной меньше или равной maxDepth.
        while (pool.getWaitCount() != numThreads) {
            try {
                // Приводит текущий выполняемый поток в спящий режим (временно прекращает выполнение) в течение заданного количества миллисекунд.
                Thread.sleep(100);
                // Выбрасывается, когда поток находится в ожидании, спящем режиме или иным образом занят.
            } catch (InterruptedException ie) {
                System.out.println("Caught unexpected " + "InterruptedException, ignoring...");
            }
        }
        // Вывод всех найденных URL-адрессов и их глубину
        LinkedList<URLDepthPair> foundUrls = pool.getSeenUrls();
        for (URLDepthPair pair : foundUrls) {
            System.out.println(pair.toString());
        }

        System.exit(0);
    }

}