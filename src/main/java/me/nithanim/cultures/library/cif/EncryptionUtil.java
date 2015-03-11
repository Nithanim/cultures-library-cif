package me.nithanim.cultures.library.cif;

import me.nithanim.longbuffer.Buffer;

public class EncryptionUtil {
    public static void decryptCommon(byte[] encrypted) {
        int c = 71;
        int d = 126;
        for(int i = 0; i < encrypted.length; i++) {
            encrypted[i] = (byte) (((encrypted[i] & 0xFF) - 1) ^ c);
            c += d;
            d += 33;
        }
    }
    public static void decryptCommon(Buffer encrypted) {
        long wi = encrypted.writerIndex();
        try {
            encrypted.writerIndex(encrypted.readerIndex());
            int c = 71;
            int d = 126;
            while(encrypted.readableBytes() > 0) {
                encrypted.writeByte((byte) (((encrypted.readByte() & 0xFF) - 1) ^ c));
                c += d;
                d += 33;
            }
        } finally {
            encrypted.writerIndex(wi);
        }
    }
    
    private EncryptionUtil() {
    }
}
