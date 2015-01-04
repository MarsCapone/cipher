package spd.cipher;

import com.sun.org.apache.xml.internal.security.utils.resolver.implementations.ResolverAnonymous;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public void setKey(String key) {
        key = key.toLowerCase();
        if (key.length() == 26) {
            this.key = key;
        } else {
            this.key = generateKeySequence(key);
        }
        initKeymap();
    }

    private void initKeymap() {
        char[] alphabetArray = English.alphabet.toCharArray();
        char[] keyArray = key.toCharArray();
        for (int i=0; i<26; i++) {
            keymap.put(alphabetArray[i], keyArray[i]);
        }
        inverseKeymap = invert(keymap);
    }


    private void initKeymap(String key) {
        setKey(key);
        initKeymap();
    }

    public String encrypt() {
        char[] plaintextArray = plaintext.toCharArray();
        char[] ciphertextArray = new char[plaintextArray.length];

        for (int i=0; i<plaintextArray.length; i++) {
            ciphertextArray[i] = keymap.get(plaintextArray[i]);
        }
        return new String(ciphertextArray);
    }

    public String encrypt(String key) {
        initKeymap(key);
        return encrypt();
    }

    public String decrypt() {
        char[] ciphertextArray = ciphertext.toCharArray();
        char[] plaintextArray = new char[ciphertextArray.length];

        for (int i=0; i<ciphertextArray.length; i++) {
            plaintextArray[i] = inverseKeymap.get(ciphertextArray[i]);
        }
        return new String(plaintextArray);
    }

    public String decrypt(String key) {
        initKeymap(key);
        return decrypt();
    }

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

    private String newRandomKey() {
        String newKey = "";
        Random r = new Random();

        for (int i=0; i<26; i++) {
            String next = String.valueOf((char) r.nextInt(26) + 97); // don't think this will work
            if (!newKey.contains(next)) {
                newKey += next;
            }
        }
        return newKey;
    }

    public String hillClimbDecrypt() {
        String seedKey = newRandomKey();
        return "";
    }


}
