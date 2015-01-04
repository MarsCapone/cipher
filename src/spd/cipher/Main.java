package spd.cipher;

public class Main {

    public static void main(String[] args) {

        String plaintext = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
        String plaintext2 = "defend the east wall of the castle";

        Affine a = new Affine(plaintext, 1);

        String enc = a.encrypt(5,7);

        System.out.println(enc);

        System.out.println();

        Affine b = new Affine(enc);
        System.out.println(b.decrypt());

    }
}
