package me.nithanim.cultures.library.cif;

import java.io.File;
import java.io.IOException;
import java.util.List;
import me.nithanim.longbuffer.Buffer;

public interface CifFile {
    List<? extends CifEntry> getCifEntries();
    void readFile(Buffer src) throws IOException;
    void writeFile(File dest) throws IOException;
}
