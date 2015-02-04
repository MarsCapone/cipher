package spd.cipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cipher {

    protected String ciphertext;
    protected String plaintext;

    protected int textlength;

    public Cipher() {
        //default cipher message
        this("defend the east wall of the castle");
    }

    public Cipher(String ciphertext) {
        this.ciphertext = removePunctuation(ciphertext.toLowerCase());
        this.textlength = this.ciphertext.length();
    }

    public Cipher(String text, int option) {
        if (option == 0) {
            this.ciphertext = removePunctuation(text.toLowerCase());
            this.textlength = this.ciphertext.length();
        } else if (option == 1) {
            this.plaintext = removePunctuation(text.toLowerCase());
            this.textlength = this.plaintext.length();
        }

    }

    /**
     * Invert a HashMap
     * @param map The HashMap to invert
     * @param <V> A value
     * @param <K> A Key
     * @return A new HashMap with the keys and values swapped.
     */
    protected static <V, K> HashMap<V, K> invert(HashMap<K, V> map) {
        HashMap<V, K> inv = new HashMap<V, K>();
        for (Map.Entry<K, V> entry : map.entrySet())
            inv.put(entry.getValue(), entry.getKey());
        return inv;
    }

    /**
     * @return The ciphertect
     */
    public String getCiphertext() {
        return ciphertext;
    }

    /**
     * @return The plaintext
     */
    public String getPlaintext() {
        return plaintext;
    }

    /**
     * Turn a string to it's ascii numbers
     * @param string
     * @return An array of ints of the characters in the string
     */
    public int[] ascify(String string) {
        char[] individualLetters = string.toCharArray();
        int[] asciiNumbers = new int[string.length()];
        for (int i=0; i<string.length(); i++) {
            asciiNumbers[i] = (int) individualLetters[i];
        }
        return asciiNumbers;
    }

    /**
     * The opposite of ascify
     * @param asciis
     * @return A string from an array of ints of ascii values
     */
    public String unascify(int[] asciis) {
        char[] individualLetters = new char[asciis.length];
        for (int i=0; i<asciis.length; i++) {
            individualLetters[i] = (char) asciis[i];
        }
        return new String(individualLetters);
    }

    /**
     * Remove punctuation to a string
     * @param string
     * @return
     */
    public String removePunctuation(String string) {
        char[] stringCharacters = string.toCharArray();
        String newString = "";
        for (char c: stringCharacters) {
            if ((65 <= c && c <= 90) || (97 <= c && c <= 122)) {
                newString += c;
            }
        }
        return newString;
    }

    /**
     * Generate a completed key for substitution ciphers and similay from a key word
     * @param keyword
     * @return A 26 character key, beginning with the keyword, and subsequent letters in alphabetical order.
     */
    public String generateKeySequence(String keyword) {
        ArrayList<Character> key = new ArrayList<Character>(26);
        int keywordLength = keyword.length();
        for (int i=0; i<keywordLength; i++) {
            if (!key.contains(keyword.charAt(i))) {
                key.add(keyword.charAt(i));
            }
        }

        for (int j=keywordLength; j<26; j++) {
            for (char c: English.alphabetArray) {
                if (!key.contains(c)) {
                    key.add(c);
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        for (Character ch: key) {
            builder.append(ch);
        }
        return builder.toString();
    }

}
