import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/7
 */
public class advent7Java {

    private final static String SHINY_GOLD = "shiny gold";

    public static void main(String[] args) {
        try {
            final FileInputStream fstream = new FileInputStream("src/main/resources/advent7.txt");
            final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            //creates a registry where the key is the bag color and the value is the bag object
            final Map<String, Bag> bagRegistry = createBagRegistry(br);

            final Set<Bag> parentBags = new HashSet<>();

            final Bag shinyGoldBag = bagRegistry.get(SHINY_GOLD);

            //iterate through parents of shiny gold bags to store all bags holding it
            for (final Bag bag : shinyGoldBag.parentBags) {
                parentBags.addAll(getAllParentBags(bag, new HashSet<>()));
            }
            System.out.println("Part 1 answer is: " + parentBags.size());
            System.out.println("Part 2 answer is: " + getCountOfAllChildBags(shinyGoldBag));
            fstream.close();
        } catch (Exception e) {// Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

    }


    private static HashSet<Bag> getAllParentBags(final Bag bag, final HashSet<Bag> outerMostParents) {
        outerMostParents.add(bag);
        for (final Bag motherBags : bag.parentBags) {
            outerMostParents.addAll(getAllParentBags(motherBags, outerMostParents));
        }
        return outerMostParents;
    }

    private static int getCountOfAllChildBags(final Bag bag) {
        int count = 0;
        if (bag.childBags.size() > 0) {
            for (final Map.Entry<Bag, Integer> childBag : bag.childBags.entrySet()) {
                final int countOfChildBags = childBag.getValue();
                count = count + countOfChildBags + (countOfChildBags * getCountOfAllChildBags(childBag.getKey()));
            }
        }
        return count;
    }

    private static Map<String, Bag> createBagRegistry(final BufferedReader br) throws IOException {
        final Map<String, Bag> bagRegistry = new HashMap<>();
        String strLine;

        while ((strLine = br.readLine()) != null) {
            final String[] tokens = strLine.split("contain");

            //1. process the "mother" bag first
            String motherToken = tokens[0].strip();
            Bag motherBag;

            String bagColor = motherToken.split("bag")[0].strip();

            motherBag = bagRegistry.get(bagColor);

            //create a bag instance if it doesn't exist
            if (motherBag == null) {
                motherBag = new Bag(bagColor, null);
                bagRegistry.put(bagColor, motherBag);
            }

            //2. process the children
            String[] childTokens = tokens[1].strip().split(",");

            for (String token : childTokens) {
                String childToken = token.strip();

                int count;
                char ch = childToken.charAt(0);
                if (Character.isDigit(ch)) {
                    count = Character.getNumericValue(ch);
                    String childBagName = childToken.substring(1).split("bag")[0].strip();

                    Bag childBag = bagRegistry.get(childBagName);
                    if (childBag != null) {
                        childBag.addParentBag(motherBag);
                    } else {
                        childBag = new Bag(childBagName, motherBag);
                        bagRegistry.put(childBagName, childBag);
                    }
                    motherBag.addChildBag(childBag, count);
                }
            }
        }
        return bagRegistry;
    }

    private static class Bag {

        private final String name;

        //bag name and count of bags
        private final HashMap<Bag, Integer> childBags = new HashMap<>();

        private final HashSet<Bag> parentBags = new HashSet<>();

        Bag(final String name, final Bag parent) {
            this.name = name;
            if (parent != null) {
                this.parentBags.add(parent);
            }
        }

        public void addChildBag(final Bag bag, final int count) {
            childBags.put(bag, count);
        }

        public void addParentBag(final Bag bag) {
            parentBags.add(bag);
        }

        @Override
        public String toString() {
            return "Bag{" +
                    "name='" + name + '\'' +
                    ", childBags=" + childBags +
                    //", motherBags=" + parentBags +
                    '}';
        }
    }
}
