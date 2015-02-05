package spd.cipher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class English {

    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final char[] alphabetArray = alphabet.toCharArray();
    private static final Map<Character, Double> expectedAlpha = createMap(); // the expected fractions of the english language for specific characters
    private static final TrieSET trieset = createTrieSet(); // A Trie Set preloaded with keys from a dictionary

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
        HashSet<String> dict = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("/usr/share/dict/cracklib-small")));
            String line;
            while ((line = br.readLine()) != null) {
                dict.add(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Cracklib word list does not exist.");
        }
        TrieSET trie = new TrieSET();
        Iterator it = dict.iterator();
        while (it.hasNext()) {
            trie.add((String) it.next());
        }
        return trie;
    }

    /**
     * Create map of characters and numbers for counts of letters in a string
     * @param string The string to analyse
     * @return A map of characters and their frequencies in the text
     */
    private static LinkedHashMap<Character,Double> frequencyAnalysis(String string) {
        LinkedHashMap<Character,Double> freq = new LinkedHashMap<>();
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
    private static String inferSpaces(TrieSET set, String text) {
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

    public static char getBestChiKey(String[] texts) {
        double fitness = 9999;
        int index = 0;
        for (int t=0; t<texts.length; t++) {
            double newFitness = chiSquaredStat(texts[t]);
            if (newFitness < fitness) {
                fitness = newFitness;
                index = t;
            }
        }
        return (char) (index + 'a');
    }

    public static double indexOfCoincidence(String text) {
        double sum = 0;
        double len = text.length();
        LinkedHashMap frequency = English.frequencyAnalysis(text);
        Iterator it = frequency.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            double count = (double) pairs.getValue();
            sum += count * (count - 1);
        }
        return sum/(len*(len-1));
    }

    public static int highestPrimeFactor(int n) {
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29}; // keys are unlikely to be more than 30 characters
        int max = 0;
        for (int p : primes) {
            if (n%p == 0) {
                max = p;
            }
        }
        return max;
    }
}
