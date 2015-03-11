package me.nithanim.cultures.library.cif.type2;

import me.nithanim.cultures.library.cif.CifEntry;

public class MetaEntry extends CifEntry{
    private final byte meta;
    
    public MetaEntry(byte meta, String string) {
        super(string);
        this.meta = meta;
    }
    
    public byte getMeta() {
        return meta;
    }
}
