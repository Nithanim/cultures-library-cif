# cultures-library-cif
This library is able to extract .cif of the game series "cultures" by Funatics. It is currenty only able to read Type2 as it is referred [in the docs of Siguza](http://classic.cultrix.org/specs/cif_tab_sal.html) as "C2_EncryptedFile".

## Extract
```java
CifFile cifFile = CifFiles.readFile(src);
cifFile.writeFile(dest);
```

## License
This library is released under the Apache License Version 2.0.

Thanks to [netty](http://netty.io/) for their aweseome buffer library, which is not directly used any more in this project.
An own implementation of Buffer is now used (that is compatible with the method names and arguments) but uses long for indexes instead to be able to use it in combination with RandomAccessFile.
This library would have been possible with netty!

Special thanks to [Siguza](http://siguza.net/) who released [his findings on the format](http://classic.cultrix.org/specs/cif_tab_sal.html).