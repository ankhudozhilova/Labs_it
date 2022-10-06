package Lab_2;

// Создание класса для представления точек в трехмерном пространстве
class Point3d{
    private double xCoord; //Координата х
    private double yCoord; //Координата y
    private double zCoord; //Координата z

    //Конструктор инициализации
    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    //Конструктор по умолчанию
    public Point3d(){
        this(0.0, 0.0, 0.0);
    }
    //Возвращение координаты х
    public double getX () {
        return xCoord;
    }
    //Возвращение координаты y
    public double getY() {
        return yCoord;
    }
    //Возвращение координаты z
    public double getZ() {
        return zCoord;
    }

    public void setX(double val) {
        xCoord = val;
    } //Установка значения координаты x
    public void setY(double val) {
        yCoord = val;
    } //Установка значения координаты y
    public void setZ(double val) {
        zCoord = val;
    } //Установка значения координаты z

    //Метод для вычисления расстояния между двумя точками
    public double distanceTo(Point3d t) {
        double dist = Math.sqrt(Math.pow(this.getX() - t.getX(), 2) + Math.pow(this.getY() - t.getY(), 2) + Math.pow(this.getZ() - t.getZ(), 2));
        double scale = Math.pow(10,2);
        dist = Math.ceil(dist*scale)/scale;
        return dist;
    }
};
