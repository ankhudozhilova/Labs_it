import java.util.Arrays;
public class Task2 {
    public static void main(String[] args){
        System.out.println(repeat("mise", 5));
        System.out.println(differenceMaxMin(new double[]{10, 4, 1, 4, -10, -50, 32, 21}));
        System.out.println(isAvgWhole(new int[]{1, 3}));

        int[] array = new int[]{1, 2, 3};
        cumulativeSum(array);
        for (int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
        System.out.println(getDecimalPlaces("43.20"));
        System.out.println(Fibonacci(3));
        System.out.println(isValid("59001"));
        System.out.println(isStrangePair("ratio", "orator"));
        System.out.println(isPrefix("automation", "auto-"));
        System.out.println(isSuffix("arachnophobia", "-phobia"));
        System.out.println(boxSeq(0));

    }
    public static String repeat(String s, int n) {
        String newString = "";
        for(int i = 0; i < s.length(); i++){
            for(int j = 0; j < n;j++){
                newString += s.charAt(i);
            }
        }
        return newString;
    }

    public static double differenceMaxMin(double[] a){
        Arrays.sort(a);
        double diff = a[a.length - 1] - a[0];
        return diff;
    }

    public static boolean isAvgWhole(int[] a){
        int k = a.length;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += a[i];
        }
        if(sum % k == 0) return true;
        else return false;
    }

    public static int[] cumulativeSum(int[] a){
        for(int i = 1; i < a.length; i++) {
            a[i] += a[i - 1];
        }
        return a;
    }
    public static int getDecimalPlaces(String s){
        int n = s.length();
        int i = 0;

        for(; i < n && s.charAt(i) != '.'; i++);

        if(i == n)
            return 0;
        else
            return n-i-1;
    }

    public static int Fibonacci(int n){
        int[] array = new int[n+1];
        array[0] = array[1] = 1;

        for(int i = 2; i <= n; i++){
            array[i] = array[i-2] + array[i-1];
        }
        return array[n];
    }

    public static boolean isValid(String ticket){
        if(ticket.length() > 5)
            return false;

        for(int i = 0; i < ticket.length();i++){
            if(ticket.charAt(i) < '0' || ticket.charAt(i) > '9')
                return false;
        }
        return true;
    }

    public static boolean isStrangePair(String s_one, String s_two) {
        if(s_one == "" && s_one == s_two)
            return true;
        else if((s_one == "" && s_two != s_one) || (s_two == "" && s_two != s_one))
            return false;
        int n1 = s_one.length() - 1;
        int n2 = s_two.length() - 1 ;
        if(s_one.charAt(0) == s_two.charAt(n2) && s_one.charAt(n1) == s_two.charAt(0))
            return true;
        else
            return false;
    }

    public static boolean isPrefix(String s, String prefix) {
        String pref = "";
        for(int i = 0; i < prefix.length() - 1; i++)
            pref += prefix.charAt(i);

        int n = 0;
        for(int i = 0; i < pref.length(); i++){
            if(s.charAt(i) == pref.charAt(i))
                n++;
        }
        return n == pref.length();
    }

    public static boolean isSuffix(String s, String suffix){
        String suff = "";
        for(int i = 1; i < suffix.length(); i++) {
            suff += suffix.charAt(i);
        }
        int n = 0;
        for(int i = 0; i <= suff.length(); i++){
            if(s.charAt(i + s.length() - suffix.length()) == suffix.charAt(i))
                n++;
        }
        return n == suff.length();
    }
    public static int boxSeq(int n) {
        if(n % 2 == 0)
            return n;
        else
            return n + 2;
    }
}
