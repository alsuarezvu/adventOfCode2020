import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * https://adventofcode.com/2020/day/7
 */
public class advent7Java {

    public static void main (String[] args ) {
        try {
            FileInputStream fstream = new FileInputStream("src/main/resources/advent7Test.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            HashMap<String,Bag> bagRegistry = createBagRegistry(br);

            int countOfBags = 0;
            //iterate registry and find bags which have shiny gold as children
            for (Map.Entry<String, Bag> entry :  bagRegistry.entrySet()) {
                HashMap<Bag, Integer> childBags = entry.getValue().bags;
                for(Map.Entry<Bag,Integer> child : childBags.entrySet()) {
                    Bag childBag = child.getKey();
                    if(childBag.name.equals("shiny gold")) {
                       countOfBags++;
                   }
                   else {
                       //traverse the children to get to an empty set
                       
                   }
                }
                if(childBags.containsKey("shiny gold")) {
                    countOfBags++;
                }
            }
            fstream.close();
            System.out.println("Part 1 answer is: "+countOfBags);
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }


    private static HashMap<String,Bag> createBagRegistry(final BufferedReader br) throws IOException {
        final HashMap<String, Bag> bagRegistry = new HashMap<>();
        String strLine;

        while ((strLine = br.readLine()) != null) {
            final String[] tokens = strLine.split("contain");

            //process the "mother" bag first
            String motherToken = tokens[0].strip();
            Bag motherBag;

            //create a bag instance if it doesn't exist
            String bagColor = motherToken.split("bag")[0].strip();
            if (bagRegistry.containsKey(bagColor)) {
                motherBag = bagRegistry.get(bagColor);
            } else {
                motherBag = new Bag(bagColor);
                bagRegistry.put(bagColor, motherBag);
            }

            //process the children
            String[] childTokens = tokens[1].strip().split(",");

            for (int i = 0; i < childTokens.length; i++) {
                String childToken = childTokens[i].strip();

                int count;
                char ch = childToken.charAt(0);
                if (Character.isDigit(ch)) {
                    count = Character.getNumericValue(ch);
                    String childBagName = childToken.substring(1).split("bag")[0].strip();
                    Bag childrenBag;
                    if (bagRegistry.containsKey(childBagName)) {
                        childrenBag = bagRegistry.get(childBagName);
                    } else {
                        childrenBag = new Bag((childBagName));
                    }

                    motherBag.addBag(childrenBag, count);
                }
            }
        }
        System.out.println(bagRegistry);
        return bagRegistry;
    }


    private static class Bag {

        private String name;

        //bag name and count of bags
        private HashMap<Bag, Integer> bags = new HashMap<>();

        Bag(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void addBag(final Bag bag, final int count) {
            bags.put(bag, count);
        }

        @Override
        public String toString() {
            return "Bag{" +
                    "name='" + name + '\'' +
                    ", bags=" + bags +
                    '}';
        }
    }
}
