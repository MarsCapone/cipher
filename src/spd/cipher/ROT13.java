package spd.cipher;

public class ROT13 extends Substitution {
    public ROT13(String ciphertext) {
        super(ciphertext);
        setKey("nopqrstuvwxyzabcdefghijklm");
    }

    public ROT13(String text, int option) {
        super(text, option);
        setKey("nopqrstuvwxyzabcdefghijklm");
    }
}
