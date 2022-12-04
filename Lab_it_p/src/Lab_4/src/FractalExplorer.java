import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int interfaceSize; //Размер экрана
    private JImageDisplay displayImage; //Ссылка на JImageDisplay для обновления отображения различными методами по мере вычисления фрактала
    private FractalGenerator fractalGenerator; //Объект FractalGenerator для каждого типа фрактала
    private Rectangle2D.Double complexPlaneRange; //Объект Rectangle2D.Double задает отображаемый диапазон в комплексной области

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(400); //Задание размеров окна
        fractalExplorer.createAndShowGUI (); //Создание пользовательский интерфейс
        fractalExplorer.drawFractal(); //Отрисовка фрактала
    }

    public FractalExplorer(int interfaceSize) {
        this.interfaceSize = interfaceSize;
        this.fractalGenerator = new Mandelbrot();
        this.complexPlaneRange = new Rectangle2D.Double(0, 0, 0, 0);
        fractalGenerator.getInitialRange(this.complexPlaneRange);
    }

    //Метод createAndShowGUI() инициализирует графический пользовательский интерфейс Swing
    public void createAndShowGUI() {
        displayImage = new JImageDisplay(interfaceSize, interfaceSize);
        displayImage.addMouseListener(new MouseListener());
        JButton button = new JButton("Reset"); //Создание кнопки
        button.addActionListener(new ResetActionListener());
        JFrame frame = new JFrame("Fractal Generator"); //Заголовок для приложения
        frame.setLayout(new BorderLayout());
        frame.add(displayImage, BorderLayout.CENTER); //Объект картинки JImageDisplay
        frame.add(button, BorderLayout.SOUTH); //Добавляем кнопку в позицию BorderLayout.SOUTH
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Операция "exit" по умолчанию при закрытии окна
        //Последовательность вызовов, которые правильно расположат содержимое окна, сделают окно видимым и запретят изменение размеров окна
        frame.pack ();
        frame.setVisible (true);
        frame.setResizable (false);
    }

    //Вспомогательный метод для прорисовки фрактала
    //Метод перебирает точки изображения и вычисляем количество итераций для соответствующей координаты в области отображения фрактала.

    private void drawFractal (){
        for (int x = 0; x < interfaceSize; x++) {
            for (int y = 0; y < interfaceSize; y++) {
                int count = fractalGenerator.numIterations(
                        FractalGenerator.getCoord(complexPlaneRange.x, complexPlaneRange.x + complexPlaneRange.width, interfaceSize, x),
                        FractalGenerator.getCoord(complexPlaneRange.y, complexPlaneRange.y + complexPlaneRange.width, interfaceSize, y));
                //Получаем значение координат с плавающей точкой для указанной точки изображения

                int rgbColor;
                if(count == -1)
                    rgbColor = 0; //черный цвет точки
                else {
                    float hue = 0.7f + (float) count / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f); //Вычисляем цвет
                }
                displayImage.drawPixel(x, y, rgbColor);
            }

        }

        displayImage.repaint();
    }

    //Класс для обработки событий java.awt.event.ActionListener от кнопки сброса
    //Обработчик сбрасывает диапазон отображения в начальное значение, указанное при инициализации генератора
    private class ResetActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            displayImage.clearImage();
            fractalGenerator.getInitialRange(complexPlaneRange);
            drawFractal(); //перерисовка фрактала
        }
    }
    //Внутренний класс для обработки событий java.awt.event.MouseListener от JImageDisplay
    //Получив сообщение о щелчке мышью, обработчик преобразует координаты точки изображения в отображаемую область фрактала

    private class MouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            double x = FractalGenerator.getCoord(complexPlaneRange.x, complexPlaneRange.x + complexPlaneRange.width, interfaceSize, e.getX());
            double y = FractalGenerator.getCoord(complexPlaneRange.x, complexPlaneRange.x + complexPlaneRange.width, interfaceSize, e.getX());

            fractalGenerator.recenterAndZoomRange(complexPlaneRange, x, y, 0.5); //Вызов метода генератора srecenterAndZoomRange() с новыми координатами и масштабом 0.5.
            drawFractal();
        }
    }
}
