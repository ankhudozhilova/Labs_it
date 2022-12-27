import java.net.*;

public class URLDepthPair {
    URL url; // URL-адрес для этой пары
    int depth; // Глубина поиска для этой пары

    /**
     * @param url   RL-адрес для этой пары
     * @param depth Глубина поиска для этой пары
     * @throws MalformedURLException Выбрасывается, чтобы указать, что произошел искаженный URL-адрес.
     *                               Либо в строке спецификации не может быть найден юридический протокол, либо строка не может быть проанализирована.
     */
    public URLDepthPair(String url, int depth) throws MalformedURLException {
        this.url = new URL(url);
        this.depth = depth;
    }

    // Вывод строки URL и глубина
    public String toString() {
        return url + "\t" + depth;
    }

    // Возвращает имя хоста для пары.
    public String getHost() {
        return url.getHost();
    }

    // Возвращает путь для пары.
    public String getPath() {
        return url.getPath();
    }

    // Возвращает глубину поиска пары.
    public int getDepth() {
        return depth;
    }

    // Возвращает строку URL пары.
    public String getURLString() {
        return url.toString();
    }

}