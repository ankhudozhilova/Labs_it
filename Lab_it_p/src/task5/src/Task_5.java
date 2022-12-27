import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Task_5 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(encrypt("Sunshine")));
        System.out.println(decrypt(new int[]{72, 33, -73, 84, -12, -3, 13, -13, -68}));
        System.out.println(canMove("Queen", "C4", "D6"));
        System.out.println(canComplete("butl", "beautiful"));
        System.out.println(sumDigProd(1, 2, 3, 4, 5, 6));
        System.out.println(Arrays.toString(sameVowelGroup(new String[]{"toe", "ocelot", "maniac"})));
        System.out.println(validateCard("1234567890123452"));
        System.out.println(numToEng(909));
        System.out.println(getSha256Hash("password123"));
        System.out.println(correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println(hexLattice(19));
    }

    public static int[] encrypt(String s) {
        int symb = 0;
        int[] arr = new int[s.length()];
        for (int i=0; i < s.length(); i++) {
            arr[i] = (int)s.charAt(i) - symb;
            symb = (int)s.charAt(i);
        }
        return arr;
    }
    public static String decrypt(int[] s) {
        char ascii = 0;
        StringBuilder symb = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            symb.append((char)(ascii + s[i]));
            ascii = symb.charAt(i);
        }
        return symb.toString();
    }
    public static boolean canMove(String figure, String s1, String s2) {
        if (figure.equals("Pawn")) { //пешка
            if (s1.charAt(0) == s2.charAt(0)) {
                if (s1.charAt(1) == '2' && s2.charAt(1) == '4') return true;
                if (s1.charAt(1) == '5' && s2.charAt(1) == '7') return true;
                if (Math.abs((int)s1.charAt(1) - (int)s2.charAt(1)) == 1) return true;
            }
        }

        if (figure.equals("Rook")) { //ладья
            if (s1.charAt(0) == s2.charAt(0) || s1.charAt(1) == s2.charAt(1))
                return true;
        }

        if (figure.equals("Horse")) { //конь
            if (Math.abs((int)s1.charAt(0) - (int)s2.charAt(0)) == 2
                    && Math.abs((int)s1.charAt(1) - (int)s2.charAt(1)) == 1)
                return true;
            if (Math.abs((int)s1.charAt(0) - (int)s2.charAt(0)) == 1
                    && Math.abs((int)s1.charAt(1) - (int)s2.charAt(1)) == 2)
                return true;
        }

        if (figure.equals("Bishop")) { //слон
            if (Math.abs((int)s1.charAt(0) - (int)s2.charAt(0)) == Math.abs((int)s1.charAt(1) - (int)s2.charAt(1)))
                return true;
        }

        if (figure.equals("Queen")) { //ферзь
            if (Math.abs((int)s1.charAt(0) - (int)s2.charAt(0)) == Math.abs((int)s1.charAt(1) - (int)s2.charAt(1)))
                return true;
            if (s1.charAt(0) == s2.charAt(0) || s1.charAt(1) == s2.charAt(1))
                return true;
        }

        if (figure.equals("King")) { //король
            if (Math.abs((int)s1.charAt(0) - (int)s2.charAt(0)) <= 1
                    && Math.abs((int)s1.charAt(1) - (int)s2.charAt(1)) <= 1)
                return true;
        }
        return false;
    }

    public static boolean canComplete(String s1, String s2) {
        int n = 0;
        for (int i=0; i<s2.length(); i++) {
            if (s1.charAt(n) == s2.charAt(i)) n++;
        }
        if (n == s1.length()) return true;
        else return false;
    }
    public static int sumDigProd(int... arr) {
        int rez = 0;
        for (int i : arr) {
            rez += i;
        }
        while (rez >= 10) {
            rez = multi(rez);
        }
        return (rez);
    }
    public static int multi(int a) {
        int n = 1;
        while (a > 0) {
            n *= a%10;
            a /= 10;
        }
        return n;
    }
    public static String[] sameVowelGroup(String[] a) {
        Set<Character> vowels = new HashSet<Character>(Arrays.asList(
                'A', 'a', 'E', 'e', 'I', 'i', 'O', 'o', 'U', 'u', 'Y', 'y'));
        String first_word = a[0];
        HashSet<Character> a1 = new HashSet<>();
        ArrayList<Character> a2 = new ArrayList<>();
        ArrayList<String> word_vowels = new ArrayList<>();

        for(int i = 0; i < first_word.length(); i++){
            if(vowels.contains(first_word.charAt(i)))
                a1.add(first_word.charAt(i));
        }
        word_vowels.add(a[0]);

        for (int i = 1; i < a.length; i++) {
            for(int j = 0; j < a[i].length();j++){
                if(a1.contains(a[i].charAt(j)))
                    a2.add(a[i].charAt(j));
            }
            if(a2.containsAll(a1))
                word_vowels.add(a[i]);
            a2 = new ArrayList<>();
        }
        return word_vowels.toArray(new String[]{});
    }
    public static boolean validateCard(String numCard) {
        StringBuilder checkCard = new StringBuilder(numCard);
        int sum = 0;
        if (checkCard.length() >= 14 && checkCard.length() <= 19) {
            char checkPoint = checkCard.charAt(checkCard.length() - 1);
            checkCard.deleteCharAt(checkCard.length() - 1);
            checkCard.reverse();
            String Card = checkCard.toString();
            for (int i = 0; i < Card.length(); i++) {
                int num = Integer.parseInt(String.valueOf(Card.charAt(i)));
                if ((i + 1) % 2 != 0) {
                    num *= 2;
                    if (num / 10 == 0) {
                        sum += num;
                    } else {
                        sum += num / 10;
                        sum += num % 10;
                    }
                } else
                    sum += num;

            }
            StringBuilder last = new StringBuilder(String.valueOf(sum));
            int last_int = 10 - Integer.parseInt(String.valueOf(last.charAt(last.length() - 1)));
            return Character.forDigit(last_int, 10) == checkPoint;
        } else return false;

    }
    public static String numToEng(int n) {
        String[] ones = {"zero","one","two","three","four","five","six","seven",
                "eight","nine"};
        String[] tens = {"ten","eleven","twelve","thirteen","fourteen","fifteen",
                "sixteen","seventeen","eighteen","nineteen"};
        String[] ty = {"","twenty","thirty","forty","fifty","sixty","seventy","eighty",
                "ninety"};
        String strNum = Integer.toString(n);

        switch (strNum.length()) {
            case 1: //0-9
                return ones[n];
            case 2: //10-99
                if (n >= 10 && n <= 19) return tens[n - 10];
                else return ty[n/10 - 1] + " " + ones[n%10];
            case 3: //100-999
                StringBuilder res = new StringBuilder(ones[n/100] + " hundred ");
                strNum = strNum.substring(1);
                if (strNum.charAt(0) == '0') {
                    strNum = strNum.substring(1);
                    res.append(ones[Integer.parseInt(strNum)%10]);
                }
                else {
                    if (Integer.parseInt(strNum) >= 10 && Integer.parseInt(strNum) <= 19) res.append(tens[Integer.parseInt(strNum) - 10]);
                    else res.append(ty[Integer.parseInt(strNum)/10 - 1] + " " + ones[Integer.parseInt(strNum)%10]);
                }
                return res.toString();
            default:
                break;
        }
        return "";
    }
    public static String getSha256Hash(String str) {
        StringBuilder res = new StringBuilder();
        try {
            //шифр байтов в строку
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(str.getBytes());
            //перевод их в хеш
            for (int i = 0; i < hash.length; i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) res.append(0);
                res.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return res.toString();
    }
    public static String correctTitle(String s) {
        String low = s.toLowerCase();
        String[] words = low.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word: words) {
            if (!word.equals("of") && !word.equals("in") && !word.equals("and") && !word.equals("the"))
                result.append(word.substring(0,1).toUpperCase() + word.substring(1) + " ");
        }
        return result.toString();
    }
    public static String hexLattice(int n) {
        int i = 0; //центр
        boolean isHexNum = false;
        while (3*i*(i+1)+1 <= n){
            if (3*i*(i+1)+1 == n) isHexNum = true;
            i++;
        }
        StringBuilder res = new StringBuilder();
        if (isHexNum){
            int l = i; //символы
            int m = i; //пробелы
            for (int j = 0; j < 2*i-1; j++){
                res.append("\n");
                StringBuilder space = new StringBuilder("");
                for (int k = 1; k < m; k++){
                    space.append(" ");
                }
                res.append(space);
                for (int k = 0; k < l; k++){
                    res.append(" o");
                }
                res.append(space + " ");
                l += (j < i-1) ? 1 : -1;
                m += (j < i-1) ? -1 : 1;
            }
            return res.toString().replaceFirst("\n", "");
        } else return "Invalid";
    }
}