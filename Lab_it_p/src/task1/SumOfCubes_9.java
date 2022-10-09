package task1;

public class SumOfCubes_9 {
    public static void main(String[] args) {
        int[] myArray = {1, 5, 9};
        System.out.println(sumOfCubes(myArray));
    }

    public static int sumOfCubes(int[] a){
        int sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i], 3);
        }
        return sum;
    }
}
