import java.util.Scanner;

class Lab1 {
    public static void main(String[] args) {
        Point3d pt1, pt2, pt3; //Создание объектов класса Point3d
        Scanner console = new Scanner(System.in);
        //Ввод координат каждой точки
        Double x, y, z;
        Double x1, y1, z1;
        Double x2, y2, z2;
        x = console.nextDouble();
        y = console.nextDouble();
        z = console.nextDouble();
        pt1 = new Point3d(x, y, z);
        x1 = console.nextDouble();
        y1 = console.nextDouble();
        z1 = console.nextDouble();
        pt2 = new Point3d(x1, y1, z1);
        x2 = console.nextDouble();
        y2 = console.nextDouble();
        z2 = console.nextDouble();
        pt3 = new Point3d(x2, y2, z2);

        //Сравнение: проверка точек на равенство и вывод сообщения об ошибке при некорректном вводе данных
        if((x.equals(x1) && y.equals(y1) && z.equals(z1)) || (x.equals(x2) && y.equals(y2) && z.equals(z2)) || (x.equals(x2) && y.equals(y2) && z.equals(z2))) {
            System.out.println("Error");
        }
        else {
            System.out.println(computeArea(pt1, pt2, pt3));
        }
    }

    //Рассчет площади по формуле Герона
    public static double computeArea(Point3d p1, Point3d p2, Point3d p3) {
        double a = p1.distanceTo(p2);
        double b = p2.distanceTo(p3);
        double c = p3.distanceTo(p1);
        double pp = (a + b + c)/2;
        double area =Math.sqrt(pp*(pp - a)*(pp - b)*(pp - c));

        //В случае если треугольник не существует
        /*
        if(a + b > c && a + c > b && b + c > a) {
            return area;
        }
        else {
            return 0.0;
        }
        */
        return area;
    }
}