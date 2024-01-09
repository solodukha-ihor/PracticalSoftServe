package jom.com.softserve.s5.task1;
import java.util.Arrays;
import java.util.function.Predicate;

public class MyUtils {
    public static int getCount(int[] array, Predicate<Integer> condition ) {
        int count = 0;
        for(int number : array) {
            if(condition.test(number)) {
                count++;
            }
        }
        return count;
    	// Write your code here
    }
}