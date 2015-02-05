package spd.cipher;

import java.util.ArrayList;

class Vigenere extends Cipher {

    private final ArrayList<String> shiftStorage = new ArrayList<>();
    private String KEY;

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
        shiftStorage.clear();
        int keylength = key.length();
        for (int s=0; s<keylength; s++) {
            StringBuilder sb = new StringBuilder();
            for (int c=s; c<textlength; c+=keylength) {
                sb.append(plaintext.charAt(c));
            }
            shiftStorage.add(String.valueOf(sb));
        }

        for (int t=0; t<keylength; t++) {
            shiftStorage.set(t, new CaesarShift(shiftStorage.get(t), 1).encrypt(key.charAt(t)-97));
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
        shiftStorage.clear();

        int keylength = key.length();

        for (int s=0; s<keylength; s++) {
            StringBuilder sb = new StringBuilder();
            for (int c=s; c<textlength; c+=keylength) {
                sb.append(ciphertext.charAt(c));
            }
            shiftStorage.add(String.valueOf(sb));
        }

        for (int t=0; t<keylength; t++) {
            String[] allShifts = new CaesarShift(shiftStorage.get(t)).decryptAllShifts();
            char shift = key.charAt(t);
            shiftStorage.set(t, allShifts[shift - 97]); // to get back to 0-26 range
        }

        StringBuilder plaintext = new StringBuilder();
        int row = 0;
        while (plaintext.length() < textlength) {
            for (int v=0; v<keylength; v++) {
                try {
                    plaintext.append(shiftStorage.get(v).charAt(row));
                } catch (StringIndexOutOfBoundsException e) {
                    plaintext.append("");
                }
            }
            row++;
        }
        return String.valueOf(plaintext);
    }

    public String decrypt(int keylength) {
        shiftStorage.clear();
        StringBuilder keyBuilder = new StringBuilder();

        for (int s=0; s<keylength; s++) {
            StringBuilder sb = new StringBuilder();
            for (int c=s; c<textlength; c+=keylength) {
                sb.append(ciphertext.charAt(c));
            }
            shiftStorage.add(String.valueOf(sb));
        }

        for (int t=0; t<keylength; t++) {
            String[] allShifts = new CaesarShift(shiftStorage.get(t)).decryptAllShifts();
            char shift = English.getBestChiKey(allShifts);
            shiftStorage.set(t, allShifts[shift-97]); // to get back to 0-26 range
            keyBuilder.append(shift);
        }

        KEY = String.valueOf(keyBuilder);

        return decrypt(String.valueOf(keyBuilder));
    }

    public String decrypt() {
        shiftStorage.clear();
        ArrayList<Double> icValues = new ArrayList<>();
        for (int key=1; key<30; key++) {
            StringBuilder sb = new StringBuilder();
            for (int c=key; c<textlength; c+=key) {
                sb.append(ciphertext.charAt(c));
            }
            double icValue = English.indexOfCoincidence(String.valueOf(sb));
            icValues.add(icValue);
            shiftStorage.add(String.valueOf(sb));
        }

        double max = -9999;
        int key = -1;
        for (int i=0; i<icValues.size(); i++) {
            double value = icValues.get(i);
            if (value > max) {
                max = value;
                key = i+1;
            }
        }

        return decrypt(English.highestPrimeFactor(key));
    }

    public String getDecryptionKey(int keylength) {
        decrypt(keylength);
        return KEY;
    }

    public String getDecryptionKey() {
        decrypt();
        return KEY;
    }

    public String getKEY() {
        return KEY;
    }
}
