package spd.cipher;// by samson

public class Test {
    public static void main(String[] args) {
        String plain = "defendtheeastwallofthecastle";
        String cipher = "vptnvffuntshtarptymjwzirappljmhhqvsubwlzzygvtyitarptyiougxiuydtgzhhvvmumshwkzgstfmekvmpkswdgbilvjljmglmjfqwioiivknulvvfemioiemojtywdsajtwmtcgluysdsumfbieugmvalvxkjduetukatymvkqzhvqvgvptytjwwldyeevquhlulwpkt";
        String vigdec = new Vigenere(plain, 1).encrypt("bc");

        Vigenere vigen = new Vigenere(cipher);
        String vigpl = vigen.decrypt();

        System.out.println(vigpl);

        System.out.println(vigen.decrypt("ciphers"));
        System.out.println(English.inferSpaces(vigen.decrypt("ciphers")));
    }
}
