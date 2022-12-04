import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

//Класс JImageDisplay наследованный от javax.swing.JComponent
//Класс управляет изображением с которым мы работаем
public class JImageDisplay extends JComponent {
    int width; //Ширина
    int height; //Высота
    public BufferedImage bufferedImage;

    //Конструктор JImageDisplay получает целые ширину и высоту и инициализирует поле BufferedImage
    // ссылкой на новое изображение заданной ширины и высоты
    public JImageDisplay(int width, int height) {
        this.width = width;
        this.height = height;

        this.bufferedImage = new BufferedImage(width, height,TYPE_INT_RGB);

        super.setPreferredSize(new Dimension(width, height));
    }

    //Переопределение метода чтобы компонент сам прорисовывал себя
    //null в аргументе ImageObserver, потому что не нуждаемся в этом функционале
    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
    }

    //Методы для записи данных в изображение: clearImage() устанавливает черный цвет для всех пикселов изображения (значение RGB 0) и
    // drawPixel(int x, int y, int rgbColor) задает указанный цвет пиксела.
    // Методы используют один из методов setRGB() класса BufferedImage.
    public void clearImage(){
        int[] rgbArray = new int[getHeight()*getWidth()];
        bufferedImage.setRGB(0, 0, getWidth(), getHeight(),rgbArray, 0, 1);
    }

    public void drawPixel(int x, int y, int rgbColor) {
        bufferedImage.setRGB(x, y, rgbColor);
    }
}
