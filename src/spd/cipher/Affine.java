package spd.cipher;

import java.util.ArrayList;

public class Affine extends Cipher {
    private int a_key;
    private int b_key;

    public Affine(String ciphertext) {
        super(ciphertext);
    }

    public Affine(String text, int option) {
        super(text, option);
    }

    public void setKey(int a, int b) {
        a_key = a; b_key = b;
    }

    private char encLet(char letter) {
        return (char) ((a_key * letter + b_key)%26);
    }

    private char decLet(char letter) {
        int inverse_a = 0;
        for (int i=1; i<26; i++) {
            if ((a_key*i)%26 == 1) {
                inverse_a = i;
                break;
            }
        }
        return (char) (inverse_a*(letter - b_key));
    }

    public String encrypt() {
        char[] plaintextArray = plaintext.toCharArray();
        char[] ciphertextArray = new char[textlength];
        for (int i=0; i<textlength; i++) {
            ciphertextArray[i] = encLet(plaintextArray[i]);
        }
        return new String(ciphertextArray);
    }

    public String decrypt(int a_key, int b_key) {
        setKey(a_key, b_key);
        char[] ciphertextArray = ciphertext.toCharArray();
        char[] plaintextArray = new char[textlength];
        for (int i=0; i<textlength; i++) {
            plaintextArray[i] = decLet(ciphertextArray[i]);
        }
        return new String(plaintextArray);
    }

    public String encrypt(int a_key, int b_key) {
        setKey(a_key, b_key);
        return encrypt();
    }

    public String decrypt() {
        int score = 0;
        String probableSolution = "";

        for (int b=0; b<26; b++) {
            for (int a=1; a<26; a+=2) {
                String tempSol = decrypt(a,b);
                int tempScore = English.englishScore(tempSol);
                if (tempScore > score) {
                    score = tempScore;
                    probableSolution = tempSol;
                    if (score == 12) {
                        return probableSolution;
                    }
                }
            }
        }
        return probableSolution;
    }

    public ArrayList<String> decryptAll() {
        ArrayList<String> allDecypts = new ArrayList<String>();
        for (int b=0; b<26; b++) {
            for (int a=1; a<26; a+=2) {
                allDecypts.add(decrypt(a,b));
            }
        }
        return allDecypts;
    }

    //TODO Make this work. Currently produces null characters and IndexOutOfBoundsException.

}
