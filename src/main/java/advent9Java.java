import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/9
 */
public class advent9Java {

    public static void main(String[] args) {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent9.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            List<Long> lines = new ArrayList<>();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                lines.add(Long.parseLong(strLine.trim()));
            }

            //change preamble to 5 for testing with advent9Test.txt
            long invalidSum = challenge1(lines, 25);
            System.out.println("Part 1 answer is: " + invalidSum);
            System.out.println("Part 2 answer is: " + challenge2(lines, invalidSum));
            // Close the input stream
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static long challenge1(final List<Long> lines, final int preaamble) {
        int ptr = 0;
        LinkedHashSet<Long> numbers = new LinkedHashSet<>();
        long invalidSum = 0;

        while (ptr + preaamble <= lines.size() - 1) {
            //store numbers to analyze in a hashset
            for (int i = ptr; i < ptr + preaamble; i++) {
                numbers.add(lines.get(i));
            }

            //get the sum we are working with
            long sum = lines.get(ptr + preaamble);

            boolean isValid = false;

            for (long number : numbers) {
                long diff = sum - number;
                if (numbers.contains(diff) && diff != sum) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                //System.out.println("not valid sum: "+sum);
                invalidSum = sum;
                break;
            }
            //System.out.println(numbers);
            //System.out.println("sum: "+sum);


            numbers.clear();
            ptr++;
        }

        return invalidSum;
    }


    private static long challenge2(final List<Long> lines, final long sumToFind) {
        int headPtr = 0;
        ArrayList<Long> numbersAddedUp = new ArrayList<>();

        while (headPtr <= lines.size() - 1) {
            int ptr = headPtr;
            long sum = 0;
            numbersAddedUp.clear();
            boolean sequenceFound = false;
            while (sum < sumToFind && ptr <= lines.size() - 1) {
                Long numToAdd = lines.get(ptr);
                sum = sum + numToAdd;
                numbersAddedUp.add(numToAdd);
                if (sum == sumToFind && numbersAddedUp.size() >= 2) {
                    sequenceFound = true;
                    System.out.println("Found a sequence that adds up to " + sumToFind);
                    break;
                }
                ptr++;
            }

            if (sequenceFound) {
                break;
            }
            headPtr++;
        }

        System.out.println("The sequence is: ");
        for (long number : numbersAddedUp) {
            System.out.println(number);
        }

        //find the min and max and add it together
        Collections.sort(numbersAddedUp);

        return numbersAddedUp.get(0) + numbersAddedUp.get(numbersAddedUp.size() - 1);
    }
}
