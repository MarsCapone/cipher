package spd.cipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

class CipherGui implements ActionListener {
    private final ButtonGroup cipherType;
    private JPanel root;
    private JLabel label_title;
    private JLabel label_plaintext;
    private JLabel label_ciphertext;
    private JLabel label_englishness;
    private JLabel label_author;
    private JLabel label_key;
    private JTextArea textarea_plaintext;
    private JTextArea textarea_ciphertext;
    private JRadioButton cipher_hill;
    private JRadioButton cipher_unknown;
    private JRadioButton cipher_affine;
    private JRadioButton cipher_atbash;
    private JRadioButton cipher_caesar;
    private JRadioButton cipher_simplesub;
    private JRadioButton cipher_vigenere;
    private JRadioButton cipher_beaufort;
    private JRadioButton cipher_autokey;
    private JRadioButton cipher_rot13;
    private JRadioButton cipher_coltrans;

    private JComboBox combobox_encdec;

    private JCheckBox checkbox_inferspaces;

    private JButton button_go;

    private JFormattedTextField keyField;
    private JPanel border;

    private String PLAINTEXT;

    public CipherGui() {
        JFrame frame = new JFrame("CipherGui");
        frame.setContentPane(this.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setPreferredSize(new Dimension(600, 480));

        combobox_encdec.setEnabled(true);

        label_title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        cipherType = new ButtonGroup();
        cipherType.add(cipher_hill);
        cipherType.add(cipher_unknown);
        cipherType.add(cipher_affine);
        cipherType.add(cipher_atbash);
        cipherType.add(cipher_caesar);
        cipherType.add(cipher_simplesub);
        cipherType.add(cipher_vigenere);
        cipherType.add(cipher_beaufort);
        cipherType.add(cipher_autokey);
        cipherType.add(cipher_rot13);
        cipherType.add(cipher_coltrans);

        button_go.addActionListener(this);
        cipher_affine.addActionListener(this);
        checkbox_inferspaces.addActionListener(this);

        textarea_ciphertext.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textarea_ciphertext.setLineWrap(true);
        textarea_plaintext.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textarea_plaintext.setLineWrap(true);

        frame.pack();
        frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CipherGui();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == cipher_affine) {
            showMessagePopup("If providing a key, please enter values for a and b in the form \"a,b\".\n"
                    + "The equation for encryption is in the form, y=ax+b", "Incorrect Key");
        }

        if (actionEvent.getSource() == checkbox_inferspaces) {
            if (inferSpaces()) {
                if (getEncDec().equalsIgnoreCase("decrypt")) {
                    setPlaintext(English.inferSpaces(PLAINTEXT));
                }
            } else {
                if (getEncDec().equalsIgnoreCase("decrypt")) {
                    setPlaintext(PLAINTEXT);
                }
            }
        }

        if (actionEvent.getSource() == button_go) {
            String plaintext = getPlaintext().toLowerCase();
            String ciphertext = getCiphertext().toLowerCase();
            String cipherType = getCipherType().toLowerCase();
            String key = getKey();
            boolean inferSpaces = inferSpaces();

            boolean encrypt = getEncDec().equalsIgnoreCase("encrypt");


            if (encrypt && key.equals("") && keyRequired()) {
                showMessagePopup("A key is required for encryption", "Key required");
            } else {

                switch (cipherType) {
                    case "caesar shift":
                        try {
                            if (encrypt) {
                                CaesarShift caesar = new CaesarShift(plaintext, 1);
                                ciphertext = caesar.encrypt(new Integer(key));
                            } else {
                                CaesarShift caesar = new CaesarShift(ciphertext);
                                if (!key.equals("")) {
                                    plaintext = caesar.decrypt(new Integer(key));
                                } else {
                                    //plaintext = caesar.decrypt();
                                    String[] allShifts = caesar.decryptAllShifts();
                                    for (String s : allShifts) {
                                        System.out.println(s);
                                        System.out.println();
                                    }
                                }
                            }
                        } catch (NumberFormatException n) {
                            showMessagePopup("Caesar ciphers use an integer key", "Wrong key type");
                        }
                        break;
                    case "simple substitution":
                        try {
                            if (encrypt) {
                                Substitution sub = new Substitution(plaintext, 1);
                                if (key.matches(".*\\d+.*")) {
                                    showMessagePopup("Substitution key cannot contain numbers.", "Incorrect Key");
                                    break;
                                }
                                ciphertext = sub.encrypt(key);
                            } else {
                                Substitution sub = new Substitution(ciphertext);
                                if (key.equals("")) {
                                    plaintext = sub.hillClimbDecrypt();
                                } else {
                                    plaintext = sub.decrypt(key);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "rot13":
                        if (encrypt) {
                            ROT13 rot13 = new ROT13(plaintext, 1);
                            ciphertext = rot13.encrypt();
                        } else {
                            ROT13 rot13 = new ROT13(ciphertext);
                            plaintext = rot13.decrypt();
                        }
                        break;
                    case "atbash":
                        if (encrypt) {
                            Atbash atbash = new Atbash(plaintext, 1);
                            ciphertext = atbash.encrypt();
                        } else {
                            Atbash atbash = new Atbash(ciphertext);
                            plaintext = atbash.decrypt();
                        }
                        break;
                    case "affine shift":
                        try {
                            if (encrypt) {
                                String[] keys = key.split("\\D");
                                int a = Integer.valueOf(keys[0]);
                                int b = Integer.valueOf(keys[1]);
                                Affine affine = new Affine(plaintext, 1);
                                ciphertext = affine.encrypt(a, b);
                            } else {
                                Affine affine = new Affine(ciphertext);
                                if (key.equals("")) {
                                    plaintext = affine.decrypt();
                                } else {
                                    String[] keys = key.split("\\D");
                                    int a = Integer.valueOf(keys[0]);
                                    int b = Integer.valueOf(keys[1]);
                                    plaintext = affine.decrypt(a, b);
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException n) {
                            showMessagePopup("Please enter values for a and b in the form \"a,b\", such that encryption is in the form, y=ax+b", "Incorrect Key");
                            n.printStackTrace();
                        }
                        break;
                    case "vigenere":
                        if (encrypt) {
                            Vigenere vigenere = new Vigenere(plaintext, 1);
                            ciphertext = vigenere.encrypt(key);
                        } else {
                            Vigenere vigenere = new Vigenere(ciphertext);
                            if (key.equals("")) {
                                plaintext = vigenere.decrypt();
                            } else {
                                try {
                                    int keylength = Integer.valueOf(key);
                                    plaintext = vigenere.decrypt(keylength);
                                } catch (NumberFormatException n) {
                                    plaintext = vigenere.decrypt(key);
                                }
                            }
                            keyField.setText(vigenere.getKEY());
                        }
                        break;

                    default:
                        System.out.println("That function does not yet exist.");
                        break;
                }
                PLAINTEXT = plaintext;
                if (encrypt) {
                    setCiphertext(ciphertext);
                } else {
                    if (inferSpaces) {
                        setPlaintext(English.inferSpaces(PLAINTEXT));
                    } else {
                        setPlaintext(PLAINTEXT);
                    }
                }
            }
        }
    }


    private String getPlaintext() {
        return textarea_plaintext.getText();
    }

    private void setPlaintext(String text) {
        textarea_plaintext.setText(text);
    }

    private String getCiphertext() {
        return textarea_ciphertext.getText();
    }

    private void setCiphertext(String text) {
        textarea_ciphertext.setText(text);
    }

    private String getCipherType() {
        for (Enumeration<AbstractButton> buttons = cipherType.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    private boolean inferSpaces() {
        return checkbox_inferspaces.isSelected();
    }

    private String getEncDec() {
        return combobox_encdec.getSelectedItem().toString();
    }

    private void showMessagePopup(String message, String title) {
        JOptionPane.showMessageDialog(this.root, message, title, JOptionPane.WARNING_MESSAGE);
    }

    private String getKey() {
        return keyField.getText();
    }

    private boolean keyRequired() {
        String cipher = getCipherType();
        return !(cipher.equalsIgnoreCase("rot13") || cipher.equalsIgnoreCase("atbash"));
    }
}