import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/11
 */
public class advent11Java {

    final static char OCCUPIED = '#';

    final static char EMPTY = 'L';

    final static int INVALID_ROW = -1;

    public static void main(String[] args) {
        try {
            final FileInputStream fstream = new FileInputStream("src/main/resources/advent11.txt");
            final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            List<String> lines = new ArrayList<>();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                lines.add(strLine.trim());
            }

            fstream.close();

            //create a seat matrix
            char[][] seatMatrix = buildSeatMatrix(lines);

            seatMatrix = challenge1(seatMatrix);

            System.out.println("Challenge 1 - Number of occupied seats: " + getOccupiedSeats(seatMatrix));

            //rebuild for challenge2
            seatMatrix = buildSeatMatrix(lines);

            seatMatrix = challenge2(seatMatrix);

            System.out.println("Challenge 2 - Number of occupied seats: " + getOccupiedSeats(seatMatrix));


        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
     * If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
     * Otherwise, the seat's state does not change.
     */
    private static char[][] challenge1(char[][] seatMatrix) {
        boolean hasSeatChanges = true;
        char[][] seatMatrixCopy = deepCloneMatrix(seatMatrix);

        while (hasSeatChanges) {
            hasSeatChanges = false;
            for (int i = 0; i < seatMatrix.length; i++) {
                for (int j = 0; j < seatMatrix[0].length; j++) {
                    final char c = seatMatrix[i][j];
                    if (isEmpty(c) && numberOfImmediateAdjacentSeats(i, j, seatMatrix) == 0) {
                        seatMatrixCopy[i][j] = OCCUPIED;
                        hasSeatChanges = true;
                    } else if (isOccupied(c) && numberOfImmediateAdjacentSeats(i, j, seatMatrix) >= 4) {
                        seatMatrixCopy[i][j] = EMPTY;
                        hasSeatChanges = true;
                    }
                }

            }

            seatMatrix = deepCloneMatrix(seatMatrixCopy);

            //printSeatMatrix(seatMatrix);
            //System.out.println("\nhasSeatChanges:"+hasSeatChanges);

        }
        return seatMatrix;
    }

    /**
     * Now, instead of considering just the eight immediately adjacent seats, consider the first seat in each of those eight directions.
     */
    private static char[][] challenge2(char[][] seatMatrix) {
        boolean hasSeatChanges = true;
        char[][] seatMatrixCopy = deepCloneMatrix(seatMatrix);

        while (hasSeatChanges) {
            hasSeatChanges = false;
            for (int i = 0; i < seatMatrix.length; i++) {
                for (int j = 0; j < seatMatrix[0].length; j++) {
                    final char c = seatMatrix[i][j];
                    if (isEmpty(c) && numberOfFirstAdjacentSeats(i, j, seatMatrix) == 0) {
                        seatMatrixCopy[i][j] = OCCUPIED;
                        hasSeatChanges = true;
                    } else if (isOccupied(c) && numberOfFirstAdjacentSeats(i, j, seatMatrix) >= 5) {
                        seatMatrixCopy[i][j] = EMPTY;
                        hasSeatChanges = true;
                    }
                }

            }

            seatMatrix = deepCloneMatrix(seatMatrixCopy);
            //printSeatMatrix(seatMatrix);
            //System.out.println("\nhasSeatChanges:"+hasSeatChanges);
        }
        return seatMatrix;
    }

    private static int getOccupiedSeats(char[][] seatMatrix) {
        int count = 0;
        for (char[] matrix : seatMatrix) {
            //System.out.println();
            for (char c : matrix) {
                if (isOccupied(c)) {
                    count++;
                }
                //System.out.print(c);
            }
        }
        //System.out.println();
        return count;
    }

    private static char[][] deepCloneMatrix(final char[][] seatMatrix) {
        final char[][] seatMatrixCopy = new char[seatMatrix.length][seatMatrix[0].length];

        for (int i = 0; i < seatMatrix.length; i++) {
            System.arraycopy(seatMatrix[i], 0, seatMatrixCopy[i], 0, seatMatrix[0].length);
        }

        return seatMatrixCopy;
    }

    private static boolean isEmpty(final char c) {
        return c == EMPTY;
    }

    private static boolean isOccupied(final char c) {
        return c == OCCUPIED;
    }

    private static int numberOfFirstAdjacentSeats(final int row, final int col, final char[][] seatMatrix) {
        int occupiedCount = 0;
        int rowPtr = row - 1;
        int colOffset = 1;
        //check left top
        while (rowPtr > INVALID_ROW && col - colOffset > -1) {
            char currSeat = seatMatrix[rowPtr][col - colOffset];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            colOffset++;
            rowPtr--;
        }

        //check top
        rowPtr = row - 1;
        while (rowPtr > INVALID_ROW) {
            char currSeat = seatMatrix[rowPtr][col];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            rowPtr--;
        }

        //check top right
        rowPtr = row - 1;
        colOffset = 1;
        while (rowPtr > INVALID_ROW && col + colOffset < seatMatrix[0].length) {
            char currSeat = seatMatrix[rowPtr][col + colOffset];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            colOffset++;
            rowPtr--;
        }

        //check left
        colOffset = 1;
        while (col - colOffset > -1) {
            char currSeat = seatMatrix[row][col - colOffset];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            colOffset++;
        }

        //check right
        colOffset = 1;
        while (col + colOffset < seatMatrix[0].length) {
            char currSeat = seatMatrix[row][col + colOffset];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            colOffset++;
        }

        //check bottom right
        rowPtr = row + 1;
        colOffset = 1;
        while (rowPtr < seatMatrix.length && col - colOffset > -1) {
            char currSeat = seatMatrix[rowPtr][col - colOffset];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            colOffset++;
            rowPtr++;
        }

        //check bottom
        rowPtr = row + 1;
        while (rowPtr < seatMatrix.length) {
            char currSeat = seatMatrix[rowPtr][col];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            rowPtr++;
        }

        //check bottom right
        colOffset = 1;
        rowPtr = row + 1;
        while (rowPtr < seatMatrix.length && col + colOffset < seatMatrix[0].length) {
            char currSeat = seatMatrix[rowPtr][col + colOffset];
            if (isOccupied(currSeat)) {
                occupiedCount++;
                break;
            } else if (isEmpty(currSeat)) {
                break;
            }
            colOffset++;
            rowPtr++;
        }

        return occupiedCount;
    }

    private static int numberOfImmediateAdjacentSeats(final int row, final int col, final char[][] seatMatrix) {
        int occupiedCount = 0;

        //check row to the top
        if (row - 1 > INVALID_ROW) {
            occupiedCount += checkAdjacentSeatsForRow(col, seatMatrix[row - 1], false);
        }

        //check present row
        occupiedCount += checkAdjacentSeatsForRow(col, seatMatrix[row], true);

        //check row to the bottom
        if (row + 1 < seatMatrix.length) {
            occupiedCount += checkAdjacentSeatsForRow(col, seatMatrix[row + 1], false);
        }
        return occupiedCount;
    }

    private static int checkAdjacentSeatsForRow(final int col, final char[] row, final boolean isPresentRow) {
        int count = 0;

        //check left
        if (col - 1 > -1) {
            if (isOccupied(row[col - 1])) {
                count++;
            }
        }

        //check right
        if (col + 1 < row.length) {
            if (isOccupied(row[col + 1])) {
                count++;
            }
        }

        //only check col if row is NOT the present row
        if (!isPresentRow) {
            if (isOccupied(row[col])) {
                count++;
            }
        }

        return count;
    }

    private static char[][] buildSeatMatrix(final List<String> lines) {
        int rowLength = lines.size();
        int colLength = lines.get(0).length();

        char[][] seatMatrix = new char[rowLength][colLength];

        int lineCount = 0;
        for (String line : lines) {
            char[] charArray = line.toCharArray();
            int colCount = 0;
            for (char c : charArray) {
                seatMatrix[lineCount][colCount] = c;
                colCount++;
            }
            lineCount++;
        }
        return seatMatrix;
    }
}
