package spd.cipher;

import java.util.ArrayList;

public class CaesarShift extends Cipher {

    private ArrayList<String> probableSolutions;

    public CaesarShift() {
        super();
    }

    public CaesarShift(String ciphertext) {
        super(ciphertext);
    }

    public CaesarShift(String text, int option) {
        super(text, option);
    }

    public String shiftString(String string, int shift) {
        int[] asciiLetters = ascify(string);
        for (int a=0; a<asciiLetters.length; a++) {
            asciiLetters[a] = ((asciiLetters[a] + shift - 97) % 26) + 97;
        }
        return unascify(asciiLetters);
    }

    public String encrypt(int key) {
        return shiftString(plaintext, key);
    }

    public String decrypt(int key) {
        return shiftString(ciphertext, 26-key);
    }

    public String decrypt() {
        String[] allShifts = decryptAllShifts();
        int score = 0;
        String probablePlaintext = "";
        ArrayList<String> probableShifts = new ArrayList<String>();
        for (String shift: allShifts) {
            if (English.englishScore(shift) > score) {
                score = English.englishScore(shift);
                probablePlaintext = shift;
                if (score == 12) {
                    probableShifts.add(shift);
                }
            }
        }
        this.probableSolutions = probableShifts;
        return probablePlaintext;
    }

    public String[] decryptAllShifts() {
        String[] allShifts = new String[26];
        for (int i=0; i<26; i++) {
            allShifts[i] = decrypt(i);
        }
        return allShifts;
    }

    public ArrayList<String> getProbableSolutions() {
        return probableSolutions;
    }
}
