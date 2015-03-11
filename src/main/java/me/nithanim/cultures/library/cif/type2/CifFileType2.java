package me.nithanim.cultures.library.cif.type2;

import java.util.ArrayList;
import java.util.List;
import me.nithanim.cultures.library.cif.CifFileBase;
import me.nithanim.longbuffer.Buffer;

public class CifFileType2 extends CifFileBase {
    @Override
    protected void _skipUnused1(Buffer src) {
        src.readInt(); //zero
        src.readInt(); //one
    }
    
    @Override
    protected List<MetaEntry> readContent(Buffer indexBuffer, Buffer contentBuffer) {
        List<MetaEntry> entries = new ArrayList<MetaEntry>();
        while(indexBuffer.readableBytes() > 0) {
            int index = indexBuffer.readInt();
            contentBuffer.readerIndex(index);
            byte meta = contentBuffer.readByte();
            String str = javaStringFromCString(contentBuffer);
            entries.add(new MetaEntry(meta, str));
        }
        return entries;
    }
    
    @Override
    protected void _skipUnused2(Buffer src) {
        src.readInt(); //numberOfEntries2
        src.readInt(); //numberOfEntries3
        src.readInt(); //contentLength, but repeats later
        src.readInt(); //constant
        src.readInt(); //zero
    }

    @Override
    protected void _skipUnused3(Buffer src) {
        src.readByte(); //byte
        src.readInt(); //constant
        src.readInt(); //zero
    }

    @Override
    protected void writeEntries(Buffer buffer) {
        List<MetaEntry> entries = (List<MetaEntry>) getCifEntries();
        StringBuilder sb = new StringBuilder(20);
        
        for(MetaEntry entry : entries) {
            if(entry.getMeta() == 1) {
                sb.append('[').append(entry.getString()).append(']');
            } else if(entry.getMeta() == 2) {
                sb.append(entry.getString());
            } else {
                throw new IllegalArgumentException(
                        "Unknown meta ("+ entry.getMeta() + ") of " + entry.getString());
            }

            sb.append('\n');
            buffer.writeBytes(sb.toString().getBytes(CHARSET));
            sb.setLength(0);
        }
    }
}
