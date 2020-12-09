import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/5
 */
public class advent5Java {

    public static void main (String[] args ) throws IOException {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent5.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            // Read File Line By Line
            int maxBoardingId = 0;
            List<Integer> boardingIds = new ArrayList<Integer>();
            while ((strLine = br.readLine()) != null) {
                int boardingNonRecursive = findBoardingNonRecursive(strLine);
                boardingIds.add(boardingNonRecursive);
                if(boardingNonRecursive > maxBoardingId) {
                   maxBoardingId = boardingNonRecursive;
               }
            }
            // Close the input stream
            fstream.close();
            System.out.println("Max boarding id is: "+maxBoardingId);
            Collections.sort(boardingIds);
            int prevBoardingId = 20;
            for(int boardingId : boardingIds) {
                if(boardingId - prevBoardingId != 1) {
                    System.out.println("previous boarding id: "+prevBoardingId+" current boarding id: "+boardingId);
                }
                prevBoardingId = boardingId;
            }

        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    private static int findBoarding(String boardingPassId, int row, int rowMax, int rowMin, int column, int columnMax, int columnMin) {
        System.out.println(row + " " + column);
        if(boardingPassId.length() != 0) {
            char character = boardingPassId.charAt(0);
            if (character == 'F') {
                int newRowMax = rowMin + (rowMax - rowMin) / 2;
                findBoarding(boardingPassId.substring(1), newRowMax, newRowMax, rowMin, column, columnMax, columnMin);
            }
            if (character == 'B') {
                int newRowMin = rowMin + (rowMax - rowMin) / 2 + 1;
                findBoarding(boardingPassId.substring(1), newRowMin, rowMax, newRowMin, column, columnMax, columnMin);
            }
            if (character == 'L') {
                int newColumnMax = columnMin + (columnMax - columnMin) / 2;
                findBoarding(boardingPassId.substring(1), row, rowMax, rowMin, newColumnMax, newColumnMax, columnMin);
            }
            if (character == 'R') {
                int newColumnMin = columnMin + (columnMax - columnMin) / 2 + 1;
                findBoarding(boardingPassId.substring(1), row, rowMax, rowMin, newColumnMin, columnMax, newColumnMin);
            }
        }
        return row * 8 + column;

    }

    private static int findBoardingNonRecursive(String boardingPassId) {

        int row = 0;
        int rowMax = 127;
        int rowMin = 0;
        int column = 0;
        int columnMax = 7;
        int columnMin = 0;

        while(boardingPassId.length() !=0) {
            char character = boardingPassId.charAt(0);
            if (character == 'F') {
                int newRowMax = rowMin + (rowMax - rowMin) / 2;
                rowMax = newRowMax;
                row = rowMax;
            }
            if (character == 'B') {
                int newRowMin = rowMin + (rowMax - rowMin) / 2 + 1;
                rowMin = newRowMin;
                row = rowMin;
            }
            if (character == 'L') {
                int newColumnMax = columnMin + (columnMax - columnMin) / 2;
                columnMax = newColumnMax;
                column = columnMax;
            }
            if (character == 'R') {
                int newColumnMin = columnMin + (columnMax - columnMin) / 2 + 1;
                columnMin = newColumnMin;
                column = columnMin;
            }
            boardingPassId = boardingPassId.substring(1);
        }
//        System.out.println("row: "+row);
//        System.out.println("column: "+column);
        return row * 8 + column;
    }


}
