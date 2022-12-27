import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private int interfaceSize; //Размер экрана
    private JImageDisplay displayImage; //Ссылка на JImageDisplay для обновления отображения различными методами по мере вычисления фрактала
    private FractalGenerator fractalGenerator; //Объект FractalGenerator для каждого типа фрактала
    private Rectangle2D.Double complexPlaneRange; //Объект Rectangle2D.Double задает отображаемый диапазон в комплексной области
    private JComboBox<FractalGenerator> comboBox; //Возможность выбора фрактала
    private int rowsRemaining = 0;
    private JButton buttonReset;
    private JButton buttonSave;

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(400); //Задание размеров окна
        fractalExplorer.initGUI(); //Создание пользовательский интерфейс
        fractalExplorer.drawFractal(); //Отрисовка фрактала
    }

    //Конструктор сохранения изображения, инициализиации объектов диапазона и генератора фракталов
    private FractalExplorer(int displaySize) {
        this.interfaceSize = displaySize;
        this.fractalGenerator = new Mandelbrot();
        this.complexPlaneRange = new Rectangle2D.Double(0, 0, 0, 0);
        fractalGenerator.getInitialRange(this.complexPlaneRange);
    }

    //Метод createAndShowGUI() инициализирует графический пользовательский интерфейс Swing
    public void initGUI() {
        //Настройки отображения
        displayImage = new JImageDisplay(interfaceSize, interfaceSize);
        displayImage.addMouseListener(new MouseListener());

        //Кнопка для сброса изображения
        buttonReset = new JButton("Reset");
        buttonReset.addActionListener(new ResetActionListener());
        //Кнопка для сохранения изображения
        buttonSave = new JButton("Save image");
        buttonSave.addActionListener(new SaveActionListener());

        //Настройки для выбора фрактала
        JLabel label = new JLabel("Fractal:");
        comboBox = new JComboBox<>();
        comboBox.addItem(new Mandelbrot()); //Фракталы между которыми пользователю предоставляется выбор
        comboBox.addItem(new Tricorn());
        comboBox.addItem(new BurningShip());
        comboBox.addActionListener(new ComboActionListener());

        //Размещение панелей
        JPanel jPanelFractal = new JPanel();
        JPanel jPanelButtons = new JPanel();
        jPanelFractal.add(label, BorderLayout.CENTER);
        jPanelFractal.add(comboBox, BorderLayout.CENTER);
        jPanelButtons.add(buttonReset, BorderLayout.CENTER);
        jPanelButtons.add(buttonSave, BorderLayout.CENTER);

        //Размещение содержимого окна
        JFrame frame = new JFrame("Fractal generation"); //Заголовок для приложения
        frame.setLayout(new BorderLayout());
        frame.add(displayImage, BorderLayout.CENTER); //Объект картинки JImageDisplay
        frame.add(jPanelFractal, BorderLayout.NORTH);
        frame.add(jPanelButtons, BorderLayout.SOUTH); //Добавляем кнопку в позицию BorderLayout.SOUTH
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Операция "exit" по умолчанию при закрытии окна
        //Последовательность вызовов, которые правильно расположат содержимое окна, сделают окно видимым и запретят изменение размеров окна
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    //Метод для вывода на экран фрактала
    private void drawFractal() {
        enableUI(false); //Отключение GUI во время рисования
        rowsRemaining = interfaceSize;
        //Для каждой строки создается рабочий объект
        for (int i = 0; i < interfaceSize; i++) {
            FractalWorker curRow = new FractalWorker(i);
            curRow.execute(); //Запуск задачи в фоновом режиме
        }
    }

    //Включение или отключение пользовательского интерфейса
    public void enableUI(boolean val) {
        buttonSave.setEnabled(val);
        buttonReset.setEnabled(val);
        comboBox.setEnabled(val);
    }

    //Восстановление масштаба, перерисовка фрактала (кнопка Reset)
    private class ResetActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayImage.clearImage();
            fractalGenerator.getInitialRange(complexPlaneRange);
            drawFractal();
        }
    }

    //Сохранение изображения (кнопка Save Image)
    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG Images", "png");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setAcceptAllFileFilterUsed(false);
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

    //Обработка события о щелчке мышью (нажимая на какое-либо место на фрактальном отображении, мы увеличиваем его)
    //Получив сообщение о щелчке мышью, обработчик преобразует координаты точки изображения в отображаемую область фрактала
    private class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (rowsRemaining > 0) return;
            double x = FractalGenerator.getCoord(complexPlaneRange.x, complexPlaneRange.x + complexPlaneRange.width, interfaceSize, e.getX());
            double y = FractalGenerator.getCoord(complexPlaneRange.y, complexPlaneRange.y + complexPlaneRange.width, interfaceSize, e.getY());
            fractalGenerator.recenterAndZoomRange(complexPlaneRange, x, y, 0.5);
            drawFractal();
        }
    }

    //Вычисление значений цвета для одной строки фрактала
    private class FractalWorker extends SwingWorker<Object, Object> {
        //поля
        private int yCoord; //Координата y вычисляемой строки
        private int[] colors; //Вычисленные значения RGB для каждого пикселя в этой строке

        public FractalWorker(int yCoord) {
            this.yCoord = yCoord;
        }

        //Выполнение длительной задачи в фоновом потоке
        @Override
        public Object doInBackground() throws Exception {
            colors = new int[interfaceSize];
            //Цикл сохраняет каждое значение RGB в соответствующем элементе массива
            for (int curX = 0; curX < interfaceSize; curX++) {
                //Вычисление количества итераций
                int count = fractalGenerator.numIterations(
                        FractalGenerator.getCoord(complexPlaneRange.x, complexPlaneRange.x + complexPlaneRange.width, interfaceSize, curX),
                        FractalGenerator.getCoord(complexPlaneRange.y, complexPlaneRange.y + complexPlaneRange.width, interfaceSize, yCoord)
                );
                int rgbColor;
                if (count == -1) rgbColor = 0; //Точка не выходит за границы: цвет - чёрный
                else { //Иначе, значение цвета = кол-ву итераций
                    float hue = 0.7f + (float) count / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                colors[curX] = rgbColor;
            }
            return null;
        }

        //Вызывается при завершении фоновой задачи(в потоке обработки событий поэтому взаимодействует с интерфейсом)
        @Override
        public void done() {
            //Изменение текущей строки
            for (int curX = 0; curX < interfaceSize; curX++) {
                displayImage.drawPixel(curX, yCoord, colors[curX]);
            }
            //Перерисовка указанной области
            displayImage.repaint(0,0,yCoord,interfaceSize,1);
            rowsRemaining --;
            if (rowsRemaining == 0) enableUI(true);
        }
    }
}