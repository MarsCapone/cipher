package spd.cipher;

class Affine extends Cipher {

    public Affine(String ciphertext) {
        super(ciphertext);
    }

    public Affine(String text, int option) {
        super(text, option);
    }


    /**
     * Encrypt an individual character
     * @param letter The character to encrypt
     * @param a The coefficient
     * @param b The constant
     * @return The encrypted character
     */
    private char encLet(char letter, int a, int b) {
        return (char) (((a * (letter-97) + b)%26)+97);
    }

    /**
     * Decrypt an individual character
     * @param letter The character to decrypt
     * @param inverse_a The inverse of the coefficient of a
     * @param b The constant
     * @return The decrypted character
     */
    private char decLet(char letter, int inverse_a, int b) {
        int val = (inverse_a*((letter-97) - b));
        return (char) (((val % 26 + 26) % 26)+97); //to get around java handling modulo weirdly
    }

    /**
     * Encrypt the plaintext
     * @param a Coefficient
     * @param b Constant
     * @return The encrypted plaintext
     */
    public String encrypt(int a, int b) {
        char[] plaintextArray = plaintext.toCharArray();
        char[] ciphertextArray = new char[textlength];
        for (int i=0; i<textlength; i++) {
            ciphertextArray[i] = encLet(plaintextArray[i], a, b);
        }
        return new String(ciphertextArray);
    }

    /**
     * Decrypt the ciphertext
     * @param a The encryption coefficent
     * @param b The encryption constant
     * @return The decrypted text
     */
    public String decrypt(int a, int b) {
        char[] ciphertextArray = ciphertext.toCharArray();
        char[] plaintextArray = new char[textlength];

        // find inverse_a first, so as not to calculate for every letter
        int inverse_a = 0;
        for (int i=1; i<26; i++) {
            if ((a*i)%26 == 1) {
                inverse_a = i;
                break;
            }
        }

        for (int i=0; i<textlength; i++) {
            char letter = decLet(ciphertextArray[i], inverse_a, b);
            if (letter <= 122 || letter >= 97) {
                plaintextArray[i] = letter;
            } else {
                plaintextArray[i] = '@';
            }
        }
        return new String(plaintextArray);
    }

    /**
     * Brute force decryption of ciphertext.
     * @return Decrypted text
     */
    public String decrypt() {
        double score = 9999.0;
        String probableSolution = "";

        for (int b=0; b<26; b++) {
            for (int a=1; a<26; a+=2) {
                String temp = decrypt(a,b);
                double tempScore = English.chiSquaredStat(temp);
                if (tempScore < score) {
                    score = tempScore;
                    probableSolution = temp;
                    if (score < 50) {
                        return probableSolution;
                    }
                }
            }
        }
        return probableSolution;
    }
}
