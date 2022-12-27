import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task_6 {
    public static void main(String[] args) {

        System.out.println(bell(3));
        System.out.println(translateWord("oaken"));
        System.out.println(translateSentence("I like to eat honey waffles."));
        System.out.println(validColor("rgb(0,0,0,1)"));
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2&b=3&a=3&c=1", 'b', 'c'));
        System.out.println(Arrays.toString(getHashTags("Science Visualizing")));
        System.out.println(Ulam(5));
        System.out.println(longestNonRepeatingSubstring("abcda"));
        System.out.println(convertToRoman(3872));
        System.out.println((formula("6 * 5 = 30 = 150 / 52")));
        System.out.println(palindromeDescendant(23336014));


    }

    public static int bell(int n) {
        int[][] bell = new int[n + 1][n + 1];
        bell[0][0] = 1;

        for (int i = 1; i <= n; i++) {
            bell[i][0] = bell[i - 1][i - 1];

            for (int j = 1; j <= i; j++)
                bell[i][j] = bell[i - 1][j - 1] + bell[i][j - 1];
        }

        return bell[n][0];
    }
    public static String translateWord(String word) {
        String ISAlphabetic = "aeyuio";
        StringBuilder pig = new StringBuilder(word);
        StringBuilder pig2 = new StringBuilder();
        pig.reverse();
        boolean flag = true;
        for (char c : word.toCharArray()) {
            if (!ISAlphabetic.contains(String.valueOf(c))) {
                pig2.append(c);
                pig.deleteCharAt(pig.length() - 1);
                flag = false;
            } else {
                pig.reverse();
                break;
            }
        }
        if (flag) return pig.append("yay").toString();
        else return pig.append(pig2).append("ay").toString();
    }

    public static String translateSentence(String message) {
        String[] splitMessage = message.split("\\W{1,2}");
        StringBuilder PIG_MESSAGE = new StringBuilder();
        String first = translateWord(splitMessage[0].toLowerCase());
        List<String> translateMessage = new ArrayList<>(Arrays.asList(splitMessage));
        PIG_MESSAGE.append(first.substring(0, 1).toUpperCase()).append(first.substring(1)).append(" ");
        translateMessage.remove(0);
        for (String word : translateMessage) {
            PIG_MESSAGE.append(translateWord(word)).append(" ");
        }
        PIG_MESSAGE.deleteCharAt(PIG_MESSAGE.length() - 1);
        return PIG_MESSAGE.append(message.charAt(message.length() - 1)).toString();
    }

    public static boolean validColor(String color) {
        Pattern pattern = Pattern.compile("(?<=\\().+(?=\\))");
        Matcher matcher = pattern.matcher(color);
        String params = "";

        while (matcher.find()) {
            params = color.substring(matcher.start(), matcher.end());
        }
        String[] paramsOfColor = params.split(",");

        if (color.contains("rgba")) {
            if (paramsOfColor.length != 4) return false;
            for (int i = 0; i < 3; i++) {
                if (Objects.equals(paramsOfColor[i], "")) return false;
                int param = Integer.parseInt(paramsOfColor[i]);
                if (param > 255 || param < 0) return false;
            }
            double param = Double.parseDouble(paramsOfColor[3]);
            return !(param < 0) && !(param > 1);
        } else if (color.contains("rgb")) {
            if (paramsOfColor.length != 3) return false;
            for (int i = 0; i < 3; i++) {
                if (Objects.equals(paramsOfColor[i], "")) return false;
                int param = Integer.parseInt(paramsOfColor[i]);
                if (param > 255 || param < 0) return false;
            }
        }
        return true;
    }

    public static String stripUrlParams(String url, Character... deleteParams) {
        String[] splitURL = url.split("\\?");
        final String staticURL = splitURL[0];
        StringBuilder editURL = new StringBuilder(staticURL);
        if (splitURL.length == 2) {
            String[] splitParamsURL = splitURL[1].split("&");
            List<String> paramsURL = new ArrayList<>(Arrays.asList(splitParamsURL));
            HashMap<Character, Integer> uniqueParams = new HashMap<>();
            for (String s : paramsURL) {
                String[] charsOfParams = s.split("=");
                if (deleteParams != null &&
                        !Arrays.toString(deleteParams).contains(String.valueOf(charsOfParams[0].charAt(0)))) {
                    uniqueParams.put(charsOfParams[0].charAt(0), Integer.parseInt(charsOfParams[1]));
                } else if (deleteParams == null)
                    uniqueParams.put(charsOfParams[0].charAt(0), Integer.parseInt(charsOfParams[1]));
            }

            editURL.append("?");

            for (Map.Entry<Character, Integer> entry : uniqueParams.entrySet()) {
                editURL.append(entry).append("&");
            }

            editURL.deleteCharAt(editURL.length() - 1);
        }
        return editURL.toString();
    }

    public static Object[] getHashTags(String message) {
        String[] splitMessage = message.split("\\W{1,2}");
        List<String> result = new ArrayList<>();
        List<String> checkMessage = new ArrayList<>(Arrays.asList(splitMessage));

        int maxLen = 0, indexing = 0;
        String bigWord = "";
        if (checkMessage.size() > 2) indexing = 3;
        else indexing = checkMessage.size();

        for (int i = 0; i < indexing; i++) {
            for (String word : checkMessage) {
                if (word.length() > maxLen) {
                    maxLen = word.length();
                    bigWord = word;
                }
            }
            result.add("#" + bigWord.toLowerCase(Locale.ROOT));
            checkMessage.remove(bigWord);
            bigWord = "";
            maxLen = 0;
        }
        return new List[]{result};
    }

    public static int Ulam(int n) { //последовательность Улама
        ArrayList<Integer> ulamSequence = new ArrayList<>();
        ulamSequence.add(1);
        ulamSequence.add(2);
        int checkedNum;
        int j;
        for (checkedNum = 3, j = 2; j < n; checkedNum++) {
            int checkCount = 0;
            for (int firstVal = 0; firstVal < ulamSequence.size() - 1; firstVal++) {
                for (int secondVal = firstVal + 1; secondVal < ulamSequence.size(); secondVal++) {
                    if (ulamSequence.get(firstVal) + ulamSequence.get(secondVal) == checkedNum)
                        checkCount++;
                    if (checkCount > 1)
                        break;
                }
                if (checkCount > 1)
                    break;
            }
            if (checkCount == 1) {
                ulamSequence.add(checkedNum);
                j++;
            }
        }
        return checkedNum - 1;
    }

    public static String longestNonRepeatingSubstring(String message) {
        int maxCount = 0;
        int count = 0;
        StringBuilder unique = new StringBuilder();
        message = message + " ";
        for (int i = 0; i < message.length() - 1; i++) {
            String uniqueChar = String.valueOf(message.charAt(i));
            if (message.charAt(i) != message.charAt(i + 1) && !unique.toString().contains(uniqueChar)) {
                count++;
                unique.append(message.charAt(i));
            } else if (maxCount < count) {
                maxCount = count;
                count = 0;
            }
        }
        if (unique.toString().isBlank()) return String.valueOf(message.charAt(0));
        else return unique.toString();
    }


    public static String convertToRoman(int number) {
        var romanInt = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        var intKeys = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder romanNum = new StringBuilder();

        for (int i = 0; i < romanInt.length; i++) {
            while (number >= intKeys[i]) {
                var countRep = number / intKeys[i];
                number = number % intKeys[i];
                romanNum.append(romanInt[i].repeat(countRep));
            }
        }

        return romanNum.toString();

    }

    public static boolean formula(String formula) {
        String[] equality = formula.split("=");

        for (int i = 0; i < equality.length - 1; i++) {
            if (calculate(equality[i]) != (calculate(equality[i + 1]))) {
                int calc = calculate(equality[i]);
                int calc2 = calculate(equality[i + 1]);
                return false;
            }
        }
        return true;
    }

    public static int calculate(String formula) {
        formula = formula.strip();
        int len;
        String[] operations = formula.split(" ");
        if (operations.length == 3) len = 2;
        else len = 1;
        return switch (operations[len - 1]) {
            case "-" -> (Integer.parseInt(operations[0]) - Integer.parseInt((operations[2])));
            case "+" -> Integer.parseInt(operations[0]) + Integer.parseInt((operations[2]));
            case "*" -> Integer.parseInt(operations[0]) * Integer.parseInt((operations[2]));
            case "/" -> Integer.parseInt(operations[0]) / Integer.parseInt((operations[2]));
            default -> Integer.parseInt(formula);
        };
    }

    public static boolean palindromeDescendant(int number) {
        StringBuilder palindrome = new StringBuilder(number);
        while (!palindrome.toString().equals(palindrome.reverse().toString())) {
            StringBuilder newPalindrome = new StringBuilder();
            int num;
            for (int i = 0; i < palindrome.length() - 1; i += 2) {
                int val1 = Integer.parseInt(String.valueOf(palindrome.charAt(i)));
                int val2 = Integer.parseInt(String.valueOf(palindrome.charAt(i++)));
                num = val1 + val2;
                newPalindrome.append(num);
            }
            palindrome = new StringBuilder(newPalindrome);
        }
        return palindrome.toString().equals(palindrome.reverse().toString());
    }


}