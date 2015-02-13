package spd.cipher;

import org.apache.commons.cli.*;

import javax.swing.*;

public class Decrypter {

    Options options;

    public Decrypter() {

        options = new Options();

        Option cipherType = OptionBuilder.hasArgs(1)
                .withArgName("cipher type")
                .withDescription("Cipher type")
                .withLongOpt("cipher")
                .create("c");

        Option key = OptionBuilder.hasArgs(1)
                .withArgName("key")
                .withDescription("Encryption key")
                .withLongOpt("key")
                .create("k");

        options.addOption(cipherType);
        options.addOption(key);

        options.addOption("g",
                "gui",
                false,
                "Start GUI");
        options.addOption("e",
                "encrypt",
                false,
                "Encrypt");
        options.addOption("d",
                "decrypt",
                false,
                "Decrypt");
        options.addOption("k",
                "key",
                true,
                "Encryption key");
    }

    public Options getOptions() {
        return options;
    }

    public static void main(String[] args) {
        Options options = new Decrypter().getOptions();
        CommandLine cmd;
        try {
            cmd = new GnuParser().parse(options, args);
            if (cmd.hasOption("g")) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new CipherGui();
            } else {
                System.out.println("It seems you don't want to use the GUI. This is not an option.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
