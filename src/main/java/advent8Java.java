import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/8
 */
public class advent8Java {

    public static void main (String[] args ) {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent8.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            List<String> lines = new ArrayList<>();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                lines.add(strLine.trim());
            }

            System.out.println("Part 1 answer is: "+ getAccumulator(lines, null).getAccumulator());
            System.out.println("Part 2 answer is: "+findInstructionCausingLoop(lines));
            // Close the input stream
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }

    private static Result getAccumulator(final List<String> lines, final List<Integer> jmpAndNopBreadCrumb) {
        //begin at 0
        int instructionPtr = 0;
        int accumulator = 0;
        boolean hasInfiniteLoop = false;
        HashSet<Integer> linesVisited = new HashSet<>();

        while (instructionPtr <= lines.size() - 1) {
            final String instructionLine = lines.get(instructionPtr);
            final String instruction = instructionLine.substring(0, 3);

            if(!linesVisited.contains(instructionPtr)) {
                linesVisited.add(instructionPtr);

                if(jmpAndNopBreadCrumb !=null) {
                    if(instruction.equals("jmp") || instruction.equals("nop")) {
                        jmpAndNopBreadCrumb.add(instructionPtr);
                    }
                }
            }
            else {
                hasInfiniteLoop = true;
                break;
            }


            char instructionParameterSign = instructionLine.charAt(4);
            int instructionParameter = Integer.parseInt(instructionLine.substring(5));
            instructionParameter = instructionParameterSign == '-' ? -instructionParameter : instructionParameter;

            switch(instruction) {
                case "acc":
                    accumulator = accumulator + instructionParameter;
                    instructionPtr = instructionPtr + 1;
                    break;
                case "jmp":
                    instructionPtr = instructionPtr + instructionParameter;
                    break;
                case "nop":
                    instructionPtr = instructionPtr + 1;
                    break;
                default:
                    System.out.println("invalid instruction "+ instructionLine);
            }
        }
        return new Result(hasInfiniteLoop, accumulator);
    }

    private static int findInstructionCausingLoop(final List<String> lines) {

        ArrayList<Integer> jmpAndNopBreadCrumbs = new ArrayList<>();

        Result result = getAccumulator(lines, jmpAndNopBreadCrumbs);

        //analyzing breadcrumbs and find which instruction to change
        for (int breadCrumb : jmpAndNopBreadCrumbs) {
            //make a copy of current lines
            ArrayList<String> newLinesToIterate  = new ArrayList<>(lines);

            String lineToReplaceInstruction = newLinesToIterate.get(breadCrumb);
            String currentInstruction = lineToReplaceInstruction.substring(0, 3);

            if(currentInstruction.equals("jmp") || currentInstruction.equals("nop")) {
                String newInstruction = currentInstruction.equals("jmp") ? lineToReplaceInstruction.replace("jmp", "nop") :
                        lineToReplaceInstruction.replace("nop", "jmp");
                newLinesToIterate.set(breadCrumb, newInstruction);

                result = getAccumulator(newLinesToIterate, null);

                if(!result.isHasInfiniteLoop()) {
                    System.out.println("Found a sequence that doesn't cause an infinite loop!  Changed line "+breadCrumb);
                    System.out.println("Replaced this instruction: "+lineToReplaceInstruction+" with this: "+newInstruction);
                    break;
                }
            }
        }
        return result.getAccumulator();
    }


    private static class Result {
        boolean hasInfiniteLoop;
        int accumulator;

        public Result(boolean hasInfiniteLoop, int accumulator) {
            this.hasInfiniteLoop = hasInfiniteLoop;
            this.accumulator = accumulator;
        }

        public boolean isHasInfiniteLoop() {
            return hasInfiniteLoop;
        }

        public int getAccumulator() {
            return accumulator;
        }
    }
}
