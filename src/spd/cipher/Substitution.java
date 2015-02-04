package spd.cipher;

import java.util.HashMap;
import java.util.Random;

public class Substitution extends Cipher {

    private String key;

    // keymap is plain:cipher
    private HashMap<Character,Character> keymap = new HashMap<Character, Character>();
    private HashMap<Character,Character> inverseKeymap = new HashMap<Character, Character>();

    public Substitution() {
        super();
    }

    public Substitution(String ciphertext) {
        super(ciphertext);
    }

    public Substitution(String text, int option) {
        super(text, option);
    }

    /**
     * Set a key for encryption or decryption
     * @param key
     */
    public void setKey(String key) {
        key = key.toLowerCase();
        if (key.length() == 26) {
            this.key = key;
        } else {
            this.key = generateKeySequence(key);
        }
        initKeymap();
    }

    /**
     * Initialise the key
     * Create a full 26 character key.
     * Create an inverse keymap for decryption
     */
    private void initKeymap() {
        char[] alphabetArray = English.alphabet.toCharArray();
        char[] keyArray = key.toCharArray();
        for (int i=0; i<26; i++) {
            keymap.put(alphabetArray[i], keyArray[i]);
        }
        inverseKeymap = invert(keymap);
    }

    /**
     * Initialise the keymap for a specific key
     * @param key
     */
    private void initKeymap(String key) {
        setKey(key);
        initKeymap();
    }

    /**
     * Encrypt the plaintext using the predefined key
     * @return The encrypted text
     */
    public String encrypt() {
        char[] plaintextArray = plaintext.toCharArray();
        char[] ciphertextArray = new char[plaintextArray.length];

        for (int i=0; i<plaintextArray.length; i++) {
            ciphertextArray[i] = keymap.get(plaintextArray[i]);
        }
        return new String(ciphertextArray);
    }

    /**
     * Encrypt the plaintext using a new key
     * @param key The key to encrypt with
     * @return The encrypted text
     */
    public String encrypt(String key) {
        initKeymap(key);
        return encrypt();
    }

    /**
     * Decrypt the ciphertext using the predefined key
     * @return The decrypted text
     */
    public String decrypt() {
        char[] ciphertextArray = ciphertext.toCharArray();
        char[] plaintextArray = new char[ciphertextArray.length];

        for (int i=0; i<ciphertextArray.length; i++) {
            plaintextArray[i] = inverseKeymap.get(ciphertextArray[i]);
        }
        return new String(plaintextArray);
    }

    /**
     * Decrypt the ciphertext with a new key
     * @param key The key to decrypt with
     * @return The decrypted text
     */
    public String decrypt(String key) {
        initKeymap(key);
        return decrypt();
    }

    /**
     * Swap 2 random letters in a key
     * @param key The key to change
     * @return The new key
     */
    private String swap2letters(String key) {
        Random r = new Random();
        char[] keyArray = key.toCharArray();
        int rA = r.nextInt(26);
        int rB = r.nextInt(26);
        char holder;
        holder = keyArray[rA];
        keyArray[rA] = keyArray[rB];
        keyArray[rB] = holder;
        return new String(keyArray);
    }

    /**
     * Generate a random key
     * @return A randomly generated key
     */
    private String newRandomKey() {
        String newKey = "";
        Random r = new Random();

        while (newKey.length() < 26) {
            String next = String.valueOf((char) (r.nextInt(26) + 97));
            if (!newKey.contains(next)) {
                newKey += next;
            }
        }
        return newKey;
    }

    /**
     * Perform a Hill Climbing Decryption for a substitution cipher
     * @return Hopefully the decrypted text.
     */
    public String hillClimbDecrypt() {
        //TODO Make this work. Would probably be better to implement QUADGRAM STATISTICS
        NGramScore ngrams = new NGramScore("quadgrams.txt", 4);
        String decipheredText = "";
        double fitness = -9999;
        for (int i=0; i<1000; i++) {
            String parentKey = newRandomKey();
            decipheredText = decrypt(parentKey);
            fitness = ngrams.score(decipheredText);
            int count = 0;
            while (count < 1000) {
                count++;
                String newKey = swap2letters(parentKey);
                String newText = decrypt(newKey);
                double newFitness = ngrams.score(newText);
                if (newFitness > fitness) {
                    parentKey = newKey;
                    decipheredText = newText;
                    fitness = newFitness;
                    count = 0;
                }
            }
        }
        System.out.println("#########################################################################");
        return decipheredText;
    }


}
