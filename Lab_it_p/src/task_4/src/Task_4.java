import java.util.*;

public class Task_4 {
    public static void main(String[] args) {
        System.out.println(Bessie(10,7, "hello my name is Bessie and this is my essay"));
        System.out.println(split("((())())(()(()())))("));
        System.out.println(toCamelCase("is_modal_open"));
        System.out.println(toSnakeCase("getColor"));
        System.out.println(overTime(new double[]{13.25, 15, 30, 1.5}));
        System.out.println(BMI("154 pounds", "2 meters"));
        System.out.println(bugger(999));
        System.out.println(toStarShorthand("abbccc"));
        System.out.println(doesRhyme("Sam I am!", "Green eggs and hAm."));
        System.out.println(trouble(9999777, 88877));
        System.out.println(countUniqueBooks("AZYWABBCATTTA", 'A'));
    }

    public static String Bessie(int n, int k, String s) {
        int count = k;
        String rez = "";
        String[] parts = s.split(" ");
        for(int i = 0; i < parts.length; i++) {
            if(parts[i].length() < count) {
                rez += parts[i] + ' ';
                count -= parts[i].length();
            }
            else if(parts[i].length() == count){
                rez += parts[i] + '\n';
                count = k;
            }
            else if(parts[i].length() > count){
                rez += '\n' + parts[i] + ' ';
                count = k - parts[i].length();
            }
        }
        return rez;
    }

    public static List<String> split(String s) {
        Stack<Character> st = new Stack<>();
        List<String> array = new ArrayList<>();
        int l = 0;
        int r = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!st.empty() && c == ')') {
                st.pop();
                if(st.empty()) {
                    array.add(s.substring(l, r+1));
                    l = r + 1;
                }
            }
            else if (c == '(')
                st.push(c);
            r++;
        }
        return array;
    }

    public static String toCamelCase(String s) {
        String[] s1 = s.split("_");
        String rez = "";
        for(int i = 1; i < s1.length; i++){
            String k = String.valueOf(s1[i].charAt(0));
            s1[i] = k.toUpperCase() + s1[i].substring(1);
        }

        for(int i = 0; i < s1.length; i++)
            rez += s1[i];
        return rez;
    }

    public static String toSnakeCase(String s) {
        String rez = "";
        String k = "";
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'){
                k = String.valueOf(s.charAt(i));
                rez += '_' + k.toLowerCase();
            }
            else
                rez += s.charAt(i);
        }
        return rez;
    }
    public static String overTime(double[] a) {
        double n = 0;
        if (a[1] > a[0]){
            if (a[1] <= 17 && a[0] >= 9)
                n = (a[1] - a[0]) * a[2];
            else if (a[1] > 17 && a[0] < 17)
                n = ((17 - a[0]) * a[2]) + ((a[1] - 17) * a[2] * a[3]);
            else if (a[0] >= 17 && a[1] <= 24)
                n = (a[1] - a[0]) * a[2] * a[3];
    }
        else if(a[1] < a[0]) {
            if(a[0] >= 17 && a[1] <= 9)
                n = (24 - a[0]) * a[2] * a[3] + a[1]*a[2]*a[3];
            else if(a[0] >= 17 && a[1] >= 9)
                n = (24 - a[0]) * a[2] * a[3] + 9*a[2]*a[3] + (a[1] - 9) * a[2];
            else if(a[0] < 17 && a[1] <= 9)
                n = (17 - a[0]) * a[2] + ((24-17) * a[2] * a[3]) + a[1]*a[2]*a[3];
            else if(a[0] < 17 && a[1] > 9)
                n = (17 - a[0]) * a[2] + ((24-17) * a[2] * a[3]) + 9*a[2]*a[3] + (a[1] - 9) * a[2];
        }
        String result = String.format("%.2f", n);
        return "$" + result;
    }

    public static String BMI(String ves, String rost) {
        String[] s1 = ves.split(" ");
        double i1 = Double.valueOf(s1[0]);

        String[] s2 = rost.split(" ");
        double i2 = Double.valueOf(s2[0]);

        if(s1[1].equals("pounds"))
            i1 = i1/2.2046;
        if(s2[1].equals("inches"))
            i2 = i2/39.370;

        double bmi = i1/(i2*i2);
        String BMI = String.format("%.1f", bmi);

        if(bmi < 18.5)
            return BMI + " Underweight";
        else if(bmi >= 18.5 && bmi <= 24.9)
            return BMI + " Normal weight";
        else
            return BMI + " Overweight";
    }

    public static int bugger(int n) {
        if(n < 10)
            return 0;

        else {
            while(n >= 10)
                n = multi(n);
        }

        return n;
    }

    public static int multi(int n) {
        int mul = n % 10;
        n = n / 10;

        while (n > 0 && mul > 0) {
            mul *= n % 10;
            n = n / 10;
        }
        return mul;
    }

    //abbccc -> ab*2c*3
    public static String toStarShorthand(String s) {
        String s_new = "";
        for (int i = 0; i < s.length(); i++) {
            int n = 1;
            for (int k = i + 1; k < s.length(); k++) {
                if (s.charAt(i) == s.charAt(k)) {
                    n++;
                    i++;
                }
            }
            if (n != 1)
                s_new += s.charAt(i) + "*" + String.valueOf(n);
            else
                s_new += s.charAt(i);
        }
        return s_new;
    }

    public static boolean doesRhyme(String s1, String s2) {
        return getLastWordVowels(s1).equals(getLastWordVowels(s2));
    }

    public static String getLastWordVowels(String s) {
        Set<Character> vowels = new HashSet<Character>(Arrays.asList(
                'A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'Y', 'y'));
        String last_word = "";
        String[] a = s.split(" ");
        int h = a.length-1;
        last_word = a[h];

        String word_vowels = "";
        for(int i = 0; i < last_word.length(); i++){
            if(vowels.contains(last_word.charAt(i)))
                word_vowels += last_word.charAt(i);
        }
        return word_vowels.toLowerCase();
    }

    public static boolean trouble(int num1, int num2) {
        ArrayList<Integer> digits1 = getDigitFrequences(num1);
        ArrayList<Integer> digits2 = getDigitFrequences(num2);
        for (int i = 0; i < 10; i++) {
            if (digits1.get(i) == 3 && digits2.get(i) == 2)
                return true;
        }
        return false;
    }

    protected static ArrayList<Integer> getDigitFrequences(int n) {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        while (n > 0) {
            int digit = n % 10;
            arr.set(digit, arr.get(digit) + 1);
            n = n / 10;
        }
        return arr;
    }

    public static int countUniqueBooks(String s, char c) {
        boolean flag = false;
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                flag = !flag;
            }
            else {
                if (flag) set.add(s.charAt(i));
            }
        }
            return set.size();
    }
}
