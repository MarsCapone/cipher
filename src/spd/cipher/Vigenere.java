package spd.cipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Vigenere extends Cipher {

    private ArrayList<String> shiftStorage = new ArrayList<>();

    public Vigenere() {
        super();
    }

    public Vigenere(String ciphertext) {
        super(ciphertext);
    }

    public Vigenere(String text, int option) {
        super(text, option);
    }

    public String encrypt(String key) {
        int keylength = key.length();
        for (int s=0; s<keylength; s++) {
            StringBuilder sb = new StringBuilder();
            for (int c=s; c<textlength; c+=keylength) {
                sb.append(plaintext.charAt(c));
            }
            shiftStorage.add(String.valueOf(sb));
        }

        for (int t=0; t<keylength; t++) {
            shiftStorage.set(t, new CaesarShift(shiftStorage.get(t), 1).encrypt((int)(key.charAt(t)-97)));
        }

        StringBuilder ciphertext = new StringBuilder();
        int row = 0;
        while (ciphertext.length() < textlength) {
            for (int v=0; v<keylength; v++) {
                ciphertext.append(shiftStorage.get(v).charAt(row));
            }
            row++;
        }
        return String.valueOf(ciphertext);
    }

    public String decrypt(String key) {

        int keylength = key.length();
        for (int s=0; s<keylength; s++) {
            StringBuilder sb = new StringBuilder();
            for (int c=s; c<textlength; c+=keylength) {
                sb.append(ciphertext.charAt(c));
            }
            shiftStorage.add(String.valueOf(sb));
        }

        for (int t=0; t<keylength; t++) {
            shiftStorage.set(t, new CaesarShift(shiftStorage.get(t)).decrypt((int)(key.charAt(t)-97)));
        }

        StringBuilder plaintext = new StringBuilder();
        int row = 0;
        while (plaintext.length() < textlength) {
            for (int v=0; v<keylength; v++) {
                plaintext.append(shiftStorage.get(v).charAt(row));
            }
            row++;
        }
        return String.valueOf(plaintext);
    }

    public String decrypt(int keylength) {
        NGramScore ngrams = new NGramScore("quadgrams.txt", 4);

        for (int s=0; s<keylength; s++) {
            StringBuilder sb = new StringBuilder();
            for (int c=s; c<textlength; c+=keylength) {
                sb.append(ciphertext.charAt(c));
            }
            shiftStorage.add(String.valueOf(sb));
        }

        for (int t=0; t<keylength; t++) {
            String[] allShifts = new CaesarShift(shiftStorage.get(t)).decryptAllShifts();
            shiftStorage.set(t, English.getBestChi(allShifts));
        }

        StringBuilder plaintext = new StringBuilder();
        int row = 0;
        while (plaintext.length() < textlength) {
            for (int v=0; v<keylength; v++) {
                plaintext.append(shiftStorage.get(v).charAt(row));
            }
            row++;
        }
        return String.valueOf(plaintext);
    }

    public String decrypt() {
        HashMap<String, Integer> separatings = new HashMap<>();
        for (int i=0; i<textlength-3; i++) {
            String sub = ciphertext.substring(i, i+3);
            int sep = ciphertext.indexOf(sub, i+3);
            separatings.put(sub, sep);
        }

        Iterator it = separatings.values().iterator();
        ArrayList<Integer> factors = new ArrayList<>();
        while (it.hasNext()) {

        }

    }
}
