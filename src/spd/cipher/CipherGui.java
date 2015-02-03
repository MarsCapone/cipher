package spd.cipher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class CipherGui implements ActionListener {
    private JPanel root;
    private JLabel label_title;
    private JLabel label_plaintext;
    private JLabel label_ciphertext;
    private JLabel label_englishness;
    private JLabel label_author;

    private JTextArea textarea_plaintext;
    private JTextArea textarea_ciphertext;

    private ButtonGroup cipherType;
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

    private JComboBox combobox_encdec;

    private JCheckBox checkbox_inferspaces;

    private JSlider slider_englishness;

    private JButton button_go;

    private JFormattedTextField keyField;



    public CipherGui() {
        JFrame frame = new JFrame("CipherGui");
        frame.setContentPane(this.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        combobox_encdec.setEnabled(true);

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

        button_go.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CipherGui();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == button_go) {
            String plaintext = getPlaintext();
            String ciphertext = getCiphertext();
            String cipherType = getCipherType();
            String key = getKey();
            boolean inferSpaces = inferSpaces();
            int englishness = getEnglishness();

            boolean encrypt = getEncDec().equalsIgnoreCase("encrypt");


            if (encrypt && key.equals("") && keyRequired()) {
                showMessagePopup("A key is required for encryption", "Key required");
            } else {

                switch (cipherType.toLowerCase()) {
                    case "caesar shift":
                        try {
                            if (encrypt) {
                                CaesarShift caesar = new CaesarShift(plaintext, 1);
                                ciphertext = caesar.encrypt(new Integer(key));
                            } else {
                                CaesarShift caesar = new CaesarShift(ciphertext);
                                if (key.equals("")) {
                                    plaintext = caesar.decrypt(new Integer(key));
                                } else {
                                    plaintext = caesar.decrypt();
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
                                    plaintext = sub.decrypt();
                                } else {
                                    plaintext = sub.decrypt(key);
                                }
                            }
                        } catch (Exception e) {
                            showMessagePopup(e.getMessage(), "ERROR");
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
                    default:
                        System.out.println("That function does not yet exist.");
                        break;
                }
                if (encrypt) {
                    setCiphertext(ciphertext);
                } else {
                    setPlaintext(plaintext);
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
        for (Enumeration<AbstractButton> buttons = cipherType.getElements(); buttons.hasMoreElements();) {
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

    private int getEnglishness() {
        return slider_englishness.getValue();
    }

    private String getEncDec() {
        return combobox_encdec.getSelectedItem().toString();
    }

    private void showMessagePopup(String message, String title) {
        JOptionPane.showMessageDialog(this.root, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private String getKey() {
        return keyField.getText();
    }

    private boolean keyRequired() {
        String cipher = getCipherType();
        if (cipher.equalsIgnoreCase("rot13") || cipher.equalsIgnoreCase("atbash")) {
            return false;
        } else {
            return true;
        }
    }



}
