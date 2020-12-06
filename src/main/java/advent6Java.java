import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class advent6Java {

    public static void main (String[] args ) {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent6.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            int sum = getSumOfAllYesAnswers(br);
            // Close the input stream
            fstream.close();
            System.out.println("Part 2 answer is: "+sum);
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    //Part 1
    private static int getSumUniqueAnswers(final BufferedReader br) throws IOException {
        String strLine;
        // Read File Line By Line
        int sum = 0;
        Set<Character> answerHashSet = new HashSet<>();
        while ((strLine = br.readLine()) != null) {
           if(strLine.isEmpty()) {
               sum = sum + answerHashSet.size();
               answerHashSet.clear();
           }
           else {
               for(char c : strLine.trim().toCharArray()) {
                   answerHashSet.add(c);
               }
           }
        }
        if(answerHashSet.size() > 0) {
            sum = sum + answerHashSet.size();
            answerHashSet.clear();
        }
        return sum;
    }

    //Part 2
    private static int getSumOfAllYesAnswers(final BufferedReader br) throws IOException {
        String strLine;
        // Read File Line By Line
        int sum = 0;
        Map<Character, Integer> answerHashMap = new HashMap<>();
        int numberOfGroupMembers = 0;
        while ((strLine = br.readLine()) != null) {
            if(strLine.isEmpty()) {
                sum = getSumOfAllYesForGroup(sum, answerHashMap, numberOfGroupMembers);
                //reset for new group
                answerHashMap.clear();
                numberOfGroupMembers = 0;
            }
            else {
                //store contents in a hashset
                for(char c : strLine.trim().toCharArray()) {
                    int value = 0;
                    if(answerHashMap.containsKey(c)) {
                        value = answerHashMap.get(c);
                    }
                    answerHashMap.put(c, value + 1);
                }
                numberOfGroupMembers++;
            }
        }
        if(answerHashMap.size() > 0) {
            sum = getSumOfAllYesForGroup(sum, answerHashMap, numberOfGroupMembers);
        }
        return sum;
    }

    private static int getSumOfAllYesForGroup(int sum, Map<Character, Integer> answerHashMap, int numberOfGroupMembers) {
        Iterator it = answerHashMap.entrySet().iterator();
        while (it.hasNext()) {
            //check whether value == number of people in group
            Map.Entry<Character, Integer> next = (Map.Entry<Character, Integer>) it.next();
            if(numberOfGroupMembers == next.getValue()) {
                sum = sum + 1;
            }
        }
        return sum;
    }
}
