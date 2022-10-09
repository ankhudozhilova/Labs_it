package task1;

public class Abcmath_10 {
    public static void main(String[] args) {
        System.out.println(abcmath(5 , 2, 1));
    }

    public static boolean abcmath(int a, int b, int c) {
        for (int i = 0; i < b; i++) {
            a += a;
        }

        return a % c == 0;
    }
}
