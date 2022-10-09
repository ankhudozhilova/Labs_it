package task1;

public class Operation_5 {
    public static void main(String[] args){
        System.out.println(operation(24, 26, 2));
    }
    public static String operation(int N, int a, int b){
        if(a + b == N) return "added";
        else if (a - b == N || b - a == N) return "subtracted";
        else if (a / b == N || b / a == N) return "divided";
        else if (a * b == N) return "multiplied";
        else return "none";
    }
}
