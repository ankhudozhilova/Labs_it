package task1;

public class Animals_3 {
    public static void main(String[] args){
        System.out.print(animals(2,3,5));
    }

    public static int animals(int chicken, int cow, int pig){
        return chicken * 2 + cow * 4 + pig * 4;
    }
}
