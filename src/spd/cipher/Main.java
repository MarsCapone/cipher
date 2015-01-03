package spd.cipher;

import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference;

public class Main {

    public static void main(String[] args) {

        String plaintext = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        String plaintext2 = "attackatdawn";

        Atbash a = new Atbash(plaintext2, 1);

        String enc = a.encrypt();

        System.out.println(enc);

        System.out.println();

        Atbash b = new Atbash(enc);
        System.out.println(b.decrypt());

    }
}
