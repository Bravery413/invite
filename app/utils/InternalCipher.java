package utils;

import java.io.UnsupportedEncodingException;

public class InternalCipher {

    private static long[] CODES = {1234567890L, 2876543109L, 3456789012L, 1934567802L, 1730682549L};

    private static final String KEYS = "fa13fbecab134cd554ac913666f1582b";
    private static InternalCipher instance = new InternalCipher();

    private InternalCipher() {
        int h = 0;
        byte keys[] = KEYS.getBytes();
        for (int i=0; i<keys.length; i++) {
            h = 17 * h +  keys[i];
        }
        h = h & 0x7fffffff;
        for (int i=0; i<CODES.length; i++) {
            CODES[i] = (CODES[i] + h) & 0x7fffffff;
        }
    }

    public static InternalCipher getInstance() {
        return  instance;
    }

    public String encrypt(String plain) {
        if (plain == null || plain.equals("")) {
            return "";
        }

        byte[] data;
        try {
            data = plain.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        for (int i=0; i<data.length; i++) {
            data[i] ^= CODES[i % CODES.length];
        }

        return StringUtils.base64Encode(data);
    }

    public String decrypt(String crypt) {
        if (crypt == null || crypt.equals("")) {
            return "";
        }
        byte[] decode = StringUtils.base64DecodeBytes(crypt);
        if (decode == null) {
            return "";
        }

        for (int i=0; i<decode.length; i++) {
            decode[i] ^= CODES[i % CODES.length];
        }

        try {
            return new String(decode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
