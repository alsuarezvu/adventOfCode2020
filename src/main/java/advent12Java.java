import javax.management.DescriptorRead;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/12
 */
public class advent12Java {

    private static int EAST = 1;
    private static int WEST = -1;
    private static int NORTH = 1;
    private static int SOUTH = -1;

    public static void main(String[] args) {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent12Test.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            List<String> lines = new ArrayList<>();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                lines.add(strLine.trim());
            }

            //change preamble to 5 for testing with advent9Test.txt
            System.out.println("Part 1 answer is: " + challenge1(lines));
            //System.out.println("Part 2 answer is: " + challenge2(lines));
            // Close the input stream
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    private static int challenge1(final List<String> lines) {

        //start facing east
        Direction direction = Direction.EAST;

        int eastWestTracker = 0;
        int northSouthTracker = 0;

        for(String line : lines) {
            final char action = line.charAt(0);
            final int movement = Integer.parseInt(line.substring(1));

            System.out.println("action: "+action);
            System.out.println("movement: "+movement);

            switch(action) {
                case 'F':
                    if(direction == Direction.EAST) {
                        eastWestTracker = eastWestTracker + movement;
                    }
                    if(direction == Direction.WEST) {
                        eastWestTracker = eastWestTracker - movement;
                    }
                    if(direction == Direction.NORTH) {
                        northSouthTracker = northSouthTracker + movement;
                    }
                    if(direction == Direction.SOUTH) {
                        northSouthTracker = northSouthTracker - movement;
                    }
                    break;
                case 'N':
                    northSouthTracker = northSouthTracker + movement;
                    break;
                case 'S':
                    northSouthTracker = northSouthTracker - movement;
                    break;
                case 'E':
                    eastWestTracker = eastWestTracker + movement;
                    break;
                case 'W':
                    eastWestTracker = eastWestTracker - movement;
                    break;
                case 'L':




            }



        }

        return 0;
    }

    private enum Direction {
        NORTH(0),
        EAST(90),
        SOUTH(180),
        WEST(270);

        private final int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue(){
            return value;
        }

        public static Direction getDirection(int value){
            Direction direction = null;
            switch(value) {
                case 0:
                    direction = NORTH;
                    break;
                case 90:
                    direction = EAST;
                break;
                case 180:
                    direction = SOUTH;
                break;
                case 270:
                    direction = WEST;
                break;
            }
            return direction;
        }
    }

    private static Direction turn(final Direction currentDirection, final int angle, final char turnDirection) {
        int directionSum = 0;
        if(turnDirection == 'R') {
            directionSum = currentDirection.getValue() + angle;
            if(directionSum > 270) {
                directionSum = directionSum - 360;
            }
        }
        else if(turnDirection == 'L') {
            directionSum = currentDirection.getValue() - angle;
        }

        return Direction.getDirection(Math.abs(directionSum));
    }

}