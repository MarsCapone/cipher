package spd.cipher;// by samson

public class Test {
    public static void main(String[] args) {
        String plain = "defendtheeastwallofthecastle";
        String cipher = "SOWFBRKAWFCZFSBSCSBQITBKOWLBFXTBKOWLSOXSOXFZWWIBICFWUQLRXINOCIJLWJFQUNWXLFBSZXFBT\n" +
                "XAANTQIFBFSFQUFCZFSBSCSBIMWHWLNKAXBISWGSTOXLXTSWLUQLXJBUUWLWISTBKOWLSWGSTOXLXTSWL\n" +
                "BSJBUUWLFULQRTXWFXLTBKOWLBISOXSSOWTBKOWLXAKOXZWSBFIQSFBRKANSOWXAKOXZWSFOBUSWJBSBF\n" +
                "TQRKAWSWANECRZAWJ";
        String vigdec = new Vigenere(plain, 1).encrypt("bc");


        //System.out.println(vigdec);
        //System.out.println(vigdec.length());

        Vigenere vigen = new Vigenere(vigdec);
        String vigpl = vigen.decrypt(2);
        System.out.println(vigpl);

    }
}
