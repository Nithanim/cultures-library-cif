package me.nithanim.cultures.library.cif;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import me.nithanim.cultures.library.cif.type2.CifFileType2;
import me.nithanim.longbuffer.Buffer;
import me.nithanim.longbuffer.RandomAccessFileBuffer;

public class CifFiles {
    public static CifFile readFile(File src) throws IOException {
        return readFile(openFileAsReadableBuffer(src));
    }
    
    public static CifFile readFile(Buffer src) throws IOException {
        long magic = src.readUnsignedInt();
        
        if(magic == 1021) {
            return new CifFileType2();
        } else if(magic == 65601) {
            throw new UnsupportedOperationException("CifType1 is currently unsupported.");
        } else {
            throw new UnsupportedOperationException("Unknown cif file!");
        }
    }
    
    private static Buffer openFileAsReadableBuffer(File f) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(f, "r");
        return new RandomAccessFileBuffer(raf).order(ByteOrder.LITTLE_ENDIAN);
    }
    
    private CifFiles() {
    }
}
