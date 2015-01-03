package spd.cipher;

import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference;

public class Main {

    public static void main(String[] args) {

        String plaintext = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

        Substitution sub = new Substitution(plaintext, 1);

        String key = "happy";

        String enc = sub.encrypt(key);

        System.out.println(enc);

        System.out.println();

        System.out.println(new Substitution(enc).decrypt(key));

        System.out.println(sub.generateKeySequence("happy"));

    }
}
