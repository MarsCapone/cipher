package spd.cipher;// by samson

public class ColumnarTransposition extends Cipher {
    public ColumnarTransposition() {
        super();
    }

    public ColumnarTransposition(String ciphertext) {
        super(ciphertext);
    }

    public ColumnarTransposition(String text, int option) {
        super(text, option);
    }

    private String[] string2columns(String text, int keylength) {
        String[] columns = new String[keylength];
        StringBuilder columnBuilder = new StringBuilder();
        int textlength = text.length();
        for (int s=0; s<keylength; s++) {
            StringBuilder col = new StringBuilder();
            for (int c=s; c<textlength; c+=keylength) {
                col.append(text.charAt(c));
            }
            columns[s] = String.valueOf(col);
        }
        return columns;
    }

    private String columns2string(String [] columns) {
        int n = columns.length;
        StringBuilder stringBuilder = new StringBuilder();
        int i=0;
        while (true) {
            try {
                for (String col : columns) {
                    stringBuilder.append(col.charAt(i));
                }
                i++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return String.valueOf(stringBuilder);
    }
}
