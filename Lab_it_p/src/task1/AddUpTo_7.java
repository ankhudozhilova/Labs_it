package task1;

public class AddUpTo_7 {
    public static void main(String[] args){
        System.out.println(addUpTo(7));
    }

    public static int addUpTo(int n) {
        /*int ans = 0;
        for(int i = 1; i <= n; i++) {
            ans += i;
        }
         */
        return (2 + (n - 1))*n/2;
    }
}
