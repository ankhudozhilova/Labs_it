package task1;

public class Profitable_4 {
    public static void main(String[] args){
        System.out.println(profitableGamble(0.2, 50, 9));
    }
    public static boolean profitableGamble(double prob, int prize, int pay){
        return prob*prize > pay;

    }
}
