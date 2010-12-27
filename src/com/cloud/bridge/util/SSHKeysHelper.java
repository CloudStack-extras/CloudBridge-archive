package com.cloud.bridge.util;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.JSch;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class SSHKeysHelper {

    private KeyPair keyPair;

    public SSHKeysHelper() {
        try {
            keyPair = KeyPair.genKeyPair(new JSch(), KeyPair.RSA);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public String getPublicKeyFingerPrint() {
        return getPublicKeyFingerprint(getPublicKey());
    }

    public static String getPublicKeyFingerprint(String publicKey) {
        Base64 b64 = new Base64();
        String key[] = publicKey.split(" ");
        byte[] keyBytes = b64.decode(key[1].getBytes());

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String sumString = StringHelper.toHexString(md5.digest(keyBytes));
        String rString = "";

        for (int i = 2; i <= sumString.length(); i += 2) {
            rString += sumString.substring(i-2, i);
            if (i != sumString.length())
                rString += ":";
        }

        return rString.toLowerCase();
    }

    public static String getPublicKeyFromKeyMaterial(String keyMaterial) {
        if (!keyMaterial.contains(" ")) {
            Base64 b64 = new Base64();
            return new String(b64.decode(keyMaterial.getBytes()));
        } 

        return keyMaterial; 
    }

    public String getPublicKey() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        keyPair.writePublicKey(baos, "");

        return baos.toString();
    }

    public String getPrivateKey() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        keyPair.writePrivateKey(baos);

        return baos.toString();
    }
}

