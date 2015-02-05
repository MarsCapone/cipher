package spd.cipher;// by samson

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Translated from ngram_score.py from practicalcryptography.com
 */
class NGramScore {

    private final HashMap<String, Double> ngrams = new HashMap<>();
    private final int L;
    private final double floor;

    private NGramScore(String ngramfile, String separator, int ngramSize) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/samson/IdeaProjects/cipher/ngrams/"+ngramfile));
            while ((line = br.readLine()) != null) {
                String[] keycount = line.split(separator);
                ngrams.put(keycount[0].toLowerCase(), Double.valueOf(keycount[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        L = ngramSize;

        Iterator it = ngrams.values().iterator();
        int n = 0;
        while (it.hasNext()) {
            n += (double) it.next();
        }

        Set<String> keys = ngrams.keySet();
        for (String key: keys) {
            double value = Math.log10((float) (ngrams.get(key) / n));
            ngrams.put(key, value);
        }

        floor = Math.log10(0.01/ n);
    }

    public NGramScore(String ngramfile, int ngramSize) {
        this(ngramfile, " ", ngramSize);
    }

    public double score(String text) {
        double score = 0;

        for (int i=0; i<(text.length()-L+1); i++) {
            String gram = text.substring(i, i+L);
            if (ngrams.containsKey(gram)) {
                score += ngrams.get(gram);
            } else {
                score += floor;
            }
        }
        return score;
    }


}
