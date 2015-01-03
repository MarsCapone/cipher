package spd.cipher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cipher {

    protected String ciphertext;
    protected String plaintext;

    public Cipher(String ciphertext) {
        this.ciphertext = removePunctuation(ciphertext.toLowerCase());
    }

    public Cipher(String text, int option) {
        if (option == 0) {
            this.ciphertext = removePunctuation(text.toLowerCase());
        } else if (option == 1) {
            this.plaintext = removePunctuation(text.toLowerCase());
        }
    }


    public String getCiphertext() {
        return ciphertext;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = removePunctuation(ciphertext.toLowerCase());
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = removePunctuation(plaintext.toLowerCase());
    }

    public int[] ascify(String string) {
        char[] individualLetters = string.toCharArray();
        int[] asciiNumbers = new int[string.length()];
        for (int i=0; i<string.length(); i++) {
            asciiNumbers[i] = (int) individualLetters[i];
        }
        return asciiNumbers;
    }

    public String unascify(int[] asciis) {
        char[] individualLetters = new char[asciis.length];
        for (int i=0; i<asciis.length; i++) {
            individualLetters[i] = (char) asciis[i];
        }
        return new String(individualLetters);
    }

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

    protected static <V, K> HashMap<V, K> invert(HashMap<K, V> map) {
        HashMap<V, K> inv = new HashMap<V, K>();
        for (Map.Entry<K, V> entry : map.entrySet())
            inv.put(entry.getValue(), entry.getKey());
        return inv;
    }

}