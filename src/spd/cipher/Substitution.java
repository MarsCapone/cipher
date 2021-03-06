package spd.cipher;

import java.util.*;

class Substitution extends Cipher {

    // keymap is plain:cipher
    private final HashMap<Character,Character> keymap = new HashMap<>();
    private String key;
    private HashMap<Character,Character> inverseKeymap = new HashMap<>();

    Substitution() {
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
     * @param key The key to use for encryption or decryption
     */
    void setKey(String key) {
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
     * @param key The key to inialise
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
        NGramScore ngrams = new NGramScore("quadgrams.txt", 4);
        String decipheredText;
        double fitness;
        ArrayList<String> allDeciphers = new ArrayList<>();
        for (int i=0; i<50; i++) {
            String parentKey = newRandomKey();
            decipheredText = decrypt(parentKey);
            fitness = ngrams.score(decipheredText);
            int count = 0;
            while (count < 2000) {
                count++;
                String newKey = swap2letters(parentKey);
                String newText = decrypt(newKey);
                double newFitness = ngrams.score(newText);
                if (newFitness > fitness) {
                    parentKey = newKey;
                    decipheredText = newText;
                    fitness = newFitness;
                    count = 0;

                    //System.out.printf("Attempt: %d | Fitness: %3.3f \n", realcount, fitness);
                    //System.out.println(decipheredText);
                    //System.out.println();
                }

            }
            allDeciphers.add(decipheredText);

        }

        Collections.sort(allDeciphers, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                double s1chi = English.chiSquaredStat(s1);
                double s2chi = English.chiSquaredStat(s2);
                if (s1chi < s2chi) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        HashMap<String, Integer> decryptionCounts = new HashMap<>();
        for (String i: allDeciphers) {
            if (decryptionCounts.containsKey(i)) {
                decryptionCounts.put(i, decryptionCounts.get(i)+1);
            } else {
                decryptionCounts.put(i, 1);
            }
        }

        Iterator it = decryptionCounts.entrySet().iterator();
        String solution = "";
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            if ((int) pairs.getValue() > count) {
                count = (int) pairs.getValue();
                solution = (String) pairs.getKey();
            }
        }

        return solution;
    }




}
