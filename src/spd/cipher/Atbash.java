package spd.cipher;

public class Atbash extends Substitution {

    public Atbash(String ciphertext) {
        super(ciphertext);
        setKey("zyxwvutsrqponmlkjihgfedcba");
    }

    public Atbash(String text, int option) {
        super(text, option);
        setKey("zyxwvutsrqponmlkjihgfedcba");
    }

    // no need to define encrypt or decrypt, because there is only one key
    // one the key is set in initialisation, decryption is as in the
    // substitution cipher.

}
