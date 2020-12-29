import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/10
 */
public class advent10Java {

    public static void main(String[] args) {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent10Test.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            List<Integer> lines = new ArrayList<>();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                lines.add(Integer.parseInt(strLine.trim()));
            }

            //change preamble to 5 for testing with advent9Test.txt
            System.out.println("Part 1 answer is: " + challenge1(lines));
            System.out.println("Part 2 answer is: " + challenge2(lines));
            // Close the input stream
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static long challenge1(final List<Integer> lines) {

        Collections.sort(lines);

        int max = lines.get(lines.size() - 1) + 3;
        int initialJoltage = 0;
        int diffCountOne = 0;
        int diffCountThree = 0;

        for(int line: lines) {
            if(inRange(initialJoltage, 3, line)) {
                int diff = line - initialJoltage;

                if(diff == 1) {
                    diffCountOne++;
                }
                else if (diff == 3) {
                    diffCountThree++;
                }
                initialJoltage = line;
            }
            else {
                System.out.println("number is not in range: "+ line);
                System.out.println("Here is the range: "+initialJoltage+"-"+ line);
            }
        }

        //diff against the max
        int diff = max - initialJoltage;

        if(diff == 1) {
            diffCountOne++;
        }
        else if (diff == 3) {
            diffCountThree++;
        }

        System.out.println("diffCountOne: "+diffCountOne);
        System.out.println("diffCountThree: "+diffCountThree);

        return diffCountOne * diffCountThree;

    }

    private static long challenge2(final List<Integer> lines) {

        Collections.sort(lines);

        int max = lines.get(lines.size() - 1) + 3;
        int initialJoltage = 0;
        int diffCountOne = 0;
        int diffCountThree = 0;

        HashSet<Integer> numOfOneDiffs = new HashSet<>();

        for(int i=0; i<lines.size()-1; i++) {
            int line = lines.get(i);
            if(inRange(initialJoltage, 3, line)) {
                int diff = line - initialJoltage;

                System.out.println(diff);
//                if(diff == 1) {
//                    diffCountOne++;
//                    numOfOneDiffs.add(line);
//
//                    if(i !=lines.size() -1) {
//                        int index = i+1;
//                        if(lines.get(index) - line != 3){
//                            numOfOneDiffs.add(line);
//                        }
//                    }
//                }

                initialJoltage = line;
            }
            else {
                System.out.println("number is not in range: "+ line);
                System.out.println("Here is the range: "+initialJoltage+"-"+ line);
            }
        }

        //diff against the max
        int diff = max - initialJoltage;

        if(diff == 1) {
            diffCountOne++;
            numOfOneDiffs.add(initialJoltage);
        }
        else if (diff == 3) {
            diffCountThree++;
        }

        for(Integer number: numOfOneDiffs){
            System.out.println(number);
        }


        return diffCountOne * diffCountThree;

    }

    private static boolean inRange(int start, int offset, int joltage) {
        if(joltage <=start+offset) {
            return true;
        }
        return false;
    }


}
