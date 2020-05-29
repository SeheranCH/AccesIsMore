package ch.noseryoung.accessismore.security;

import android.util.Base64;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordEncoder {

    private static final String TAG = "PasswordEncoder";

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";

    public static String encrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(PasswordEncoder.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        Log.d(TAG, "Password has been encrypted: " + encryptedValue64);
        return encryptedValue64;

    }

    public static String decrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(PasswordEncoder.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue, "utf-8");
        Log.d(TAG, "Password has been decrypted: " + decryptedValue);
        return decryptedValue;

    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(PasswordEncoder.KEY.getBytes(), PasswordEncoder.ALGORITHM);
        return key;
    }
}
