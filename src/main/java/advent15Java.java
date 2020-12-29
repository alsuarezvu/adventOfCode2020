import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/15
 */
public class advent15Java {

    public static void main(String[] args) {


        int[] numStrings = {0,3,6};

        //change preamble to 5 for testing with advent9Test.txt
        System.out.println("Part 1 answer is: " + challenge1(numStrings));

    }

    private static int challenge1(final int[] numStrings) {
        HashMap<Integer, Integer> numSet = new HashMap<>();
        HashSet<Integer> spokenSet = new HashSet<>();

        for (int i = 0; i < numStrings.length-1; i++) {
            numSet.put(numStrings[i], i+1);
        }

        int count = numSet.size() + 2;
        int numberToAnalyze = numStrings[numStrings.length - 1];

        while(count < 2020) {
            Integer number = numSet.get(numberToAnalyze);
            if(number == null) {
                numSet.put(numberToAnalyze, count);
                numberToAnalyze = 0;
                System.out.println(numberToAnalyze);
            }
            else {
                //it had been spoken before so find the turn it was spoken before
                numberToAnalyze = (count - 1) - numSet.get(numberToAnalyze);
                System.out.println(numberToAnalyze);
            }
            count++;
        }
        return numberToAnalyze;
    }

}
