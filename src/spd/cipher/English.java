package spd.cipher;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class English {

    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final char[] alphabetArray = alphabet.toCharArray();
    public static final Map<Character, Double> expectedAlpha = createMap(); // the expected fractions of the english language for specific characters
    public static TrieSET trieset = createTrieSet(); // A Trie Set preloaded with keys from a dictionary

    /**
     * @return Setup for the expectedAlpha variable
     */
    private static Map<Character, Double> createMap() {
        Map<Character, Double> result = new HashMap<>();
        result.put('a', 0.08167); result.put('b', 0.01492); result.put('c', 0.02782);
        result.put('d', 0.04253); result.put('e', 0.12702); result.put('f', 0.02228);
        result.put('g', 0.02015); result.put('h', 0.06094); result.put('i', 0.06966);
        result.put('j', 0.00153); result.put('k', 0.00772); result.put('l', 0.04025);
        result.put('m', 0.02406); result.put('n', 0.06749); result.put('o', 0.07507);
        result.put('p', 0.01929); result.put('q', 0.00095); result.put('r', 0.05987);
        result.put('s', 0.06327); result.put('t', 0.09056); result.put('u', 0.02758);
        result.put('v', 0.00978); result.put('w', 0.02360); result.put('x', 0.00150);
        result.put('y', 0.01974); result.put('z', 0.00074);

        return Collections.unmodifiableMap(result);
    }

    /**
     * @return Setup for the trieset variable
     */
    private static TrieSET createTrieSet() {
        try {
            HashSet<String> dict = new HashSet<String>(FileUtils.readLines(new File("/home/samson/IdeaProjects/cipher/dictionaries/cracklib-small")));
            TrieSET trie = new TrieSET();
            Iterator it = dict.iterator();
            while (it.hasNext()) {
                trie.add((String) it.next());
            }
            return trie;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create map of characters and numbers for counts of letters in a string
     * @param string
     * @return
     */
    public static LinkedHashMap<Character,Double> frequencyAnalysis(String string) {
        LinkedHashMap<Character,Double> freq = new LinkedHashMap<Character, Double>();
        for (int i=0; i<string.length(); i++) {
            Character c = string.charAt(i);
            Double currentCount = freq.get(c);
            if (currentCount != null) {
                freq.put(c, currentCount+1.0);
            } else {
                freq.put(c, 1.0);
            }
        }
        return freq;
    }

    /**
     * Inefficient way to calculate englishness of a String. The highest value is 12 (very english).
     * The lowest value is 0 (not very english)
     * @param string The string to calculate englishness for.
     * @return A value in the range 0-12
     */
    @Deprecated
    public static int englishScore(String string) {

            LinkedHashMap<Character, Double> frequency = frequencyAnalysis(string);
            List<Map.Entry<Character, Double>> sortedFrequency = new ArrayList<Map.Entry<Character, Double>>(frequency.entrySet());
            Collections.sort(sortedFrequency, new Comparator<Map.Entry<Character, Double>>() {
                @Override
                public int compare(Map.Entry<Character, Double> a, Map.Entry<Character, Double> b) {
                    return b.getValue().compareTo(a.getValue());
                }
            });

            int points = 0;
        try {
            char[] fullPointsStart = {'e', 't', 'a', 'o', 'i', 'n'};
            char[] fullPointsEnd = {'v', 'k', 'x', 'j', 'q', 'z'};

            for (char startChar : fullPointsStart) {
                List<Map.Entry<Character, Double>> actualStart = sortedFrequency.subList(0, 6);
                for (Map.Entry actualEntry : actualStart) {
                    if (actualEntry.getKey().equals(startChar)) {
                        points++;
                    }
                }
            }

            for (char endChar : fullPointsEnd) {
                List<Map.Entry<Character, Double>> actualEnd = sortedFrequency.subList(frequency.size() - 6, frequency.size());
                for (Map.Entry actualEntry : actualEnd) {
                    if (actualEntry.getKey().equals(endChar)) {
                        points++;
                    }
                }
            }

            return points;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not enough letters in the string.");
            e.printStackTrace();
            System.out.println(points);
            System.out.println();
        }
        return 0;
    }

    /**
     * Calculate the Chi Squared Statistic for how English a string seems.
     * Lower values are more English.
     * @param string The string to calculate the statistic for
     * @return The value of the statistic
     */
    public static double chiSquaredStat(String string) {
        double sum = 0;
        double len = string.length();
        LinkedHashMap frequency = English.frequencyAnalysis(string);
        Iterator it = English.expectedAlpha.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            char letter = (char) pairs.getKey(); double expectedCount = (double) pairs.getValue() * len;
            Object actualC = frequency.get(letter);
            double actualCount = 0.0;
            if (actualC != null) {
                actualCount = (double) actualC;
            }

            sum += Math.pow((actualCount - expectedCount), 2) / expectedCount;
        }
        return sum;
    }

    /**
     * Infer the spaces in a string. This currently not very well done.
     * Currently uses the default trieset dictionary.
     * @param text The text to find spaces for.
     * @return The newly spaced text.
     */
    public static String inferSpaces(String text) {
        return inferSpaces(trieset, text);
    }

    /**
     * Infer the spaces in a string. Currently not well done.
     * @param set The Trie set to use
     * @param text The text to find spaces for
     * @return The newly spaced text
     */
    public static String inferSpaces(TrieSET set, String text) {
        StringBuilder sb = new StringBuilder();
        String root = text;
        while (root.length()>0) {
            String word = set.longestPrefixOf(root);
            root = root.substring(word.length());
            sb.append(word);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String getBestChi(String[] texts) {
        String solution = "";
        double fitness = 9999;
        for (String text : texts) {
            double newFitness = chiSquaredStat(text);
            if (newFitness < fitness) {
                fitness = newFitness;
                solution = text;
            }
        }
        return solution;
    }
}
