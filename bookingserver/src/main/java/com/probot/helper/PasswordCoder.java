package com.probot.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author Vedang, Created on Sep 17, 2017
 *
 */
@Component
public class PasswordCoder
{

    private static final String ALGO = "DES";

    private static final String MODE = "ECB";

    private static final String PADDING = "PKCS5Padding";

    private static SecretKey key;

    private static final Logger logger = Logger.getLogger(Bookie.class);

    static
    {
        setSecretKey();
    }

    
    /**
     * Encrypts given password with secret key
     * @param plainText
     * @return
     * @throws Exception
     */
    public String encrypt(String plainText) throws Exception
    {
        logger.debug("Encryption algo running");
        Cipher cipher = Cipher.getInstance(ALGO + "/" + MODE + "/" + PADDING);
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);

        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        logger.debug("Encryption algo executed");
        return encryptedText;
    }

    /**
     * Decrypts password using secret key
     * @param encryptedText
     * @return
     * @throws Exception
     */
    public String decrypt(String encryptedText) throws Exception
    {
        logger.debug("Decryption algo running");
        Cipher cipher = Cipher.getInstance(ALGO + "/" + MODE + "/" + PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decryptedByte = cipher.doFinal(decoder.decode(encryptedText));

        String decryptedText = new String(decryptedByte);
        logger.debug("Decryption algo executed");
        return decryptedText;
    }

    private static void setSecretKey()
    {
        try
        {
            byte[] encoded = load("shared.key");
            SecretKeyFactory kf = SecretKeyFactory.getInstance(ALGO);
            KeySpec ks = new DESKeySpec(encoded);
            key = kf.generateSecret(ks);
        } catch (Exception e)
        {
            // Do Nothing
        }

    }

    private static byte[] load(String file) throws FileNotFoundException, IOException
    {
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[fis.available()];
        fis.read(buf);
        fis.close();
        return buf;
    }

}
