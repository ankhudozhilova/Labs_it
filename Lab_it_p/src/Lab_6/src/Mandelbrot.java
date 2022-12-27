import java.awt.geom.Rectangle2D;

//Дочерний класс FractalGenerator Mandelbrot
public class Mandelbrot extends FractalGenerator{

    public static final int MAX_ITERATIONS = 2000; //константа для указания "максимального числа итераций"

    //Метод getInitialRange(Rectangle2D.Double) указывает генератору фрактала, какую область брать для его вычисления
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    //Реализация итеративной функции фрактала Мандельброта
    @Override
    public int numIterations(double x, double y) {
        int i = 0;

        double mnim = 0; //мнимая часть
        double d = 0; //действительная часть

        double help = 0; //вспомогательная(временная переменная)

        while (i < MAX_ITERATIONS && d*d + mnim*mnim < 4){
            help = d * d - mnim * mnim + x; //корректирровка значения действительной части(х)
            mnim = 2 * d * mnim + y; //корректирровка значения мнимой части (у)
            d = help;
            i++;
        }

        if (i == MAX_ITERATIONS) return -1; //указатель на то, что точка не вышла за пределы множества
        return i;
    }
    @Override
    public String toString() {
        return "Mandelbrot";
    }

}
