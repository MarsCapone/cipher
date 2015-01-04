package spd.cipher;

import java.util.*;

public class English {

    private static final int ENGLISH_12POINT_CUTOFF = 9;

    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final char[] alphabetArray = alphabet.toCharArray();


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

    public static boolean isEnglishEnough(String string) {
        return englishScore(string) >= ENGLISH_12POINT_CUTOFF;
    }
}
