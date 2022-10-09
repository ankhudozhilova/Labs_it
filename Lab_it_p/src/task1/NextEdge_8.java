package task1;

public class NextEdge_8 {
    public static void main(String[] args) {
        System.out.println(nextEdge(10, 8));
    }

    public static int nextEdge(int a, int b){
        return a + b - 1;
    }
}
