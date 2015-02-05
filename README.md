Cipher 
======

Encrypts and decrypts classical ciphers, using Java.

Interested in ciphers so just created this and previously created a similar program in Python. Just started Java in CS, so thought I would make the program again in Java. Hopefully using better code, and faster, and with a GUI and full command line interface.
This is not to be used for the National Cipher Challenge - that would be cheating!!!

***

**Currently implemented ciphers**:

* Atbash: encryption and decryption (there is only one key)
* ROT13: encryption and decryption (there is only one key)
* Caesar Shift: encryption and decryption, with a key or force search
* Substitution Cipher: encryption and decryption with or without a key
* Vigenere Cipher: encryption and decryption with a key, a key length or no key
* Affine Shift: encryption and decryption with or without a key.

**Planned Ciphers**:

* Autokey
* Beaufort
* Hill
* Unknown (attempts decryption even if the cipher is unknown)

**Extras**:

* There is a GUI
* I currently implements Encryption and Decryption of Atbash, ROT13, Caesar Shift, Substitution Cipher, Vigenere Cipher and Affine Shift
* It can also somewhat infer spaces, but this needs work.
* It has various statistical methods for checking how English a string looks, including:
    * Chi Squared Statistic
    * Index of Coincidence
    * NGram Scoring
