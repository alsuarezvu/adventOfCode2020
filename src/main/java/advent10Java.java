import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/10
 */
public class advent10Java {

    public static void main(String[] args) {
        try {
            final FileInputStream fstream = new FileInputStream("src/main/resources/advent10.txt");
            final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            final List<Integer> lines = new ArrayList<>();
            String strLine;
            lines.add(0);
            while ((strLine = br.readLine()) != null) {
                lines.add(Integer.parseInt(strLine.trim()));
            }

            System.out.println("Part 1 answer is: " + challenge1(lines));
            System.out.println("Part 2 answer is: " + challenge2(lines));
            // Close the input stream
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static int challenge1(final List<Integer> lines) {

        Collections.sort(lines);

        int max = lines.get(lines.size() - 1) + 3;
        int initialJoltage = 0;
        int diffCountOne = 0;
        int diffCountThree = 0;

        for (int line : lines) {
            if (inRange(initialJoltage, 3, line)) {
                int diff = line - initialJoltage;

                if (diff == 1) {
                    diffCountOne++;
                } else if (diff == 3) {
                    diffCountThree++;
                }
                initialJoltage = line;
            } else {
                System.out.println("number is not in range: " + line);
                System.out.println("Here is the range: " + initialJoltage + "-" + line);
            }
        }

        //diff against the max
        int diff = max - initialJoltage;

        if (diff == 1) {
            diffCountOne++;
        } else if (diff == 3) {
            diffCountThree++;
        }

        //System.out.println("diffCountOne: "+diffCountOne);
        //System.out.println("diffCountThree: "+diffCountThree);

        return diffCountOne * diffCountThree;

    }

    /**
     * Solution referenced from: https://schnouki.net/post/2020/advent-of-code-2020-day-10/
     * Calculate the diffs between numbers and make note of the diffs of 1
     * For all diffs of 1, check the number of consecutive 1s
     * Depending on the number of consecutive 1s, multiply the different wants to arrange it with all
     * consecutive groups of 1s
     *
     * @param adapters the list of adapters
     * @return the number of different options to build the sequence
     */
    private static long challenge2(final List<Integer> adapters) {

        Collections.sort(adapters);

        int max = adapters.get(adapters.size() - 1) + 3;

        //add the max to the end
        adapters.add(adapters.size(), max);

        //System.out.println(adapters);

        //map to store one diffs and line position
        final List<Integer> oneDiffPositions = new ArrayList<>();
        for (int i = 0; i < adapters.size() - 1; i++) {
            int num1 = adapters.get(i);
            int num2 = adapters.get(i + 1);

            //calculate the diffs and add to a list
            int diff = num2 - num1;

            if (diff == 1) {
                oneDiffPositions.add(i);
            }
        }

        //System.out.println("oneDiffPositions"+oneDiffPositions);

        //number of consecutive 1s multiplier
        //{2: 2, 3: 4, 4: 7, 5: 13}
        final Map<Integer, Integer> consecutiveOnesMultiplier = new HashMap<>();
        consecutiveOnesMultiplier.put(1, 1);
        consecutiveOnesMultiplier.put(2, 2);
        consecutiveOnesMultiplier.put(3, 4);
        consecutiveOnesMultiplier.put(4, 7);
        consecutiveOnesMultiplier.put(5, 13);

        int count = 1;
        long numberOfPaths = 1;
        for (int i = 0; i < oneDiffPositions.size(); i++) {

            if (i == oneDiffPositions.size() - 1) {
                final Integer multiplier = consecutiveOnesMultiplier.get(count);
                //System.out.println("multiplier: "+multiplier);
                if (multiplier != null) {
                    numberOfPaths *= multiplier;
                }

                return numberOfPaths;
            }

            int num1 = oneDiffPositions.get(i);
            int num2 = oneDiffPositions.get(i + 1);

            //System.out.println("num1: "+num1);
            //System.out.println("num2: "+num2);


            if (num2 - num1 == 1) {
                count++;
            } else if (num2 - num1 > 1) {
                final Integer multiplier = consecutiveOnesMultiplier.get(count);
                //System.out.println("multiplier: "+multiplier);
                if (multiplier != null) {
                    numberOfPaths *= multiplier;
                }
                count = 1;
            } else if (num2 - num1 < 1) {
                System.out.println("Invalid case! num2:" + num2 + " num1:" + num1);
            }
        }
        return numberOfPaths;
    }

    private static boolean inRange(int start, int offset, int joltage) {
        if (joltage <= start + offset) {
            return true;
        }
        return false;
    }
}
