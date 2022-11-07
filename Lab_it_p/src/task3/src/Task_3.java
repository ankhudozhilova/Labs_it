public class Task_3 {
    public static void main(String[] args) {
        System.out.println(isValidHexCode("#CD5C58C"));
    }

    public static boolean isValidHexCode(String s) {
        int flag = 0;
        if(s.charAt(0) == '#' && s.length() == 7) {
            for(int i = 1; i < s.length(); i ++) {
                if((s.charAt(i) >= '0' && s.charAt(i) <= '9') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'F') || (s.charAt(i) >= 'a' && s.charAt(i) <= 'f'))
                    flag = 1;
            }
        }
        return flag == 1;
    }
}
