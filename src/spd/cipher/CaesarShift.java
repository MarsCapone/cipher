package spd.cipher;

public class CaesarShift extends Cipher {

    public CaesarShift() {
        super();
    }

    public CaesarShift(String ciphertext) {
        super(ciphertext);
    }

    public CaesarShift(String text, int option) {
        super(text, option);
    }

    /**
     * Shift every letter in a string a specific number of places.
     * @param string The string to shift
     * @param shift The amount to shift each letter
     * @return The shifted text
     */
    public String shiftString(String string, int shift) {
        int[] asciiLetters = ascify(string);
        for (int a=0; a<asciiLetters.length; a++) {
            asciiLetters[a] = ((asciiLetters[a] + shift - 97) % 26) + 97;
        }
        return unascify(asciiLetters);
    }

    /**
     * Encrypt the plaintext
     * @param key
     * @return The encrypted plaintext
     */
    public String encrypt(int key) {
        return shiftString(plaintext, key);
    }

    /**
     * Decrypt the ciphertext
     * @param key
     * @return The decrypted text
     */
    public String decrypt(int key) {
        return shiftString(ciphertext, 26-key);
    }

    /**
     * Brute force decryption of the ciphertext
     * @return The decrypted text
     */
    public String decrypt() {
        String[] allShifts = decryptAllShifts();
        double score = 9999.0;
        String probablePlaintext = "";
        for (String shift: allShifts) {
            double chi = English.chiSquaredStat(shift);
            if (chi < score) {
                score = chi;
                probablePlaintext = shift;
            }
        }
        return probablePlaintext;
    }

    /**
     * Decrypt every shift
     * @return An array containg all decryptions of the text
     */
    public String[] decryptAllShifts() {
        String[] allShifts = new String[26];
        for (int i=0; i<26; i++) {
            allShifts[i] = decrypt(i);
        }
        return allShifts;
    }

}
