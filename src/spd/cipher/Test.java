package spd.cipher;// by samson

public class Test {
    public static void main(String[] args) {
        String plain = "Defend the east wall of the castle";
        String cipher = "SOWFBRKAWFCZFSBSCSBQITBKOWLBFXTBKOWLSOXSOXFZWWIBICFWUQLRXINOCIJLWJFQUNWXLFBSZXFBT\n" +
                "XAANTQIFBFSFQUFCZFSBSCSBIMWHWLNKAXBISWGSTOXLXTSWLUQLXJBUUWLWISTBKOWLSWGSTOXLXTSWL\n" +
                "BSJBUUWLFULQRTXWFXLTBKOWLBISOXSSOWTBKOWLXAKOXZWSBFIQSFBRKANSOWXAKOXZWSFOBUSWJBSBF\n" +
                "TQRKAWSWANECRZAWJ";

        Substitution sub = new Substitution(cipher);

        NGramScore ngrams = new NGramScore("quadgrams.txt", 4);

        //System.out.println(ngrams.score("ATTACKTHEEASTWALLOFTHECASTLEATDAWN".toLowerCase()));

        System.out.println(sub.hillClimbDecrypt());
    }
}
