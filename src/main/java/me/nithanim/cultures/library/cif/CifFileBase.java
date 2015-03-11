package me.nithanim.cultures.library.cif;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.List;
import me.nithanim.longbuffer.Buffer;
import me.nithanim.longbuffer.RandomAccessFileBuffer;
import static me.nithanim.cultures.library.cif.EncryptionUtil.*;
import me.nithanim.longbuffer.ByteArrayBuffer;

public abstract class CifFileBase implements CifFile {
    public static final Charset CHARSET = Charset.forName("US-ASCII");
    
    protected List<? extends CifEntry> content;
    
    private Buffer buffer;
    
    @Override
    public void readFile(Buffer src) throws IOException {
        _skipUnused1(buffer);
        readNumberOfEntries(buffer);
        _skipUnused2(buffer);
        byte[] indexRaw = readIndex(buffer);
        _skipUnused3(buffer);
        byte[] contentRaw = readContentRaw(buffer);
        
        decryptCommon(indexRaw);
        decryptCommon(contentRaw);
        
        content = readContent(
                new ByteArrayBuffer(indexRaw).order(ByteOrder.LITTLE_ENDIAN),
                new ByteArrayBuffer(contentRaw).order(ByteOrder.LITTLE_ENDIAN));
        
        buffer.dispose();
    }
    
    @Override
    public void writeFile(File dest) throws IOException {
        Buffer out = openFileAsWriteableBuffer(dest);
        try {
            writeEntries(buffer);
        } finally {
            out.dispose();
        }
    }
    
    protected abstract void writeEntries(Buffer buffer);
    
    protected abstract void _skipUnused1(Buffer src);
    protected abstract void _skipUnused2(Buffer src);
    protected abstract void _skipUnused3(Buffer src);
    
    protected abstract List<? extends CifEntry> readContent(Buffer indexBuffer, Buffer contentBuffer);
    
    protected int readNumberOfEntries(Buffer src) {
        return src.readInt();
    }
    
    protected byte[] readIndex(Buffer src) {
        int indexLength = buffer.readInt();
        byte[] index = new byte[indexLength];
        buffer.readBytes(index);
        return index;
    }
    
    protected byte[] readContentRaw(Buffer buffer) {
        int length = buffer.readInt();
        byte[] cnt = new byte[length];
        buffer.readBytes(cnt);
        return cnt;
    }
    
    protected String javaStringFromCString(Buffer b) {
        long start = b.readerIndex();
        int length = 0;
        for(; length < b.readableBytes(); length++) {
            if(b.readByte() == 0) {
                break;
            }
        }
        
        b.readerIndex(start);
        byte[] arr = new byte[length];
        b.readBytes(arr);
        return new String(arr, CHARSET);
    }
    
    private Buffer openFileAsWriteableBuffer(File file) throws IOException{
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        return new RandomAccessFileBuffer(raf).order(ByteOrder.LITTLE_ENDIAN);
    }
    
    @Override
    public List<? extends CifEntry> getCifEntries() {
        return content;
    }
}
