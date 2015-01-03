package spd.cipher;

import javax.swing.*;

public class CipherGui {
    private JPanel root;
    private JLabel label_title;
    private JTextArea textarea_plaintext;
    private JRadioButton cipher_hill;
    private JRadioButton cipher_unknown;
    private JRadioButton cipher_affine;
    private JRadioButton cipher_atbash;
    private JRadioButton cipher_caesar;
    private JRadioButton cipher_simplesub;
    private JRadioButton cipher_vigenere;
    private JRadioButton cipher_beaufort;
    private JRadioButton cipher_autokey;
    private JLabel label_plaintext;
    private JLabel label_ciphertext;
    private JComboBox combobox_encdec;
    private JLabel label_englishness;
    private JCheckBox checkbox_inferspaces;
    private JSlider slider_englishness;
    private JButton button_go;
    private JLabel label_author;
}
