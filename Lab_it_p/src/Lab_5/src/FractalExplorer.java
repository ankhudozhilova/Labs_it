import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.imageio.ImageIO;

public class FractalExplorer {
    private int interfaceSize; //Размер экрана
    private JImageDisplay displayImage; //Ссылка на JImageDisplay для обновления отображения различными методами по мере вычисления фрактала
    private FractalGenerator fractalGenerator; //Объект FractalGenerator для каждого типа фрактала
    private Rectangle2D.Double complexPlaneRange; //Объект Rectangle2D.Double задает отображаемый диапазон в комплексной области
    private JComboBox<FractalGenerator> comboBox; //Возможность выбора фрактала

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(400); //Задание размеров окна
        fractalExplorer.createAndShowGUI (); //Создание пользовательский интерфейс
        fractalExplorer.drawFractal(); //Отрисовка фрактала
    }

    //Конструктор сохранения изображения, инициализиации объектов диапазона и генератора фракталов
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

        JButton button = new JButton("Reset"); //Создание кнопки сброса
        button.addActionListener(new ResetActionListener());
        JButton buttonSave = new JButton("Save image"); //Создание кнопки сохранения изображения
        buttonSave.addActionListener(new SaveActionListener());

        JLabel label = new JLabel("Fractal:"); //Создание настроек для возможности выбора фрактала
        comboBox = new JComboBox<>();
        //Фракталы между которыми пользователю предоставляется выбор
        comboBox.addItem(new Mandelbrot());
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());
        comboBox.addActionListener(new ComboActionListener());

        JPanel jPanelFractal = new JPanel(); //Размещение панелей
        JPanel jPanelButtons = new JPanel();
        jPanelFractal.add(label, BorderLayout.CENTER);
        jPanelFractal.add(comboBox, BorderLayout.CENTER);
        jPanelButtons.add(button, BorderLayout.CENTER);
        jPanelButtons.add(buttonSave, BorderLayout.CENTER);

        JFrame frame = new JFrame("Fractal Generator"); //Заголовок для приложения
        frame.setLayout(new BorderLayout());
        frame.add(displayImage, BorderLayout.CENTER); //Объект картинки JImageDisplay
        frame.add(jPanelFractal, BorderLayout.NORTH);
        frame.add(jPanelButtons, BorderLayout.SOUTH); //Добавляем кнопку в позицию BorderLayout.SOUTH
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

    //Сохранение изображения (кнопка Save Image)
    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Настройка средства выбора файлов чтобы сохранять изображения только в формате PNG
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);//Гарантирует, что средство выбора не разрешит пользователю сохранить в любом другом формате
            int t = fileChooser.showSaveDialog(displayImage);
            if (t == JFileChooser.APPROVE_OPTION) {
                try {
                    ImageIO.write(displayImage.bufferedImage, "png", fileChooser.getSelectedFile());
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(displayImage, ee.getMessage(), "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    //Восстановление масштаба, перерисовка фрактала (combobox)
    private class ComboActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fractalGenerator = (FractalGenerator) comboBox.getSelectedItem();
            fractalGenerator.getInitialRange(complexPlaneRange);
            drawFractal();
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
