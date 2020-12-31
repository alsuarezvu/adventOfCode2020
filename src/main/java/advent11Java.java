import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/11
 */
public class advent11Java {
    public static void main(String[] args) {
        try {
            final FileInputStream fstream = new FileInputStream("src/main/resources/advent11Test.txt");
            final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            List<String> lines = new ArrayList<>();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                lines.add(strLine.trim());
            }

            //create matrix
            char[][] seatMatrix = buildSeatMatrix(lines);

            int lineSize = lines.size();
            int colSize = lines.get(0).length();

            for(int i=0 ; i<lineSize; i++) {
                for(int j=0; i<colSize; colSize++) {
                    System.out.println(seatMatrix[i][j]);
                }
            }
            //System.out.println("Part 1 answer is: " + challenge1(lines));
            //System.out.println("Part 2 answer is: " + challenge2(lines));
            // Close the input stream
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static char[][] buildSeatMatrix(final List<String> lines) {
        char[][] seatMatrix = new char[lines.size()][lines.get(0).length()];

        int lineCount =0;
        for (String line : lines) {
            char[] charArray = line.toCharArray();
            int colCount = 0;
            for(char c : charArray) {
                seatMatrix[lineCount][colCount] = c;
                colCount++;
            }
            lineCount++;
        }
        return seatMatrix;
    }
}
