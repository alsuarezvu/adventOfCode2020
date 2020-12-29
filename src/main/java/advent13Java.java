import scala.Int;
import scala.concurrent.impl.FutureConvertersImpl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/13
 */
public class advent13Java {
    public static void main(String[] args) {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent13.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            List<String> lines = new ArrayList<>();

            //read the the earliest timestamp
            final int earliestTimestampToLeave = Integer.parseInt(br.readLine().trim());

            //read the bus ids and service
            final String busIdsInService = br.readLine().trim();

            fstream.close();

            //change preamble to 5 for testing with advent9Test.txt
            System.out.println("Part 1 answer is: " + challenge1(earliestTimestampToLeave, busIdsInService));
            System.out.println("Part 2 answer is: " + challenge2(busIdsInService));
            // Close the input stream
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    private static int challenge1(final int earliestTimestampToLeave, final String busIdsInService) {

        int earliestBusToTake = 0;
        int timeDiffToEarliestBusToTake = Int.MaxValue();

        for (String busId : busIdsInService.split(",")) {

            if (!busId.equals("x")) {
                int busNum = Integer.parseInt(busId);

                int prevDepartingTime = (int) (Math.floor(((double) earliestTimestampToLeave / busNum))) * busNum;
                int nextDepartingTime = prevDepartingTime + busNum;

                int timeDiff = nextDepartingTime - earliestTimestampToLeave;

                if (timeDiff < timeDiffToEarliestBusToTake) {
                    timeDiffToEarliestBusToTake = timeDiff;
                    earliestBusToTake = busNum;
                }
            }
        }

        System.out.println("earliestBusToTake: " + earliestBusToTake);
        System.out.println("timeDiffToEarliestBusToTake: " + timeDiffToEarliestBusToTake);
        return earliestBusToTake * timeDiffToEarliestBusToTake;
    }

    private static long challenge2(final String busIdsInService) {
        //store bus ids and their offsets in a map
        LinkedHashMap<Integer, Integer> busOffSetMap = new LinkedHashMap<>();

        String[] strings = busIdsInService.split(",");
        for (int i=0; i<strings.length; i++) {
            if (!strings[i].equals("x")) {
                int busId = Integer.parseInt(strings[i]);
                busOffSetMap.put(busId, i);
            }
        }

        for(Map.Entry mapElement : busOffSetMap.entrySet()) {
            int key = (int) mapElement.getKey();
            int value = (int) mapElement.getValue();

            System.out.println("key: "+key+" , value: "+value);

            //for each bus id, subtract the offset from the id and store back into the map
            if(value == 0) {
                value = 0;
            }
            else {
                value = key - value;
            }

            busOffSetMap.put(key, value);

        }

        int sumOfFound = 0;
        long timestamp = 800000000000000L;

        while(sumOfFound < busOffSetMap.size()) {
            timestamp++;
            sumOfFound = 0;
            //System.out.println(timestamp);
            for(Map.Entry mapElement : busOffSetMap.entrySet()) {
                int key = (int) mapElement.getKey();
                int value = (int) mapElement.getValue();

                if(timestamp % key == value) {
                    sumOfFound++;
                }
                else {
                    break;
                }
            }


        }

        return timestamp;
    }
}
